package suncertify.domain;

/**
 * Representation of the legal states of the database file. Supported states
 * are:
 * <ul>
 * <li><i>valid</i> ({@code 0x00})</li>
 * <li><i>deleted</i> ({@code 0xFF})</li>
 * </ul>
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public enum RecordState {

	/** Value that implies a valid record. */
	valid(0x00),
	/** Value that implies a deleted record. */
	deleted(0xFF);

	/** code for the state. */
	private int code;

	/**
	 * Constructor for a record state.
	 * 
	 * @param code
	 *            corresponding code for the supported state
	 */
	private RecordState(final int code) {
		this.code = code;
	}

	/**
	 * Gets the code of the record state.
	 * 
	 * @return code corresponding code for the supported state
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * Returns a {@link RecordState} for a supplied Integer value.
	 * 
	 * @param code
	 *            the code
	 * @return the {@code RecordState} corresponding to the supplied code
	 */
	public static RecordState forValue(final int code) {
		for (final RecordState state : RecordState.values()) {
			if (state.getCode() == code) {
				return state;
			}
		}
		return null;
	}
}
