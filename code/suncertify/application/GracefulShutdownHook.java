package suncertify.application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import suncertify.db.Data;
import suncertify.db.DatabaseException;
import suncertify.remote.DBFactory;

/**
 * The {@code run} method of this class will be executed when the application is
 * shut down by the user, performing any necessary actions to ensure that the
 * application stops correctly.
 * <p>
 * 
 * This class should be registered with the JVM to run when the application is
 * shutting down by calling {@code Runtime.addShutdownHook} with an instance of
 * this class as a parameter.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class GracefulShutdownHook extends Thread {

	private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

	/**
	 * Signals the components of this application to shut down and release any
	 * used resources. Depending on the {@code LaunchMode}, the following tasks
	 * are currently required:
	 * <ul>
	 * <li>{@link LaunchMode#SERVER} - Remove entry in the RMI registry and save
	 * any updates into the db.</li>
	 * <li>{@link LaunchMode#STANDALONE_CLIENT} - Save any updates into the db.</li>
	 * <li>{@link LaunchMode#NETWORK_CLIENT} - No task is currently required on
	 * shutdown</li>
	 * </ul>
	 */
	@Override
	public void run() {
		final LaunchMode launchMode = LaunchApplication.getLaunchMode();
		if (launchMode.isServer()) {
			this.disconnectRmi();
		}

		if (launchMode.isServer() || launchMode.isStandalone()) {
			this.destroyData();
		}
	}

	private void disconnectRmi() {
		final int rmiPort = Integer.parseInt(this.applicationProperties.getServerPort());
		try {
			final Registry registry = LocateRegistry.getRegistry(rmiPort);
			registry.unbind(DBFactory.RMI_KEY);
		} catch (final RemoteException canBeIgnored) {
			// Can be ignored, because even though we cannot get a reference to
			// the registry, the server is about to be shutdown
		} catch (final NotBoundException canBeIgnored) {
			// Can be ignored, because the factory is already unbound.
		}
	}

	private void destroyData() {
		try {
			Data.getInstance().destroy();
		} catch (final DatabaseException databaseException) {
			LaunchApplication.showErrorAndExit(databaseException.getMessage());
		}
	}

}
