package suncertify.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.db.DB;
import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

/**
 * The Class DBRemoteProvider is an implementation of the {@link DBRemote}
 * interface. This class is an RMI implementation.
 * 
 * This class references the {@link Data} object, who interacts with our
 * database. This class acts as a wrapper for our database interaction.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public final class DBRemoteProvider extends UnicastRemoteObject implements DBRemote {

	private static final long serialVersionUID = -6199759573206229884L;

	private transient final DB db;

	/**
	 * Constructor for the RMI implementation of the {@link DBRemote} interface.
	 * <p>
	 * Gets a reference to our database via the {@link Data} object.
	 * 
	 * @throws RemoteException
	 *             the remote exception
	 */
	public DBRemoteProvider() throws RemoteException {
		this.db = Data.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String[] read(final int recNo) throws RecordNotFoundException {
		return this.db.read(recNo);
	}

	/** {@inheritDoc} */
	@Override
	public void update(final int recNo, final String[] data, final long lockCookie)
			throws RecordNotFoundException, SecurityException {
		this.db.update(recNo, data, lockCookie);
	}

	/** {@inheritDoc} */
	@Override
	public void delete(final int recNo, final long lockCookie) throws RecordNotFoundException,
			SecurityException {
		this.db.delete(recNo, lockCookie);
	}

	/** {@inheritDoc} */
	@Override
	public int[] find(final String[] criteria) {
		return this.db.find(criteria);
	}

	/** {@inheritDoc} */
	@Override
	public int create(final String[] data) throws DuplicateKeyException {
		return this.db.create(data);
	}

	/** {@inheritDoc} */
	@Override
	public long lock(final int recNo) throws RecordNotFoundException {
		return this.db.lock(recNo);
	}

	/** {@inheritDoc} */
	@Override
	public void unlock(final int recNo, final long cookie) throws RecordNotFoundException,
			SecurityException {
		this.db.unlock(recNo, cookie);
	}

}
