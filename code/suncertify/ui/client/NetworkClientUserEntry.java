package suncertify.ui.client;

import java.awt.GridBagConstraints;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JLabel;
import javax.swing.JTextField;

import suncertify.application.LaunchApplication;
import suncertify.ui.UserEntryPanel;

/**
 * This class defines the UI components required for retrieving configuration
 * information from the user when launched as a <i>networked client</i>. Also as
 * inherited from its <i>Superclass</i>, it must handle requests to validate and
 * store user entry as well as disable specified UI components when requested.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class NetworkClientUserEntry extends UserEntryPanel {

	private static final long serialVersionUID = -1056039300933266019L;

	private static final String SERVER_ADDRESS_LABEL = "Server Address: ";
	private static final String SERVER_ADDRESS_TOOLTIP = "The server name where the application is currently running in server mode";

	private static final String SERVER_PORT_LABEL = "Server Port: ";
	private static final String SERVER_PORT_TOOLTIP = "The port on which the server listens for requests";

	private JTextField serverAddressField;
	private JTextField portNumber;

	/** {@inheritDoc} */
	@Override
	public void addComponents() {
		this.serverAddressField = new JTextField(30);
		this.portNumber = new JTextField(30);

		final JLabel serverNameLabel = new JLabel(
				NetworkClientUserEntry.SERVER_ADDRESS_LABEL);
		UserEntryPanel.gridBag.setConstraints(serverNameLabel,
				UserEntryPanel.constraints);
		this.add(serverNameLabel);

		this.serverAddressField
				.setToolTipText(NetworkClientUserEntry.SERVER_ADDRESS_TOOLTIP);
		this.serverAddressField.setText(this.applicationProperties
				.getServerAddress());
		UserEntryPanel.constraints.gridwidth = GridBagConstraints.REMAINDER;
		UserEntryPanel.gridBag.setConstraints(this.serverAddressField,
				UserEntryPanel.constraints);
		this.add(this.serverAddressField);

		final JLabel portNumberLabel = new JLabel(
				NetworkClientUserEntry.SERVER_PORT_LABEL);
		UserEntryPanel.constraints.gridwidth = 1;
		UserEntryPanel.constraints.anchor = GridBagConstraints.EAST;
		UserEntryPanel.gridBag.setConstraints(portNumberLabel,
				UserEntryPanel.constraints);
		this.add(portNumberLabel);

		this.portNumber
				.setToolTipText(NetworkClientUserEntry.SERVER_PORT_TOOLTIP);
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
		if (this.invalidServerAddress()) {
			LaunchApplication
					.showError("Server Address entered is not contactable!");
			result = false;
		} else if (this.invalidPortNumber(this.portNumber.getText())) {
			LaunchApplication.showError("Error in Port Number entry!"
					+ "\nPort Number must be a numeric value between "
					+ UserEntryPanel.MIN_PORT_RANGE + "-"
					+ UserEntryPanel.MAX_PORT_RANGE);
			result = false;
		}
		return result;
	}

	private boolean invalidServerAddress() {
		try {
			final String addressOrIp = this.serverAddressField.getText();
			InetAddress.getByName(addressOrIp);
			return false;
		} catch (final IOException ioException) {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void storeUserEntry() {
		this.applicationProperties.setServerAddress(this.serverAddressField
				.getText());
		this.applicationProperties.setServerPort(this.portNumber.getText());
	}

	/** {@inheritDoc} */
	@Override
	public void disableControls() {
		this.serverAddressField.setEnabled(false);
		this.portNumber.setEnabled(false);
	}

}
