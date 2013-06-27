package suncertify.application;

/**
 * The supported launch mode of the application.
 * <ul>
 * <li>{@link LaunchMode#NETWORK_CLIENT}</li>
 * <li>{@link LaunchMode#SERVER}</li>
 * <li>{@link LaunchMode#STANDALONE_CLIENT}</li>
 * </ul>
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public enum LaunchMode {

	/**
	 * A networked client via RMI. This is used when the user has not specified
	 * any command line parameters when starting the application.
	 */
	NETWORK_CLIENT(null),
	/**
	 * Application will be a single JVM incorporating both client and server
	 * components as one. No networking is allowed when launched in this mode.
	 */
	STANDALONE_CLIENT("alone"),
	/**
	 * Application will be a server. Responsible for exposing access to the
	 * database via RMI.
	 */
	SERVER("server");

	/** The command line argument associated with this application launch mode. */
	public final String cmdLineArg;

	/**
	 * Constructor for {@code LaunchMode}. Allows specification for the command
	 * line argument corresponding to this mode.
	 * 
	 * @param commandLineArg
	 *            Command line argument corresponding to this mode.
	 */
	LaunchMode(final String mode) {
		this.cmdLineArg = mode;
	}

	/**
	 * Gets the command line argument associated with this application launch
	 * mode.
	 * 
	 * @return the command line argument
	 */
	public String getCmdLineArg() {
		return this.cmdLineArg;
	}

	/**
	 * Checks if this is a {@link #NETWORK_CLIENT}
	 * 
	 * @return true if this is a {@code NETWORK_CLIENT}
	 */
	public boolean isNetworkClient() {
		return LaunchMode.NETWORK_CLIENT == this;
	}

	/**
	 * Checks if this is a {@link #SERVER}
	 * 
	 * @return true if this is a {@code SERVER}
	 */
	public boolean isServer() {
		return LaunchMode.SERVER == this;
	}

	/**
	 * Checks if this is a {@link #STANDALONE_CLIENT}
	 * 
	 * @return true if this is a {@code STANDALONE_CLIENT}
	 */
	public boolean isStandalone() {
		return LaunchMode.STANDALONE_CLIENT == this;
	}

}