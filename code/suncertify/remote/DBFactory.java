package suncertify.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A factory for creating a DB object. <br>
 * Specifies the methods that may be remotely called on the {@code DBFactory}
 * interface.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface DBFactory extends Remote {

	/**
	 * Key used to bind an instance in an RMI registry.
	 */
	public final static String RMI_KEY = "DBFactory";

	/**
	 * Creates a new instance implementing {@code DBRemote}.
	 * 
	 * @return a DBRemote instance.
	 * @throws RemoteException
	 *             if a networking error has occurred.
	 */
	public DBRemote createRemoteDB() throws RemoteException;
}
