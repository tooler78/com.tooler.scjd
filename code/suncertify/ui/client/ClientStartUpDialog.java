package suncertify.ui.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import suncertify.application.ApplicationProperties;
import suncertify.application.LaunchApplication;
import suncertify.application.LaunchMode;
import suncertify.ui.UICommand;
import suncertify.ui.UserEntryPanel;

/**
 * Provides a dialog that displays the client properties retrieved via the the
 * {@link ApplicationProperties} and allows the user to make changes.
 * <p>
 * The properties provide by the user are validated and stored in the OCMJD
 * specified properties file <i>suncertify.properties</i> via the
 * {@code ApplicationProperties}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ClientStartUpDialog extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1160816246329515659L;

	private final UserEntryPanel userEntryPanel;

	private final JButton operationButton = new JButton();
	private final JButton exitButton = new JButton();

	private JDialog dialog;

	/**
	 * Instantiates a new client dialog which displays the relevant properties
	 * based on the {@link LaunchMode} of the application.
	 */
	public ClientStartUpDialog() {
		final LaunchMode launchMode = LaunchApplication.getLaunchMode();
		this.userEntryPanel = this.getUserEntryPanel(launchMode);
		this.add(this.userEntryPanel, BorderLayout.CENTER);
		if (launchMode == LaunchMode.NETWORK_CLIENT) {
			this.setupButton(this.operationButton, UICommand.CONNECT);
		} else {
			this.setupButton(this.operationButton, UICommand.START);
		}
		this.setupButton(this.exitButton, UICommand.EXIT);
	}

	private UserEntryPanel getUserEntryPanel(final LaunchMode launchMode) {
		if (launchMode == LaunchMode.NETWORK_CLIENT) {
			return new NetworkClientUserEntry();
		} else {
			return new StandaloneUserEntry();
		}
	}

	private void setupButton(final JButton button, final UICommand command) {
		button.addActionListener(this);
		button.setText(command.getActionCommand());
		button.setToolTipText(command.getActionCommandToolTip());
		button.setMnemonic(command.getKeyEvent());
	}

	/**
	 * Invoked when an action occurs in the dialog via the action command
	 * buttons.
	 */
	@Override
	public void actionPerformed(final ActionEvent actionEvent) {
		final String command = actionEvent.getActionCommand();
		if (command.equals(this.operationButton.getActionCommand())) {
			this.dialog.pack();
			if (this.userEntryPanel.validateUserEntry()) {
				this.userEntryPanel.storeUserEntry();
				this.dialog.dispose();
			}
		} else {
			// Exit button has been selected
			System.exit(0);
		}
	}

	/**
	 * Display the dialog.
	 */
	public void displayDialog() {
		final JOptionPane pane = new JOptionPane(this,
				JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		this.dialog = pane.createDialog("Client Properties");
		this.dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		pane.setOptions(new Object[] { this.exitButton, this.operationButton });

		this.dialog.setVisible(true);
	}
}
