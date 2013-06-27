package suncertify.ui.server;

import suncertify.application.LaunchMode;

/**
 * The interface {@code Model} is used to represent the supported interaction
 * and states to model the application when launched in
 * {@link LaunchMode#SERVER}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface ServerStateModel {

	/**
	 * Start the server instance. Once started the {@link ServerState} should be
	 * one of the following:
	 * <ul>
	 * <li>{@link ServerState#FAILED}</li>
	 * <li>{@link ServerState#STARTED}</li>
	 * </ul>
	 */
	public void start();

	/**
	 * Stop the server instance. On completion, the {@link ServerState} should
	 * be set to {@link ServerState#NOT_STARTED}.
	 */
	public void stop();

	/**
	 * Gets the current {@link ServerState} from this {@code ServerStateModel} .
	 * 
	 * @return the current {@code ServerState} of this {@code ServerStateModel}
	 */
	public ServerState getServerState();

	/**
	 * Register instances of {@link ServerView} to be notified of any changes in
	 * the {@code ServerState}.
	 * 
	 * @param listener
	 *            the {@code ServerStateListener} who registers for future
	 *            notification of {@code ServerState} changes
	 */
	public void addServerStateListener(final ServerStateListener listener);

	/**
	 * Notify any instances of {@link ServerView} who are interested in
	 * notifications with regard to changes in {@link ServerState}.
	 * 
	 * @param stateNotification
	 *            the {@link ServerStateNotification} object containing
	 *            information of the new {@link ServerState}
	 */
	public void notifyServerStateListeners(
			final ServerStateNotification stateNotification);

}
