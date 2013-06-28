package suncertify.application;

import java.util.ArrayList;
import java.util.List;

import suncertify.db.CommunicationException;
import suncertify.db.DB;
import suncertify.db.RecordAlreadyBookedException;
import suncertify.db.RecordNotFoundException;
import suncertify.domain.Subcontractor;

/**
 * Provides a business method for each use case of the application:
 * <ul>
 * <li>Listing all valid {@code Subcontractors} in the database</li>
 * <li>Searching for matching {@code Subccontractor(s)} by name and/or location</li>
 * <li>Booking a {@code Subcontractor} for a specified customer ID</li>
 * </ul>
 * <p>
 * Internally the class uses a {@link DAOFactory} to retrieve an instance of a
 * class implementing the {@link DB} interface to access the data. Since the
 * implementation only uses the methods provided by the {@code DB} interface, we
 * do not need to distinguish whether we operate directly on a data file or
 * whether the data operations are sent over a network.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class DBService {

	private final DB dao;

	/**
	 * Creates a new {@link DBService} instance.
	 * 
	 * @param dao
	 *            <i>data access object</i> for interaction with the database
	 *            containing {@code Subcontractors}
	 */
	public DBService(final DB dao) {
		this.dao = dao;
	}

	/**
	 * Returns a list of all valid {@link Subcontractor}'s.
	 * 
	 * @return all valid {@code Subcontractors}
	 */
	public List<Subcontractor> retrieveAllSubcontractors() {
		// Fetching all Subcontractors is the same as "searching" for records
		// without any filtering criteria. The boolean to represent exactMatch
		// is not of consequence as the name/location are null so all records
		// will be retrieved and comparison is not possible.
		return this.searchSubcontractor(null, null, false);
	}

	/**
	 * Returns a list of {@link Subcontractor}'s that exactly match the
	 * specified criteria.
	 * <p>
	 * A criteria value of null matches any field value.
	 * 
	 * @param name
	 *            Criteria for the name field
	 * @param location
	 *            Criteria for the location field
	 * @param exactMatchRequired
	 *            boolean value to represent if the name/location should be
	 *            exactly matched
	 * @return all {@code Subcontractors} matching the criteria
	 */
	public List<Subcontractor> searchSubcontractor(final String name, final String location,
			final boolean exactMatchRequired) {
		final String[] searchCriteria = new String[] { name, location, null, null, null, null };
		final List<Subcontractor> result = new ArrayList<Subcontractor>();

		try {
			final int[] matchingRecords = this.dao.find(searchCriteria);
			if (matchingRecords != null) {
				for (final int recordNumber : matchingRecords) {
					String[] recordData;
					try {
						recordData = this.dao.read(recordNumber);
						// Can assume record is valid in the constructor as any
						// deleted records will be filtered out
						final Subcontractor subContractor = new Subcontractor(recordNumber,
								recordData);
						if (exactMatchRequired) {
							if (this.isExactMatch(subContractor, name, location)) {
								result.add(subContractor);
							}
						} else {
							result.add(subContractor);
						}
					} catch (final RecordNotFoundException canBeIgnored) {
						// This exception can be ignored.
						// This situation may happen, when another client
						// deletes the Subcontractor between this DAO call to
						// find and call to read.
					}
				}
			}

		} catch (final CommunicationException communicationException) {
			this.handleCommunicationException(communicationException);
		}
		return result;
	}

	private boolean isExactMatch(final Subcontractor subContractor, final String name,
			final String location) {
		boolean exactMatch = true;
		if (name != null) {
			exactMatch &= name.equals(subContractor.getName());
		}
		if (location != null) {
			exactMatch &= location.equals(subContractor.getLocation());
		}
		return exactMatch;
	}

	/**
	 * Tries to book the {@link Subcontractor} with the specified record number.
	 * <p>
	 * The parameter containing a collection of {@code Subcontractors} is
	 * modified to reflect the update. So even if the method throws an exception
	 * (and does not return an updated record collection), the parameter's
	 * modified value can be used by callers to receive an updated view of the
	 * {@code Subcontractors}.
	 * 
	 * @param subContractor
	 *            {@code Subcontractor} to be booked.
	 * @param subContractors
	 *            {@code Subcontractors} that are currently visible to the
	 *            caller.
	 * @param customerID
	 *            the customerID representing who wants to book a specific
	 *            {@code Subcontractor}
	 * @return collection of {@code Subcontractors} containing the update
	 * @throws RecordAlreadyBookedException
	 *             if the record is already booked
	 * @throws RecordNotFoundException
	 *             if no record with the given record number exists or if it is
	 *             marked as deleted
	 */
	public List<Subcontractor> bookSubcontractor(final Subcontractor subContractor,
			final List<Subcontractor> subContractors, final String customerID)
			throws RecordAlreadyBookedException, SecurityException, RecordNotFoundException {
		final int recordNumber = subContractor.getRecordNumber();
		long lockCookie = -1;
		boolean locked = false;

		try {
			lockCookie = this.dao.lock(recordNumber);
			// if the lock method succeeded (i.e. no exception thrown) then we
			// will need to release the lock in the finally block
			locked = true;

			final String[] data = this.dao.read(recordNumber);
			final Subcontractor updatedSubcontractor = new Subcontractor(recordNumber, data);

			// If the Subcontractor was already booked by another user, update
			// the current Subcontractors displayed to reflect this and report
			// the error to the user.
			if (updatedSubcontractor.isBooked()) {
				this.updateCurrentSubcontractors(subContractors, updatedSubcontractor);
				throw new RecordAlreadyBookedException();
			}

			updatedSubcontractor.setOwner(customerID);
			this.dao.update(recordNumber, updatedSubcontractor.getData(), lockCookie);
			this.updateCurrentSubcontractors(subContractors, updatedSubcontractor);
		} catch (final SecurityException canBeIgnored) {
			// Can only occur if a mistake occurs with the cookie value
			// logic handling. Can be ignored.
		} catch (final CommunicationException communicationException) {
			this.handleCommunicationException(communicationException);
		} finally {
			if (locked) {
				try {
					this.dao.unlock(recordNumber, lockCookie);
				} catch (final SecurityException securityException) {
					LaunchApplication.showError("Unable to unlock the Subcontractor!");
				} catch (final RecordNotFoundException canBeIgnored) {
					// Can be ignored as we had the lock on the record we were
					// updating so this type of exception should not occur.
				} catch (final CommunicationException communicationException) {
					this.handleCommunicationException(communicationException);
				}
			}
		}

		return subContractors;
	}

	/**
	 * Update current collection of {@code Subcontractors} where a specific
	 * {@code Subcontractors} values are overwritten with the newly updated
	 * values.
	 * 
	 * @param subContractors
	 *            current list of {@code Subcontractors}
	 * @param updatedSC
	 *            the updated instance of a {@code Subcontractor}
	 */
	private void updateCurrentSubcontractors(final List<Subcontractor> subContractors,
			final Subcontractor updatedSC) {
		for (final Subcontractor currentSC : subContractors) {
			if (updatedSC.getRecordNumber() == currentSC.getRecordNumber()) {
				currentSC.setName(updatedSC.getName());
				currentSC.setLocation(updatedSC.getLocation());
				currentSC.setSize(updatedSC.getSize());
				currentSC.setSpecialties(updatedSC.getSpecialties());
				currentSC.setRate(updatedSC.getRate());
				currentSC.setOwner(updatedSC.getOwner());
			}
		}
	}

	/**
	 * Handle communication exception.
	 * <p>
	 * Communication between Client and Server remotely needs to be handled
	 * gracefully. Client needs to be closed down immediately after encountering
	 * a {@link CommunicationException} as we can no longer guarantee that
	 * client display is consistent with the database been accessed by the
	 * server.
	 * 
	 * @param communicationException
	 *            the communication exception experienced between network client
	 *            and server.
	 */
	private void handleCommunicationException(final CommunicationException communicationException) {
		LaunchApplication.showErrorAndExit("Communication issue encountered with remote server."
				+ "\nClient may now be out of sync with server so must be exited.");
	}

}
