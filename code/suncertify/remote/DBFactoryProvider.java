package suncertify.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The implementation of {@link DBFactory} for networked client connectivity.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class DBFactoryProvider extends UnicastRemoteObject implements DBFactory {

	public DBFactoryProvider() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = -6428906753316731501L;

	/** {@inheritDoc} */
	@Override
	public DBRemote createRemoteDB() throws RemoteException {
		return new DBRemoteProvider();
	}

}
