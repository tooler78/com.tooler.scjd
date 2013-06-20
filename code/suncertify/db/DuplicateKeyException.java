package suncertify.db;

/**
 * Instances of this exception class are thrown by the {@code create(String[])}
 * method of the {@code DB} interface.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class DuplicateKeyException extends Exception {

	private static final long serialVersionUID = 4906931810062308615L;

	/**
	 * Instantiates a new {@link DuplicateKeyException} instance.
	 */
	public DuplicateKeyException() {
		super();
	}

	/**
	 * Instantiates a new {@link DuplicateKeyException} instance with the
	 * specified description text.
	 * 
	 * @param exceptionText
	 *            the exception text
	 */
	public DuplicateKeyException(final String exceptionText) {
		super(exceptionText);
	}
}
