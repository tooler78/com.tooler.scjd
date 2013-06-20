package suncertify.ui.server;

/**
 * The view renders the contents of a {@link ServerStateModel}. It specifies
 * exactly how the {@code Model} data should be presented. If the {@code Model}
 * data changes, the {@code View} must update its presentation as needed. This
 * is achieved by using a push model, in which the {@code View} registers itself
 * with the {@code Model} for change notifications via
 * {@link ServerStateModel#addServerStateListener(ServerStateListener)}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface ServerView {

	/**
	 * The {@link ServerView} should render the GUI to be visible to the user.
	 */
	public void display();

	/**
	 * Gets the {@link ServerInfoPanel} used by this {@code View} object to
	 * communicate current server status.
	 * 
	 * @return the {@code ServerInfoPanel} object used by this {@code View}, to
	 *         communicate with the customer, the current status
	 */
	public ServerInfoPanel getInfoPanel();

	/**
	 * Disable any controls that would allow a user to interact with UI
	 * components once the server application has entered a {@code ServerState}
	 * {@link ServerState#STARTED}.
	 */
	public void disableStartControls();

	/**
	 * Validate all controls that require interaction from the user prior to
	 * {@code ServerState} {@link ServerState#STARTED}in the {@code View}
	 * object.
	 * 
	 * @return true, if the {@code View} successfully validates user entry.
	 */
	public boolean validateUserEntry();

	/**
	 * Stores the user configuration information entered in the different GUI
	 * components by the user of the current {@code View}
	 */
	public void storeUserSettings();
}
