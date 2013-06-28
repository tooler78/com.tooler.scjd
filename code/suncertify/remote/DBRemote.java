package suncertify.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

/**
 * The Interface DBRemote.
 * 
 * Remote interface for network launched clients. This interface needs to be an
 * exact match for the {@link DB} interface stored in the {@code suncertify.db}
 * package. <br>
 * Method signatures are identical except that they can now also have a
 * {@code RemoteException} thrown. This is to allow for RMI interaction from
 * other JVM execution(s).
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface DBRemote extends Remote {

	/**
	 * Reads a record from the file. Returns an array where each element is a
	 * record value.
	 * 
	 * @param recNo
	 *            number of the record to be updated.
	 * @return field values of the record for the given number
	 * @throws RecordNotFoundException
	 *             If a specified record does not exist or is marked as deleted
	 *             in the database file
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public String[] read(int recNo) throws RecordNotFoundException, RemoteException;

	/**
	 * Modifies the fields of a record. The new value for field n appears in
	 * data[n]. Throws SecurityException if the record is locked with a cookie
	 * other than lockCookie.
	 * 
	 * @param recNo
	 *            number of the record to be updated.
	 * @param data
	 *            data to be updated
	 * @param lockCookie
	 *            the lock cookie value
	 * @throws RecordNotFoundException
	 *             If a specified record does not exist or is marked as deleted
	 *             in the database file
	 * @throws SecurityException
	 *             if the record is locked with a cookie other than lockCookie.
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public void update(int recNo, String[] data, long lockCookie) throws RecordNotFoundException,
			SecurityException, RemoteException;

	/**
	 * Deletes a record, making the record number and associated disk storage
	 * available for reuse. Throws SecurityException if the record is locked
	 * with a cookie other than lockCookie.
	 * 
	 * @param recNo
	 *            number of the record to be deleted.
	 * @param lockCookie
	 *            the lock cookie value
	 * @throws RecordNotFoundException
	 *             If a specified record does not exist or is marked as deleted
	 *             in the database file
	 * @throws SecurityException
	 *             if the record is locked with a cookie other than lockCookie
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public void delete(int recNo, long lockCookie) throws RecordNotFoundException,
			SecurityException, RemoteException;

	/**
	 * Returns an array of record numbers that match the specified criteria.
	 * Field n in the database file is described by criteria[n]. A null value in
	 * criteria[n] matches any field value. A non-null value in criteria[n]
	 * matches any field value that begins with criteria[n]. (For example,
	 * "Fred" matches "Fred" or "Freddy".) *
	 * 
	 * @param criteria
	 *            the criteria to be used for finding the applicable record(s)
	 * @return the array of record numbers that match the specified criteria
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public int[] find(String[] criteria) throws RemoteException;

	/**
	 * Creates a new record in the database (possibly reusing a deleted entry).
	 * Inserts the given data, and returns the record number of the new record.
	 * 
	 * @param data
	 *            the new data record
	 * @return the record number of the new record
	 * @throws DuplicateKeyException
	 *             If a specified record already exists with the same key
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public int create(String[] data) throws DuplicateKeyException, RemoteException;

	/**
	 * Locks a record so that it can only be updated or deleted by this client.
	 * Returned value is a cookie that must be used when the record is unlocked,
	 * updated, or deleted. If the specified record is already locked by a
	 * different client, the current thread gives up the CPU and consumes no CPU
	 * cycles until the record is unlocked.
	 * 
	 * @param recNo
	 *            number of the record to be locked.
	 * @return the lock cookie value
	 * @throws RecordNotFoundException
	 *             If a specified record does not exist or is marked as deleted
	 *             in the database file
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public long lock(int recNo) throws RecordNotFoundException, RemoteException;

	/**
	 * Releases the lock on a record. Cookie must be the cookie returned when
	 * the record was locked; otherwise throws SecurityException. *
	 * 
	 * @param recNo
	 *            number of the record to be unlocked.
	 * @param cookie
	 *            the lock cookie value
	 * @throws RecordNotFoundException
	 *             If a specified record does not exist or is marked as deleted
	 *             in the database file
	 * @throws SecurityException
	 *             if the record is locked with a cookie other than cookie.
	 * @throws RemoteException
	 *             if a networking error has occurred
	 */
	public void unlock(int recNo, long cookie) throws RecordNotFoundException, SecurityException,
			RemoteException;
}
