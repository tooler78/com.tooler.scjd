package suncertify.db.lock;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Holds information about the lock on a record, i.e. whether it is locked and
 * the current lock cookie value.
 * <p>
 * Provides methods to lock and unlock a single <code>Subcontractor</code>.
 * <p>
 * When a thread tries to acquire a lock that is already locked, it is blocked
 * on an internal condition variable.
 * <p>
 * When a lock is released, threads waiting for its release are notified via the
 * condition variable.
 * 
 * @version 1.0
 * @author Damien O'Toole
 * 
 */
public class LockInformation {

	private static final Random RANDOM = new Random();

	private final Lock lock = new ReentrantLock();

	private long cookie;

	private boolean locked;

	private final Condition lockReleased = this.lock.newCondition();

	/**
	 * Creates a new {@link LockInformation} instance.
	 */
	public LockInformation() {
		this.cookie = 0;
		this.locked = false;
	}

	/**
	 * locks the record.
	 * <p>
	 * If the lock is not held by any thread, it is locked and a new cookie
	 * value is generated and returned.
	 * <p>
	 * If the lock is already held, the current thread waits on the condition
	 * variable until it is signaled that the lock is available. After being
	 * woken up, the thread tries again to get the lock, until it is successful.
	 */
	public long lock() {
		this.lock.lock();
		try {
			while (this.locked) {
				this.lockReleased.awaitUninterruptibly();
			}
			this.locked = true;
			this.cookie = LockInformation.RANDOM.nextLong();

			return this.cookie;
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * unlocks the record.
	 * <p>
	 * Once record is unlocked, a notification is sent for any thread waiting on
	 * the lock to now retry to lock the record.
	 * 
	 * @param cookie
	 *            cookie value to authorize the unlock
	 * @throws SecurityException
	 *             if the record is locked with a cookie other than the cookie
	 *             supplied
	 */
	public void unlock(final long cookie) throws SecurityException {
		this.lock.lock();
		try {
			if (this.cookie != cookie) {
				// can't release the lock as not owner (wrong cookie value)
				throw new SecurityException("Invalid cookie value");
			} else {
				this.locked = false;
				this.cookie = 0;

				// notify waiting threads about this lock's release
				this.lockReleased.signal();
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Returns the current cookie value.
	 * 
	 * @return the current cookie value
	 */
	public long getCookie() {
		return this.cookie;
	}
}
