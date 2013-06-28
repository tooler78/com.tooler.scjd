package suncertify.application;

import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import suncertify.db.DB;
import suncertify.ui.client.ClientController;
import suncertify.ui.client.ClientModel;
import suncertify.ui.client.ClientStartUpDialog;
import suncertify.ui.client.SubcontractorController;
import suncertify.ui.client.SubcontractorModel;
import suncertify.ui.server.ServerController;
import suncertify.ui.server.ServerModel;
import suncertify.ui.server.ServerStateController;
import suncertify.ui.server.ServerStateModel;

/**
 * A facade to the three modes the application can run in. This class will check
 * the command line arguments and then call the classes to start the application
 * in the correct {@link LaunchMode}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class LaunchApplication {

	private final String[] cmdLineArgs;

	private static LaunchMode launchMode;

	/**
	 * LaunchApplication constructor.
	 * <p>
	 * Inspect the arguments passed in when starting the application and
	 * configures the application's launch mode and the native system look and
	 * feel if there is one.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public LaunchApplication(final String[] args) {
		this.cmdLineArgs = args;
		this.configureApplicationMode();
		this.setUILookAndFeel();
	}

	private void configureApplicationMode() {
		if (this.cmdLineArgs.length == 0) {
			// when no argument is supplied, default assumption is that this
			// should be a network client
			LaunchApplication.launchMode = LaunchMode.NETWORK_CLIENT;
		} else if (this.cmdLineArgs.length == 1) {
			final String suppliedcmdLineArg = this.cmdLineArgs[0];
			for (final LaunchMode mode : LaunchMode.values()) {
				if (suppliedcmdLineArg.equalsIgnoreCase(mode.getCmdLineArg())) {
					LaunchApplication.launchMode = mode;
				}
			}
			if (LaunchApplication.launchMode == null) {
				/*
				 * The singular command line argument supplied has not
				 * corresponded to any of the supported LaunchMode
				 */
				this.displayUsageAndExit();
			}
		} else {
			// more than one argument has been supplied which is not supported
			this.displayUsageAndExit();
		}
	}

	private void displayUsageAndExit() {
		System.err.println("Invalid parameter(s) passed in when starting the application: "
				+ Arrays.toString(this.cmdLineArgs));
		System.err.println("Command line options may be one of:");
		System.err.println("\"server\" - starts server application");
		System.err.println("\"alone\"  - starts non-networked client");
		System.err.println("\"\"       - (no command line option): "
				+ "networked client will start");
		System.exit(0);
	}

	private void setUILookAndFeel() {
		final String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (final Exception canBeIgnored) {
			// Exception can be ignored as the UI will simply revert to default.
		}
	}

	/**
	 * The main method. The method that launches the application.
	 * 
	 * @param args
	 *            the command line argument(s)
	 */
	public static void main(final String args[]) {
		final LaunchApplication applicationLaunch = new LaunchApplication(args);
		Runtime.getRuntime().addShutdownHook(new GracefulShutdownHook());
		applicationLaunch.start();
	}

	private void start() {
		switch (LaunchApplication.launchMode) {
		case SERVER:
			this.startServer();
			break;
		case STANDALONE_CLIENT:
		case NETWORK_CLIENT:
			this.startClient();
			break;
		}
	}

	private void startServer() {
		final ServerStateModel serverModel = new ServerModel();
		final ServerStateController serverController = new ServerController(serverModel);
		serverController.displayView();
	}

	private void startClient() {
		final ClientStartUpDialog startUpDialog = new ClientStartUpDialog();
		startUpDialog.displayDialog();
		final DB dao = DAOFactory.getDAO(LaunchApplication.launchMode);
		final DBService dbService = new DBService(dao);
		final SubcontractorModel model = new ClientModel(dbService);
		final SubcontractorController controller = new ClientController(model);
		controller.displayView();
	}

	/**
	 * Returns the launch mode the application is running.
	 * 
	 * @return launch mode
	 */
	public static LaunchMode getLaunchMode() {
		return LaunchApplication.launchMode;
	}

	/**
	 * Prompts the user with an error message in an alert window. Once
	 * acknowledged by the user, the application will exit.
	 * 
	 * @param message
	 *            the error message
	 */
	public static void showErrorAndExit(final String message) {
		final JDialog dialog = LaunchApplication.createErrorDialog(message);
		dialog.setVisible(true);
		System.exit(0);
	}

	/**
	 * Prompts the user with an error message in an alert window.
	 * 
	 * @param message
	 *            the error message
	 */
	public static void showError(final String message) {
		final JDialog dialog = LaunchApplication.createErrorDialog(message);
		dialog.setVisible(true);
	}

	private static JDialog createErrorDialog(final String message) {
		final JOptionPane alert = new JOptionPane(message, JOptionPane.ERROR_MESSAGE,
				JOptionPane.DEFAULT_OPTION);
		final JDialog dialog = alert.createDialog(null, "Error");

		// Center on screen
		dialog.setLocationRelativeTo(null);

		return dialog;
	}
}
