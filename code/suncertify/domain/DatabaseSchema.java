package suncertify.domain;

/**
 * Schema definition of the supplied DB file from Oracle
 * <p>
 * This includes the defined fields and the expected <i>"magic cookie"</i> value
 * of the database file to confirm that we are handling the expected database
 * file originally supplied.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class DatabaseSchema {

	/** Expected magic cookie value in the data file */
	public static final int EXPECTED_MAGIC_COOKIE_VALUE = 0x203;

	/** Expected initial offset for all schema information to be completed */
	public static final int EXPECTED_INITIAL_OFFSET = 54;

	/**
	 * Field count in the data file.
	 */
	public static final short FIELD_COUNT = 6;

	/**
	 * Combined field lengths in the data file for a single entry including the
	 * flag representing {@code RecordState}.
	 */
	public static final int TOTAL_FIELD_LENGTH;

	/**
	 * Combined field lengths in the data file for a single entry excluding the
	 * flag representing {@code RecordState}.
	 */
	public static final int TOTAL_SCHEMA_LENGTH;

	/**
	 * Name of the <i>"name"</i> data field
	 */
	public static final String FIELD_NAME_NAME = "name";

	/**
	 * Name of the <i>"location"</i> data field
	 */
	public static final String FIELD_NAME_LOCATION = "location";

	/**
	 * Name of the <i>"specialties"</i> data field
	 */
	public static final String FIELD_NAME_SPECIALTIES = "specialties";

	/**
	 * Name of the <i>"size"</i> data field
	 */
	public static final String FIELD_NAME_SIZE = "size";

	/**
	 * Name of the <i>"rate"</i> data field
	 */
	public static final String FIELD_NAME_RATE = "rate";

	/**
	 * Name of the <i>"owner"</i> data field
	 */
	public static final String FIELD_NAME_OWNER = "owner";

	/**
	 * Length of the <i>"name"</i> data field
	 */
	public static final int FIELD_LENGTH_NAME = 32;

	/**
	 * Length of the <i>"location"</i> data field
	 */
	public static final int FIELD_LENGTH_LOCATION = 64;

	/**
	 * Length of the <i>"specialties"</i> data field
	 */
	public static final int FIELD_LENGTH_SPECIALTIES = 64;

	/**
	 * Length of the <i>"size"</i> data field
	 */
	public static final int FIELD_LENGTH_SIZE = 6;

	/**
	 * Length of the <i>"rate"</i> data field
	 */
	public static final int FIELD_LENGTH_RATE = 8;

	/**
	 * Length of the <i>"owner"</i> data field
	 */
	public static final int FIELD_LENGTH_OWNER = 8;

	/**
	 * Length of the <i>"state"</i> data field
	 */
	public static final int FIELD_LENGTH_STATE = 1;

	/**
	 * Expected field lengths.
	 */
	public static final int[] FIELD_LENGTHS = new int[] { DatabaseSchema.FIELD_LENGTH_NAME,
			DatabaseSchema.FIELD_LENGTH_LOCATION, DatabaseSchema.FIELD_LENGTH_SPECIALTIES,
			DatabaseSchema.FIELD_LENGTH_SIZE, DatabaseSchema.FIELD_LENGTH_RATE,
			DatabaseSchema.FIELD_LENGTH_OWNER };

	static {
		int tempFieldLeghtTotal = 0;
		for (final int fieldLength : DatabaseSchema.FIELD_LENGTHS) {
			tempFieldLeghtTotal += fieldLength;
		}
		TOTAL_SCHEMA_LENGTH = tempFieldLeghtTotal;

		// Flag byte needs to be included along with the other fields to
		// determine record length even though it is read separately.
		TOTAL_FIELD_LENGTH = tempFieldLeghtTotal + DatabaseSchema.FIELD_LENGTH_STATE;
	}

}
