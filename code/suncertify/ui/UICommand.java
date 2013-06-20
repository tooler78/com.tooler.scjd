package suncertify.ui;

import java.awt.event.KeyEvent;

/**
 * Commands that can be used by the package {@code suncertify.ui} or relevant
 * sub-packages. Each element has an associated <i>ActionCommand</i> value that
 * can be used by {@code ActionListener} instances to identify an event source.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */

public enum UICommand {

	/** Command to book a {@code Subcontractor} for a customer. */
	BOOK("Book", "Book the Subcontractor for the specified customer ID",
			KeyEvent.VK_O),
	/** Command to browse for a file location. */
	BROWSE("Browse...", "Browse the file system", KeyEvent.VK_B),
	/** Command to clear the user text entry from a component. */
	CLEAR("Clear", "Clear user text entry", KeyEvent.VK_C),
	/** Command to connect a networked client to a remote server. */
	CONNECT("Connect", "Connect to the server remotely", KeyEvent.VK_C),
	/** Command to display all the {@code Subcontractors}. */
	DISPLAYALL("Display All", "Display all the Subcontractors", KeyEvent.VK_D),
	/** Command to exit the application. */
	EXIT("Exit", "Exit the application", KeyEvent.VK_X),
	/** Command to search the database. */
	SEARCH("Search", "Search the database for criteria specified",
			KeyEvent.VK_S),
	/** Command to start the application. */
	START("Start", "Start the application", KeyEvent.VK_T);

	private final String actionCommand;
	private final String actionCmdToolTip;
	private final int keyEvent;

	/**
	 * Constructor for a {@link UICommand}. Allows for an action command and
	 * corresponding tool tip value to be set.
	 * 
	 * @param actionCommand
	 *            the action command text
	 * @param actionCmdToolTip
	 *            the action command tool tip text
	 * @param keyEvent
	 *            integer value for the key event represented by this command
	 */
	UICommand(final String actionCommand, final String actionCmdToolTip,
			final int keyEvent) {
		this.actionCommand = actionCommand;
		this.actionCmdToolTip = actionCmdToolTip;
		this.keyEvent = keyEvent;
	}

	/**
	 * Returns the {@code ActionCommand} value associated with this
	 * {@code UICommand}.
	 * 
	 * @return associated {@code UICommand} value
	 */
	public String getActionCommand() {
		return this.actionCommand;
	}

	/**
	 * Gets the {@code ActionCommand} <i>ToolTip</i> associated with this
	 * {@code UICommand}.
	 * 
	 * @return associated {@code UICommand} <i>ToolTip</i>
	 */
	public String getActionCommandToolTip() {
		return this.actionCmdToolTip;
	}

	/**
	 * Gets the {@code ActionCommand} <i>KeyEvent</i> associated with this
	 * {@code UICommand}.
	 * 
	 * @return associated {@code UICommand} <i>KeyEvent</i>
	 */
	public int getKeyEvent() {
		return this.keyEvent;
	}
}
