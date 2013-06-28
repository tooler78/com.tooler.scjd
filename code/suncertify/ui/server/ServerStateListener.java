package suncertify.ui.server;

/**
 * The listener interface for receiving {@link ServerState} events. The class
 * that is interested in processing a {@code ServerState} event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addServerStateListener<code> method. When
 * the {@code ServerState} event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ServerState
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface ServerStateListener {

	/**
	 * Handle {@link ServerState} change events.
	 * 
	 * @param stateNotification
	 *            the {@code ServerStateNotification} containing the updated
	 *            {@code ServerState}
	 */
	public void handleStateChange(final ServerStateNotification stateNotification);

}
