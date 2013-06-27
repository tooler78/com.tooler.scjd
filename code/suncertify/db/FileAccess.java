package suncertify.db;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import suncertify.domain.DatabaseSchema;
import suncertify.domain.RecordState;
import suncertify.domain.Subcontractor;

/**
 * This is the worker class that does all the access and manipulation of the
 * physical file that is our database. <br/>
 * Having all the code here, instead of in the {@link Data} class, means that
 * private methods are only in here - the {@link Data} class is much cleaner. <br/>
 * This class can be thought of as using the Adapter design pattern (where this
 * class is hiding the low level database access information from the end user.
 * 
 * @version 1.0
 * @author Damien O'Toole
 * @see Data
 */

class FileAccess {

	private static final String ENCODING = "US-ASCII";

	private static RandomAccessFile database;

	private static ReadWriteLock dbReadWriteLock = new ReentrantReadWriteLock();

	/**
	 * Instantiates a new file access.
	 * 
	 * @param databaseLocation
	 * 
	 * @throws IOException
	 *             error while accessing the file.
	 * @throws DatabaseException
	 *             the invalid database exception
	 */
	public FileAccess(final String databaseLocation) throws DatabaseException {
		this.validateDatabase(databaseLocation);
	}

	private void validateDatabase(final String databaseLocation)
			throws DatabaseException {
		int magicCookie = -1;
		try {
			FileAccess.database = new RandomAccessFile(databaseLocation, "rw");
			magicCookie = FileAccess.database.readInt();
		} catch (final IOException ioException) {
			throw new DatabaseException(
					"Error while accessing the database file.");
		}
		if (DatabaseSchema.EXPECTED_MAGIC_COOKIE_VALUE != magicCookie) {
			throw new DatabaseException(
					"Incompatible database - database supplied cannot be used by this application.");
		}
	}

	/**
	 * Gets all records contained in the database.
	 * 
	 * @return A collection of all the {@code Subcontractors} in a map keyed on
	 *         record numbers based on location in the database
	 * @throws DatabaseException
	 * @throws IOException
	 *             error while accessing the file
	 */
	public ConcurrentSkipListMap<Integer, Subcontractor> getAllRecords()
			throws DatabaseException {
		final ConcurrentSkipListMap<Integer, Subcontractor> result = new ConcurrentSkipListMap<Integer, Subcontractor>();
		int recNo = 1;

		FileAccess.dbReadWriteLock.writeLock().lock();
		try {
			for (long filePosition = DatabaseSchema.EXPECTED_INITIAL_OFFSET; filePosition < FileAccess.database
					.length(); filePosition += DatabaseSchema.TOTAL_FIELD_LENGTH) {
				result.put(recNo, this.readSingleEntry(filePosition));
				recNo++;
			}
		} catch (final IOException ioException) {
			throw new DatabaseException(
					"Error while accessing the database file.");
		} finally {
			FileAccess.dbReadWriteLock.writeLock().unlock();
		}

		return result;
	}

	private Subcontractor readSingleEntry(final long filePosition)
			throws IOException {
		final byte flagByte;
		final byte[] input = new byte[DatabaseSchema.TOTAL_SCHEMA_LENGTH];
		final String[] recordData = new String[DatabaseSchema.FIELD_COUNT];
		synchronized (FileAccess.database) {
			FileAccess.database.seek(filePosition);
			flagByte = FileAccess.database.readByte();
			FileAccess.database.readFully(input);
		}

		class SingleRecordReader {
			private int offSet = 0;

			String read(final int length) throws UnsupportedEncodingException {
				final String string = new String(input, this.offSet, length,
						FileAccess.ENCODING);
				this.offSet += length;
				return string.trim();
			}
		}

		final SingleRecordReader entryReader = new SingleRecordReader();
		final int flag = this.convertByteToInt(flagByte);
		recordData[0] = entryReader.read(DatabaseSchema.FIELD_LENGTH_NAME);
		recordData[1] = entryReader.read(DatabaseSchema.FIELD_LENGTH_LOCATION);
		recordData[2] = entryReader
				.read(DatabaseSchema.FIELD_LENGTH_SPECIALTIES);
		recordData[3] = entryReader.read(DatabaseSchema.FIELD_LENGTH_SIZE);
		recordData[4] = entryReader.read(DatabaseSchema.FIELD_LENGTH_RATE);
		recordData[5] = entryReader.read(DatabaseSchema.FIELD_LENGTH_OWNER);
		final Subcontractor subContractor = new Subcontractor(
				RecordState.forValue(flag), recordData);
		return subContractor;
	}

	private int convertByteToInt(final byte flagByte) {
		return flagByte & 0xFF;
	}

	/**
	 * This method is responsible for saving all data currently stored in the
	 * {@code Data} class cache and persists it into the database
	 * 
	 * @param cache
	 *            the living instance of the records read from the database
	 *            previously but who's state could since have changed.
	 * @throws IOException
	 *             error while accessing the file
	 */
	public void saveAllRecords(
			final ConcurrentSkipListMap<Integer, Subcontractor> cache)
			throws IOException {
		FileAccess.dbReadWriteLock.writeLock().lock();
		try {
			long filePosition = DatabaseSchema.EXPECTED_INITIAL_OFFSET;
			for (final Integer key : cache.keySet()) {
				final Subcontractor contractor = cache.get(key);
				this.writeSingleEntry(filePosition, contractor);
				filePosition += DatabaseSchema.TOTAL_FIELD_LENGTH;
			}
		} finally {
			FileAccess.dbReadWriteLock.writeLock().unlock();
		}
	}

	private void writeSingleEntry(final long filePosition,
			final Subcontractor contractor) throws IOException {
		synchronized (FileAccess.database) {
			FileAccess.database.seek(filePosition);
			final byte flagByte = this.convertIntToByte(contractor.getState()
					.getCode());
			FileAccess.database.writeByte(flagByte);

			final String[] data = contractor.getData();
			for (int i = 0; i < DatabaseSchema.FIELD_COUNT; i++) {
				final int fieldLength = DatabaseSchema.FIELD_LENGTHS[i];
				final byte[] fieldValue = data[i].getBytes(FileAccess.ENCODING);

				/*
				 * write only the fieldLength bytes. if fieldValue is longer,
				 * then skip remaining bytes. if fieldValue is shorter, then
				 * fill to length with blanks (0x020)
				 */
				for (int currentPos = 0; currentPos < fieldLength; currentPos++) {
					if (currentPos < fieldValue.length) {
						FileAccess.database.writeByte(fieldValue[currentPos]);
					} else {
						FileAccess.database.writeByte(0x020);
					}
				}
			}
		}
	}

	private byte convertIntToByte(final Integer integer) {
		final byte flagByte = (byte) (integer >>> 0 * 8);
		return flagByte;
	}

}
