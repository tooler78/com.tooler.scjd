package suncertify.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JPanel;

import suncertify.application.ApplicationProperties;
import suncertify.application.LaunchApplication;
import suncertify.application.LaunchMode;

/**
 * Irrespective of the {@link LaunchMode}, configuration input is required from
 * the user before starting the given application. For every {@code LaunchMode}
 * there should be a <i>Subclass</i> implementation for specific implementation
 * of the abstract methods associated with this class.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public abstract class UserEntryPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2632637828424541950L;

	/**
	 * The Constant gridBag. {@code GridBagLayout} layout manager for laying out
	 * UI components in this {@code JPanel}.
	 */
	protected static final GridBagLayout gridBag = new GridBagLayout();

	/**
	 * The Constant constraints. {@code GridBagConstraints} specifies
	 * constraints for components and should be used in association with a
	 * {@code GridBagLayout} class.
	 */
	protected static final GridBagConstraints constraints = new GridBagConstraints();

	/**
	 * The Constant DATABASE_CHOOSER_DESCRIPTION text to be used in a.
	 * {@code JFileChooser} dialog to allow for user tips on expected database
	 * file input.
	 */
	protected static final String DATABASE_CHOOSER_DESCRIPTION = "Database files (*."
			+ ApplicationProperties.DATABASE_EXTENSION + ")";

	/** The {@link LaunchMode} that this running application was launched in. */
	protected final LaunchMode launchMode = LaunchApplication.getLaunchMode();

	/** The {@link ApplicationProperties} of this running application. */
	protected final ApplicationProperties applicationProperties = ApplicationProperties
			.getInstance();

	/**
	 * Creates a {@code JPanel} that contains a number of components to allow a
	 * user to configure the application prior to executing a {@link UICommand}.
	 * The components to be added will be done by the <i>Subclass's</i>.
	 */
	public UserEntryPanel() {
		this.setLayout(UserEntryPanel.gridBag);
		UserEntryPanel.constraints.insets = new Insets(2, 2, 2, 2);
		UserEntryPanel.constraints.anchor = GridBagConstraints.LINE_START;
		this.addComponents();
	}

	/**
	 * Verify if the database location is an existing file with the extension of
	 * <i>.db</i>.
	 * 
	 * @param dbLocation
	 *            the absolute path of a database location
	 * @return true, if database location is invalid
	 */
	protected boolean invalidDbLocation(final String dbLocation) {
		boolean result = true;
		final File file = new File(dbLocation);
		if (dbLocation.endsWith(ApplicationProperties.DATABASE_EXTENSION) && file.exists()) {
			result = false;
		}
		return result;
	}

	/**
	 * Verify if the port number is an {@code Integer} between 0 and 65535.
	 * 
	 * @param portNumber
	 *            the port number to be verified
	 * @return true, if the port number is an {@code Integer} between 0 and
	 *         65535.
	 */
	protected boolean invalidPortNumber(final String portNumber) {
		boolean result = true;
		try {
			final int port = Integer.parseInt(portNumber);
			if (port >= ApplicationProperties.MIN_PORT_RANGE
					&& port <= ApplicationProperties.MAX_PORT_RANGE) {
				result = false;
			}
		} catch (final NumberFormatException e) {
			result = true;
		}
		return result;
	}

	/**
	 * Adds any GUI components to the {@code JPanel} which are specific to the
	 * {@link LaunchMode}.
	 */
	public abstract void addComponents();

	/**
	 * Validate user entry of required configuration information. Depending on
	 * the {@code LaunchMode} differing controls require validation.
	 * 
	 * @return true, if successful
	 */
	public abstract boolean validateUserEntry();

	/**
	 * Stores the user configuration information entered in the different GUI
	 * components in the {@link ApplicationProperties} object.
	 */
	public abstract void storeUserEntry();

	/**
	 * Disable all controls being rendered in this {@code UserEntryPanel}.
	 */
	public abstract void disableControls();
}
