package suncertify.ui.server;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import suncertify.ui.UICommand;

/**
 * {@code JPanel} containing server specific instructions for the user to carry
 * out initialization of the server component.
 * <p>
 * This class can also be called upon to show success/failure messages once
 * server start-up has been requested by the user.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ServerInfoPanel extends JPanel {

	private static final long serialVersionUID = 2047274943176659649L;

	private static final String HTML_HEADING_START_TAG = "<html><br><b>&nbsp;";
	private static final String HTML_HEADING_END_TAG = "</b><br><br></html>";
	private static final String HTML_INFO_START_TAG = "<html>&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String HTML_INFO_END_TAG = "<br><br></html>";
	private static final String INSTRUCTION_HEADING_TEXT = "Server Settings";
	private static final String INSTRUCTION_INFO_TEXT = "Edit database location and port, and choose '"
			+ UICommand.START.getActionCommand() + "'";
	private static final String SUCCESS_HEADING_TEXT = "<font color='green'>Server successfully started</font>";
	private static final String FAILURE_HEADING_TEXT = "<font color='red'>Server failed to start</font>";

	private final JLabel headingTextLabel = new JLabel();
	private final JLabel infoTextLabel = new JLabel();

	/**
	 * Creates a new instance of {@code ServerInfoPanel} - the panel for showing
	 * initial instructions on configuration options for server startup.
	 */
	public ServerInfoPanel() {
		this.setHeadingText(ServerInfoPanel.INSTRUCTION_HEADING_TEXT);
		this.setInfoText(ServerInfoPanel.INSTRUCTION_INFO_TEXT);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.add(this.headingTextLabel, BorderLayout.LINE_START);
		this.add(this.infoTextLabel, BorderLayout.AFTER_LAST_LINE);
	}

	/**
	 * Sets the message for the {@code JLabel} contained in the
	 * {@link ServerInfoPanel} to <i>Server successfully started</i>.
	 * 
	 * @param infoMessage
	 */
	public void setSuccess(final String infoMessage) {
		this.setHeadingText(ServerInfoPanel.SUCCESS_HEADING_TEXT);
		this.setInfoText(infoMessage);
	}

	/**
	 * Sets the message for the {@code JLabel} contained in the
	 * {@link ServerInfoPanel} to <i>Server failed to start</i>.
	 */
	public void setFailure(final String infoMessage) {
		this.setHeadingText(ServerInfoPanel.FAILURE_HEADING_TEXT);
		this.setInfoText(infoMessage);
	}

	private void setHeadingText(final String headingText) {
		final String htmlFormattedHeading = ServerInfoPanel.HTML_HEADING_START_TAG
				+ headingText + ServerInfoPanel.HTML_HEADING_END_TAG;
		this.headingTextLabel.setText(htmlFormattedHeading);
	}

	private void setInfoText(final String infoText) {
		final String htmlFormattedInfo = ServerInfoPanel.HTML_INFO_START_TAG
				+ infoText + ServerInfoPanel.HTML_INFO_END_TAG;
		this.infoTextLabel.setText(htmlFormattedInfo);
	}
}
