package suncertify.ui.server;

import java.awt.GridBagConstraints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import suncertify.application.LaunchApplication;
import suncertify.ui.BrowseListener;
import suncertify.ui.UICommand;
import suncertify.ui.UserEntryPanel;

/**
 * This class defines the UI components required for retrieving configuration
 * information from the user when launched as a <i>server</i>. Also as inherited
 * from its <i>Superclass</i>, it must handle requests to validate and store
 * user entry as well as disable specified UI components when requested.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ServerUserEntry extends UserEntryPanel implements Observer {

	private static final long serialVersionUID = -8092798004150923429L;

	private static final String SERVER_PORT_LABEL = "Server Port: ";
	private static final String SERVER_PORT_TOOLTIP = "The port on which the server listens for requests";

	private static final String DB_LOCATION_LABEL = "Database location: ";
	private static final String DB_LOCATION_TOOLTIP = "The absolute path of the database file";

	private JTextField dbLocationField;
	private JTextField portNumber;
	private JButton browseButton;

	/** {@inheritDoc} */
	@Override
	public void addComponents() {
		this.dbLocationField = new JTextField(42);
		this.portNumber = new JTextField(30);
		this.browseButton = new JButton(UICommand.BROWSE.getActionCommand());

		final JLabel dbLocationLabel = new JLabel(
				ServerUserEntry.DB_LOCATION_LABEL);
		UserEntryPanel.gridBag.setConstraints(dbLocationLabel,
				UserEntryPanel.constraints);
		this.add(dbLocationLabel);

		this.dbLocationField
				.setToolTipText(ServerUserEntry.DB_LOCATION_TOOLTIP);
		this.dbLocationField.setText(this.applicationProperties
				.getDatabaseLocation());
		UserEntryPanel.constraints.gridwidth = GridBagConstraints.RELATIVE;
		UserEntryPanel.gridBag.setConstraints(this.dbLocationField,
				UserEntryPanel.constraints);
		this.add(this.dbLocationField);

		this.browseButton.setToolTipText(UICommand.BROWSE
				.getActionCommandToolTip());
		final BrowseListener browseListener = new BrowseListener();
		browseListener.addObserver(this);
		this.browseButton.addActionListener(browseListener);
		UserEntryPanel.constraints.gridwidth = GridBagConstraints.REMAINDER;
		UserEntryPanel.gridBag.setConstraints(this.browseButton,
				UserEntryPanel.constraints);
		this.add(this.browseButton);

		final JLabel portNumberLabel = new JLabel(
				ServerUserEntry.SERVER_PORT_LABEL);
		UserEntryPanel.constraints.gridwidth = 1;
		UserEntryPanel.constraints.anchor = GridBagConstraints.EAST;
		UserEntryPanel.gridBag.setConstraints(portNumberLabel,
				UserEntryPanel.constraints);
		this.add(portNumberLabel);

		this.portNumber.setToolTipText(ServerUserEntry.SERVER_PORT_TOOLTIP);
		this.portNumber.setText(this.applicationProperties.getServerPort());
		UserEntryPanel.constraints.gridwidth = GridBagConstraints.REMAINDER;
		UserEntryPanel.constraints.anchor = GridBagConstraints.WEST;
		UserEntryPanel.gridBag.setConstraints(this.portNumber,
				UserEntryPanel.constraints);
		this.add(this.portNumber);
	}

	/** {@inheritDoc} */
	@Override
	public boolean validateUserEntry() {
		boolean result = true;
		if (this.invalidDbLocation(this.dbLocationField.getText())) {
			LaunchApplication
					.showError("The database location entered is not a valid file!");
			result = false;
		}
		if (this.invalidPortNumber(this.portNumber.getText())) {
			LaunchApplication.showError("Error in Port Number entry!"
					+ "\nPort Number must be a numeric value between "
					+ UserEntryPanel.MIN_PORT_RANGE + "-"
					+ UserEntryPanel.MAX_PORT_RANGE);
			result = false;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void storeUserEntry() {
		this.applicationProperties.setDatabaseLocation(this.dbLocationField
				.getText());
		this.applicationProperties.setServerPort(this.portNumber.getText());
	}

	/** {@inheritDoc} */
	@Override
	public void disableControls() {
		this.dbLocationField.setEnabled(false);
		this.portNumber.setEnabled(false);
		this.browseButton.setEnabled(false);
	}

	/** {@inheritDoc} */
	@Override
	public void update(final Observable observable, final Object object) {
		if (object instanceof String) {
			final String updatedDbLocation = (String) object;
			this.dbLocationField.setText(updatedDbLocation);
		}
	}
}
