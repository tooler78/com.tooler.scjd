package suncertify.ui.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import suncertify.application.ApplicationProperties;
import suncertify.db.Data;
import suncertify.db.DatabaseException;
import suncertify.remote.DBFactory;
import suncertify.remote.DBFactoryProvider;

/**
 * The {@code ServerModel} represents data and the rules that govern access to
 * and updates of this data. This {@code Model} needs to manage the different
 * {@link ServerState} based on requests via the {@link ServerController}
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ServerModel implements ServerStateModel {

	private ServerState serverState;

	private final ArrayList<ServerStateListener> listeners = new ArrayList<ServerStateListener>();

	private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

	private static final String REGISTRY_START_ERROR = "Unable to start the RMI registry. Perhaps the port is already in use?";

	/**
	 * Instantiates a new {@link ServerModel} object. On instantiation of
	 * {@code Model}, the initial {@code ServerState} is set to
	 * {@link ServerState#NOT_STARTED}
	 */
	public ServerModel() {
		this.serverState = ServerState.NOT_STARTED;
	}

	/** {@inheritDoc} */
	@Override
	public ServerState getServerState() {
		return this.serverState;
	}

	/** {@inheritDoc} */
	@Override
	public void start() {
		final ServerStateNotification stateNotification = new ServerStateNotification(
				ServerState.STARTED);
		try {
			this.initDBConnectionSuccessful();
			this.registerDBinRmiSuccessful();
		} catch (final DatabaseException databaseException) {
			stateNotification.setServerState(ServerState.FAILED);
			stateNotification.setInfoMessage(databaseException.getMessage());
		} catch (final RemoteException remoteException) {
			stateNotification.setServerState(ServerState.FAILED);
			stateNotification.setInfoMessage(ServerModel.REGISTRY_START_ERROR);
		} finally {
			this.notifyServerStateListeners(stateNotification);
		}
	}

	private void initDBConnectionSuccessful() throws DatabaseException {
		Data.getInstance().initialize(this.applicationProperties.getDatabaseLocation());
	}

	private void registerDBinRmiSuccessful() throws RemoteException {
		final int rmiPort = Integer.parseInt(this.applicationProperties.getServerPort());
		final Registry registry = LocateRegistry.createRegistry(rmiPort);
		registry.rebind(DBFactory.RMI_KEY, new DBFactoryProvider());
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		final ServerStateNotification stateNotification = new ServerStateNotification(
				ServerState.EXIT);
		this.notifyServerStateListeners(stateNotification);
	}

	/** {@inheritDoc} */
	@Override
	public void addServerStateListener(final ServerStateListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void notifyServerStateListeners(final ServerStateNotification stateNotification) {
		this.serverState = stateNotification.getServerState();
		for (final ServerStateListener stateListener : this.listeners) {
			stateListener.handleStateChange(stateNotification);
		}
	}
}
