package suncertify.ui.server;

public class ServerStateNotification {

	private ServerState serverState;
	private String infoMessage = "";

	public ServerStateNotification(final ServerState serverState) {
		this.setServerState(serverState);
	}

	public void setInfoMessage(final String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public String getInfoMessage() {
		return this.infoMessage;
	}

	public ServerState getServerState() {
		return this.serverState;
	}

	public void setServerState(final ServerState serverState) {
		this.serverState = serverState;
	}

}
