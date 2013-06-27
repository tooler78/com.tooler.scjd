package suncertify.db;

/**
 * Instances of this exception class are thrown by {@code DB} methods if a
 * specified record does not exist or is marked as deleted in the database file.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class RecordNotFoundException extends Exception {

	private static final long serialVersionUID = 2823045322464184130L;

	/**
	 * Instantiates a new {@link RecordNotFoundException} instance.
	 */
	public RecordNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new {@link RecordNotFoundException} instance with the
	 * specified exception text.
	 * 
	 * @param exceptionText
	 *            the exception text
	 */
	public RecordNotFoundException(final String exceptionText) {
		super(exceptionText);
	}

}
