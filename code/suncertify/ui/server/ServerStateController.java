package suncertify.ui.server;

/**
 * The {@code Controller} translates the user's interactions with the
 * {@link ServerView} into actions that the {@link ServerStateModel} will
 * perform.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface ServerStateController {

	/**
	 * Displays the {@link ServerView} object associated with this
	 * {@code Controller} instance.
	 */
	public void displayView();

	/**
	 * Starts the server application via interaction with the
	 * {@link ServerStateModel}.
	 */
	public void startServer();

	/**
	 * Stops the server application via interaction with the
	 * {@link ServerStateModel} .
	 */
	public void stopServer();

	/**
	 * {@code Controller} will receive a request via the associated
	 * {@link ServerView} based on the {@link ServerState} changes received from
	 * the {@link ServerStateModel}.
	 * 
	 * @param stateNotification
	 *            the {@code ServerStateNotification} containing the current
	 *            {@code ServerState} of the {@code Model}
	 */
	public void stateChanged(final ServerStateNotification stateNotification);
}
