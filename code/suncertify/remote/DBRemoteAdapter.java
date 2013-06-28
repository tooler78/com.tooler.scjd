package suncertify.remote;

import java.rmi.RemoteException;

import suncertify.db.CommunicationException;
import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

/**
 * Adapter that wraps a {@link DBRemote} instance and exposes its methods behind
 * the corresponding {@link DB} methods.
 * <p>
 * {@code RemoteException} instances thrown by {@code DBRemote} are caught and
 * re-thrown as a {@link CommunicationException}
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class DBRemoteAdapter implements DB {

	private final DBRemote dbRemote;

	/**
	 * Creates a new RemoteAdapter instance wrapping the specified
	 * {@code DBRemote} instance.
	 * 
	 * @param dbRemote
	 *            wrapped {@code DBRemote} instance
	 */
	public DBRemoteAdapter(final DBRemote dbRemote) {
		this.dbRemote = dbRemote;
	}

	@Override
	/** {@inheritDoc} */
	public String[] read(final int recNo) throws RecordNotFoundException {
		try {
			return this.dbRemote.read(recNo);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke read remotely", remoteException);
		}
	}

	@Override
	/** {@inheritDoc} */
	public void update(final int recNo, final String[] data, final long lockCookie)
			throws RecordNotFoundException, SecurityException {
		try {
			this.dbRemote.update(recNo, data, lockCookie);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke update remotely", remoteException);
		}
	}

	@Override
	/** {@inheritDoc} */
	public void delete(final int recNo, final long lockCookie) throws RecordNotFoundException,
			SecurityException {
		try {
			this.dbRemote.delete(recNo, lockCookie);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke delete remotely", remoteException);
		}
	}

	@Override
	/** {@inheritDoc} */
	public int[] find(final String[] criteria) {
		try {
			return this.dbRemote.find(criteria);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke find remotely", remoteException);
		}
	}

	@Override
	/** {@inheritDoc} */
	public int create(final String[] data) throws DuplicateKeyException {
		try {
			return this.dbRemote.create(data);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke create remotely", remoteException);
		}
	}

	@Override
	/** {@inheritDoc} */
	public long lock(final int recNo) throws RecordNotFoundException {
		try {
			return this.dbRemote.lock(recNo);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke lock remotely", remoteException);
		}
	}

	@Override
	/** {@inheritDoc} */
	public void unlock(final int recNo, final long cookie) throws RecordNotFoundException,
			SecurityException {
		try {
			this.dbRemote.unlock(recNo, cookie);
		} catch (final RemoteException remoteException) {
			throw new CommunicationException("Cannot invoke unlock remotely", remoteException);
		}
	}

}
