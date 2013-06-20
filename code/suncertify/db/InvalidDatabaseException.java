package suncertify.db;

/**
 * Instances of this exception class are thrown, if the database does not
 * conform to the specified database schema.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class InvalidDatabaseException extends Exception {

	private static final long serialVersionUID = 5893568783871792434L;

	/**
	 * Constructs a new {@link InvalidDatabaseException} instance.
	 */
	public InvalidDatabaseException() {
		super();
	}

	/**
	 * Constructs a new {@link InvalidDatabaseException} instance with the
	 * specified description text.
	 * 
	 * @param exceptionText
	 *            the exception text
	 */
	public InvalidDatabaseException(final String exceptionText) {
		super(exceptionText);
	}
}
