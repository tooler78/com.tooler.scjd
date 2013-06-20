package suncertify.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import suncertify.db.lock.LockManager;
import suncertify.domain.DatabaseSchema;
import suncertify.domain.RecordState;
import suncertify.domain.Subcontractor;

/**
 * Implementation of the {@link DBOperations} interface that operates directly
 * on a data file.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class Data implements DBOperations {

	private FileAccess fileAccess;

	private LockManager lockManager;

	private ConcurrentSkipListMap<Integer, Subcontractor> cache = new ConcurrentSkipListMap<Integer, Subcontractor>();

	/**
	 * SingletonHolder is loaded on the first execution of
	 * {@link Data#getInstance()} or the first access to
	 * {@code SingletonHolder.INSTANCE}, not before.
	 */
	private static class SingletonHolder {
		private final static Data INSTANCE = new Data();
	}

	/**
	 * Instantiates the {@code Data} object.
	 */
	private Data() {
	}

	/**
	 * Gets the single instance of {@code Data}.
	 * 
	 * @return single instance of {@code Data}
	 */
	public static Data getInstance() {
		return Data.SingletonHolder.INSTANCE;
	}

	/** {@inheritDoc} */
	@Override
	public void initialize(final String databaseLocation)
			throws InvalidDatabaseException {
		this.fileAccess = new FileAccess(databaseLocation);
		this.cache.putAll(this.fileAccess.getAllRecords());
		this.lockManager = new LockManager();
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() throws InvalidDatabaseException {
		try {
			if (this.fileAccess != null) {
				this.fileAccess.saveAllRecords(this.cache);
			}
		} catch (final IOException e) {
			throw new InvalidDatabaseException(
					"Error while saving records into the database file.");
		} finally {
			this.fileAccess = null;
			this.lockManager = null;
			this.cache = new ConcurrentSkipListMap<Integer, Subcontractor>();
		}

	}

	/** {@inheritDoc} */
	@Override
	public String[] read(final int recNo) throws RecordNotFoundException {
		return this.cache.get(recNo).getData();
	}

	/** {@inheritDoc} */
	@Override
	public void update(final int recNo, final String[] data,
			final long lockCookie) throws RecordNotFoundException,
			SecurityException {
		this.lockManager.checkCookie(recNo, lockCookie);
		if (this.cache.containsKey(recNo)) {
			final Subcontractor subContractor = this.cache.get(recNo);
			subContractor.setData(data);
			this.cache.putIfAbsent(recNo, subContractor);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void delete(final int recNo, final long lockCookie)
			throws RecordNotFoundException, SecurityException {
		this.lockManager.checkCookie(recNo, lockCookie);
		if (this.cache.containsKey(recNo)) {
			final Subcontractor subContractor = this.cache.get(recNo);
			subContractor.setState(RecordState.deleted);
			this.cache.put(recNo, subContractor);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int[] find(final String[] criteria) {
		final List<Integer> matchFound = new ArrayList<Integer>();
		if (criteria != null && criteria.length == DatabaseSchema.FIELD_COUNT) {
			for (final Integer key : this.cache.keySet()) {
				final Subcontractor contractor = this.cache.get(key);
				if (contractor.getState() != RecordState.deleted) {
					if (this.isMatchingSubcontractor(contractor.getData(),
							criteria)) {
						matchFound.add(key);
					}
				}
			}
		}
		final int[] result = new int[matchFound.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = matchFound.get(i);
		}
		return result;
	}

	private boolean isMatchingSubcontractor(final String[] data,
			final String[] query) {
		boolean criteriaMatch = true;
		for (int i = 0; i < query.length; i++) {
			final String attribute = query[i];
			if (attribute != null) {
				if (!data[i].toLowerCase().contains(attribute.toLowerCase())) {
					criteriaMatch = false;
				}
			}
		}
		return criteriaMatch;
	}

	/** {@inheritDoc} */
	@Override
	public int create(final String[] data) throws DuplicateKeyException {
		// We need to check through the current cache for an existing record
		// who's state is deleted. If no record is found the we need to add
		// a new entry to our cache
		final int NO_DELETED_RECORD_FOUND = -1;
		int availRecordNo = NO_DELETED_RECORD_FOUND;
		for (final Integer key : this.cache.keySet()) {
			final Subcontractor subContractor = this.cache.get(key);
			if (subContractor.getState() == RecordState.deleted
					&& availRecordNo == NO_DELETED_RECORD_FOUND) {
				availRecordNo = key;
				break;
			}
		}

		if (availRecordNo == NO_DELETED_RECORD_FOUND) {
			// If no record in the cache was marked as deleted, use the
			// cache size to calculate a record number.
			availRecordNo = this.cache.size() + 1;
		}
		final Subcontractor contractor = new Subcontractor(RecordState.valid,
				data);
		this.cache.put(availRecordNo, contractor);

		return availRecordNo;
	}

	/** {@inheritDoc} */
	@Override
	public long lock(final int recNo) throws RecordNotFoundException {
		final long cookie = this.lockManager.lock(recNo);
		if (!this.cache.containsKey(recNo)) {
			// Record number does not exist so we need to unlock the record
			// and then throw the RecordNotFoundException
			this.lockManager.unlock(recNo, cookie);
			throw new RecordNotFoundException("Record with number " + recNo
					+ " does not exist");
		}

		return cookie;
	}

	/** {@inheritDoc} */
	@Override
	public void unlock(final int recNo, final long cookie)
			throws RecordNotFoundException, SecurityException {
		this.lockManager.unlock(recNo, cookie);
	}

}