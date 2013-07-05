package suncertify.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.util.Properties;

/**
 * Properties of the application.
 * <p>
 * Provides support to load and save the properties to the OCMJD specified
 * properties file <i>suncertify.properties</i>.
 * 
 * Provides named access methods to the different properties supported in the
 * <i>suncertify.properties</i> file.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ApplicationProperties {

	private static final String BASE_DIRECTORY = System.getProperty("user.dir");

	/** The supported database file extension type. */
	public static final String DATABASE_EXTENSION = "db";

	/** The minimum allowed <i>Port</i> number entry. */
	public static final int MIN_PORT_RANGE = 0;

	/** The maximum allowed <i>Port</i> number entry. */
	public static final int MAX_PORT_RANGE = 65535;

	private static final String PROPERTY_FILE = "suncertify.properties";

	private static final String DATABASE_LOCATION = "databaseLocation";

	private static final String SERVER_ADDRESS = "ServerAddress";

	private static final String SERVER_PORT = "ServerPort";

	private Properties configProperties = null;

	private static final File optionsFile = new File(ApplicationProperties.BASE_DIRECTORY,
			ApplicationProperties.PROPERTY_FILE);

	/**
	 * Singleton of {@code ApplicationProperties}. Since we know that we will
	 * want one of these, we are creating our instance as soon as we possibly
	 * can.
	 */
	private static final ApplicationProperties INSTANCE = new ApplicationProperties();

	/** Instantiates the {@code ApplicationProperties} object. */
	private ApplicationProperties() {
		this.configProperties = this.loadPropertiesFile();

		if (this.configProperties == null) {
			this.createDefaultProperties();
		}
	}

	/**
	 * Gets the single instance of {@link ApplicationProperties}.
	 * 
	 * @return single instance of {@code ApplicationProperties}
	 */
	public static ApplicationProperties getInstance() {
		return ApplicationProperties.INSTANCE;
	}

	private void createDefaultProperties() {
		this.configProperties = new Properties();
		this.configProperties.setProperty(ApplicationProperties.SERVER_ADDRESS, "localhost");
		this.configProperties.setProperty(ApplicationProperties.SERVER_PORT, ""
				+ Registry.REGISTRY_PORT);
	}

	private Properties loadPropertiesFile() {
		Properties loadedProperties = null;

		if (ApplicationProperties.optionsFile.exists()
				&& ApplicationProperties.optionsFile.canRead()) {
			synchronized (ApplicationProperties.optionsFile) {
				try {
					final FileInputStream fileInputStream = new FileInputStream(
							ApplicationProperties.optionsFile);
					loadedProperties = new Properties();
					loadedProperties.load(fileInputStream);
					fileInputStream.close();
				} catch (final FileNotFoundException fileNotFoundException) {
					LaunchApplication.showError("File not found. Default values will be used.");
				} catch (final IOException ioException) {
					LaunchApplication
							.showError("Bad data in parameters file. Default values will be used.");
				}
			}
		}

		return loadedProperties;
	}

	/**
	 * Gets the value of the database location.
	 * 
	 * @return the database location property value
	 */
	public String getDatabaseLocation() {
		return this.configProperties.getProperty(ApplicationProperties.DATABASE_LOCATION);
	}

	/**
	 * Sets the value of the database location property.
	 * 
	 * @param databaseLocation
	 *            new database location value.
	 */
	public void setDatabaseLocation(final String databaseLocation) {
		this.setProperty(ApplicationProperties.DATABASE_LOCATION, databaseLocation);
	}

	/**
	 * Gets the value of the server address.
	 * 
	 * @return the server address property value
	 */
	public String getServerAddress() {
		return this.configProperties.getProperty(ApplicationProperties.SERVER_ADDRESS);
	}

	/**
	 * Sets the value of the server address property.
	 * 
	 * @param serverAddress
	 *            new server address value.
	 */
	public void setServerAddress(final String serverAddress) {
		this.setProperty(ApplicationProperties.SERVER_ADDRESS, serverAddress);
	}

	/**
	 * Returns the value of the server port property.
	 * 
	 * @return server port property value.
	 */
	public String getServerPort() {
		return this.configProperties.getProperty(ApplicationProperties.SERVER_PORT);
	}

	/**
	 * Sets the value of the server port property.
	 * 
	 * @param serverPort
	 *            new server port property value.
	 */
	public void setServerPort(final String serverPort) {
		this.setProperty(ApplicationProperties.SERVER_PORT, serverPort);
	}

	private void setProperty(final String name, final String value) {
		this.configProperties.setProperty(name, value);
		this.savePropertiesFile();
	}

	private void savePropertiesFile() {
		try {
			synchronized (ApplicationProperties.optionsFile) {
				final FileOutputStream fileOutputStream = new FileOutputStream(
						ApplicationProperties.optionsFile);
				this.configProperties.store(fileOutputStream,
						"Bodgitt and Scarper, LLC Application Configuration Properties");
				fileOutputStream.close();
			}
		} catch (final IOException ioException) {
			LaunchApplication.showError("Unable to save user parameters to file. ");
		}
	}

}