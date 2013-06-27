package suncertify.application;

import java.rmi.Naming;

import suncertify.db.DB;
import suncertify.db.Data;
import suncertify.db.DatabaseException;
import suncertify.remote.DBFactory;
import suncertify.remote.DBRemote;
import suncertify.remote.DBRemoteAdapter;

/**
 * A factory for creating a <i>Data Access Object</i> or <i>DAO</i>.
 * <p>
 * This factory will create either a local or remote instance based on this
 * applications {@link LaunchMode}. The DAO returned (whether local or remote)
 * will be an implementation of the {@link DB} interface.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public final class DAOFactory {

	private static final String REMOTE_ACCESS_ERROR = "Failed to connect to the Remote Server Application."
			+ "\nPerhaps the server is not running on the server name/port combination entered?";

	/**
	 * private constructor to prevent creation of a {@code DAOFactory} instance.
	 */
	private DAOFactory() {
	}

	private static ApplicationProperties applicationProperties = ApplicationProperties
			.getInstance();

	private static DB dbInstance;

	/**
	 * Gets the <i>DAO</i> object which is an instance of the {@link DB}
	 * interface.
	 * 
	 * @param launchMode
	 *            the {@link LaunchMode} of this running application instance
	 * @return the {@code DAO} object that will interact with the database via
	 *         the {@code DB} interface
	 */
	public static DB getDAO(final LaunchMode launchMode) {
		if (launchMode == LaunchMode.STANDALONE_CLIENT) {
			DAOFactory.dbInstance = DAOFactory.getLocalDBInstance();
		} else {
			DAOFactory.dbInstance = DAOFactory.getRemoteDBInstance();
		}
		return DAOFactory.dbInstance;
	}

	private static DB getLocalDBInstance() {
		try {
			Data.getInstance().initialize(
					DAOFactory.applicationProperties.getDatabaseLocation());
		} catch (final DatabaseException databaseException) {
			LaunchApplication.showErrorAndExit(databaseException.getMessage());
		}
		return Data.getInstance();
	}

	private static DB getRemoteDBInstance() {
		final String url = "rmi://"
				+ DAOFactory.applicationProperties.getServerAddress() + ":"
				+ DAOFactory.applicationProperties.getServerPort() + "/"
				+ DBFactory.RMI_KEY;
		try {
			final DBFactory factory = (DBFactory) Naming.lookup(url);
			final DBRemote dbRemote = factory.createRemoteDB();
			DAOFactory.dbInstance = new DBRemoteAdapter(dbRemote);
		} catch (final Exception exception) {
			LaunchApplication.showErrorAndExit(DAOFactory.REMOTE_ACCESS_ERROR);
		}
		return DAOFactory.dbInstance;
	}

}
