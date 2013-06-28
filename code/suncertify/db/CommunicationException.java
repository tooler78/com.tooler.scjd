package suncertify.db;

/**
 * Instances of this exception class are thrown, if a client cannot invoke an
 * operation on a server object because of a network error (e.g.
 * {@code RemoteException}).
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class CommunicationException extends RuntimeException {

	private static final long serialVersionUID = -9113001671136676153L;

	/**
	 * Constructs a new {@link CommunicationException} instance.
	 */
	public CommunicationException() {
		super();
	}

	/**
	 * Constructs a new {@link CommunicationException} instance with the
	 * specified description text.
	 * 
	 * @param exceptionText
	 *            the exception text
	 */
	public CommunicationException(final String exceptionText) {
		super(exceptionText);
	}

	/**
	 * Constructs a new {@link CommunicationException} instance with the
	 * specified description text and a {@code Throwable} exception.
	 * 
	 * @param exceptionText
	 *            the exception text
	 * @param cause
	 *            the throwable exception that was encountered
	 */
	public CommunicationException(final String exceptionText, final Throwable cause) {
		super(exceptionText, cause);
	}

}
