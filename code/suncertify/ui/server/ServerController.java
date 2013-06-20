package suncertify.ui.server;

/**
 * Server component controller used in conjunction with the {@link ServerWindow}
 * class.
 * <p>
 * The controller implements a method for each action button in the
 * {@code ServerView}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ServerController implements ServerStateController {

	private final ServerStateModel serverModel;
	private final ServerView serverView;

	/**
	 * Instantiates a new {@link ServerController} object.
	 * 
	 * @param serverModel
	 *            the {@code ServerModel} that this {@code ServerController}
	 *            should interact with
	 */
	public ServerController(final ServerStateModel serverModel) {
		this.serverModel = serverModel;
		this.serverView = new ServerWindow(this, this.serverModel);
	}

	/** {@inheritDoc} */
	@Override
	public void displayView() {
		this.serverView.display();
	}

	/** {@inheritDoc} */
	@Override
	public void startServer() {
		if (this.serverView.validateUserEntry()) {
			this.serverView.storeUserSettings();
			this.serverModel.start();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void stopServer() {
		this.serverModel.stop();
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ServerStateNotification stateNotification) {
		final ServerState state = stateNotification.getServerState();
		final String message = stateNotification.getInfoMessage();
		switch (state) {
		case STARTED:
			this.serverView.disableStartControls();
			this.serverView.getInfoPanel().setSuccess(message);
			break;
		case FAILED:
			this.serverView.getInfoPanel().setFailure(message);
			break;
		case EXIT:
			System.exit(0);
			break;
		default:
			break;
		}
	}

}
