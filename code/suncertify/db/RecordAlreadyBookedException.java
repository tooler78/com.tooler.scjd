package suncertify.db;

/**
 * Instances of this exception class are thrown if during an attempt to book a
 * record it fails to succeed as it has already been booked.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class RecordAlreadyBookedException extends Exception {

	private static final long serialVersionUID = 6883989718077183109L;

	/**
	 * Creates a new exception instance.
	 */
	public RecordAlreadyBookedException() {
		super();
	}

	/**
	 * Creates a new exception instance with the specified description.
	 * 
	 * @param exceptionText
	 *            the exception text
	 */
	public RecordAlreadyBookedException(final String exceptionText) {
		super(exceptionText);
	}

}
