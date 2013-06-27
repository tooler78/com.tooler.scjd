package suncertify.db;

/**
 * Instances of this exception class are thrown, if the database does not
 * conform to the specified database schema. Also an {@code DatabaseException}
 * may be thrown if an error occurs during interaction with the database during
 * access
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class DatabaseException extends Exception {

	private static final long serialVersionUID = 5893568783871792434L;

	/**
	 * Constructs a new {@link DatabaseException} instance.
	 */
	public DatabaseException() {
		super();
	}

	/**
	 * Constructs a new {@link DatabaseException} instance with the specified
	 * description text.
	 * 
	 * @param exceptionText
	 *            the exception text
	 */
	public DatabaseException(final String exceptionText) {
		super(exceptionText);
	}
}
