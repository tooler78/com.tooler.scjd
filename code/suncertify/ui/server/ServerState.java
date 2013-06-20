package suncertify.ui.server;

/**
 * The {@code enum} representing the different states that the server instance
 * can hold.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public enum ServerState {

	/** The server has attempted to exit the application. */
	EXIT,
	/** The server has attempted to start but failed. */
	FAILED,
	/** The server has not been started. */
	NOT_STARTED,
	/** The server has started and is now operational and ready for use. */
	STARTED;
}