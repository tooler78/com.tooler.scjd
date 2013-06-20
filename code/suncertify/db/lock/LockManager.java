package suncertify.db.lock;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to manage locks on a given record.
 * <p>
 * The operations to lock and unlock a record are delegated by the Data
 * implementation to an instance of this class. Internally, lock information is
 * stored in a static hashmap, mapping record numbers to lock entries.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class LockManager {

	private static ConcurrentHashMap<Integer, LockInformation> lockRecordMap = new ConcurrentHashMap<Integer, LockInformation>();

	/**
	 * locks a record with the given record number and returns a unique
	 * <i>cookie</i> value of the lock.
	 * <p>
	 * This method blocks until the lock succeeds.Threads trying to lock are
	 * synchronized on the corresponding {@link LockInformation} instance.
	 * <p>
	 * i.e. if a lock is already held by another thread the calling thread
	 * suspends execution and must be notified when the lock is released.
	 * 
	 * @param recordNo
	 *            the record number
	 * @return <i>cookie</i> value of the lock after it is successfully locked
	 */
	public long lock(final int recordNo) {
		final LockInformation lockRecord = this.getLockRecord(recordNo);
		final long cookie = lockRecord.lock();

		return cookie;
	}

	/**
	 * Unlock a record for a given record number. To unlock a previously locked
	 * record the <i>cookie</i> must match the locked record.
	 * 
	 * @param recordNo
	 *            the record number
	 * @param cookie
	 *            <i>cookie</i> value of the lock
	 * @throws SecurityException
	 *             if the record is locked with a <i>cookie</i> other than the
	 *             supplied <i>cookie</i>
	 */
	public void unlock(final int recordNo, final long cookie)
			throws SecurityException {
		final LockInformation lockRecord = this.getLockRecord(recordNo);
		lockRecord.unlock(cookie);
	}

	/**
	 * Checks if the record with the specified number is locked with the
	 * specified cookie value. If the correct <i>cookie</i> value is used, the
	 * method simply returns. If the wrong <i>cookie</i> value is supplied, a
	 * {@code SecurityException} is thrown.
	 * 
	 * @param recordNo
	 *            the record number
	 * @param cookie
	 *            the <i>cookie</i> value
	 * @throws SecurityException
	 *             if the record is locked with a <i>cookie</i> other than the
	 *             supplied <i>cookie</i>
	 */
	public void checkCookie(final int recordNo, final long cookie)
			throws SecurityException {
		LockInformation lockRecord = new LockInformation();
		lockRecord = LockManager.lockRecordMap
				.putIfAbsent(recordNo, lockRecord);
		if (lockRecord.getCookie() != cookie) {
			throw new SecurityException("Invalid cookie value");
		}
	}

	private LockInformation getLockRecord(final int recordNo) {
		final LockInformation lockRecord = new LockInformation();
		LockManager.lockRecordMap.putIfAbsent(recordNo, lockRecord);
		return LockManager.lockRecordMap.get(recordNo);

	}
}
