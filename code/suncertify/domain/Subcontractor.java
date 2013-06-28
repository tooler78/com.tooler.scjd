package suncertify.domain;

/**
 * The Value Object representing a {@code Subcontractor}. it contains all values
 * relating to attributes we wish to track relating to a Home-Owner
 * Subcontractor
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class Subcontractor {

	/**
	 * Index of the <i>name</i> value in the array
	 */
	public static final int INDEX_NAME = 0;

	/**
	 * Index of the <i>location</i> value in the array
	 */
	public static final int INDEX_LOCATION = 1;

	/**
	 * Index of the <i>specialties</i> value in the array
	 */
	public static final int INDEX_SPECIALTIES = 2;

	/**
	 * Index of the <i>size</i> value in the array
	 */
	public static final int INDEX_SIZE = 3;

	/**
	 * Index of the <i>rate</i> value in the array
	 */
	public static final int INDEX_RATE = 4;

	/**
	 * Index of the <i>owner</i> value in the array
	 */
	public static final int INDEX_OWNER = 5;

	private static final int NO_RECORD_NUMBER_ASSIGNED = -1;

	private RecordState state;

	private String name = "";

	private String location = "";

	private String specialties;

	private String size = "";

	private String rate = "";

	private String owner = "";

	private final int recordNumber;

	/**
	 * Creates an instance of a {@code Subcontractor} with a specified record
	 * number, a {@code RecordState} and a list of initial values.
	 * 
	 * @param recordNumber
	 *            the record number to associate with this {@code Subcontractor}
	 * @param state
	 *            the {@code RecordState} for this {@code Subcontractor}
	 * @param data
	 *            String array of the attributes associated with this
	 *            {@code Subcontractor}
	 */
	public Subcontractor(final int recordNumber, final RecordState state, final String[] data) {
		this.recordNumber = recordNumber;
		this.state = state;
		this.setData(data);
	}

	/**
	 * Creates an instance of a {@code Subcontractor} with a specified a
	 * {@code RecordState} and a list of initial values.
	 * <p>
	 * The record number is set to a special value, signifying that a record
	 * number is not assigned.
	 * 
	 * @param state
	 *            the {@code RecordState} of the {@code Subcontractor}
	 * @param data
	 *            String array of the attributes associated with this
	 *            {@code Subcontractor}
	 */
	public Subcontractor(final RecordState state, final String[] data) {
		this(Subcontractor.NO_RECORD_NUMBER_ASSIGNED, state, data);
	}

	/**
	 * Creates an instance of a {@code Subcontractor} with a specified list of
	 * initial values and a record number.
	 * <p>
	 * This constructor presumes that the Subcontractor being initialized has a
	 * {@link RecordState#valid} by default.
	 * 
	 * @param recordNumber
	 *            the record number associated with this {@code Subcontractor}
	 * @param data
	 *            String array of the attributes associated with this
	 *            {@code Subcontractor}
	 * 
	 */
	public Subcontractor(final int recordNumber, final String[] data) {
		this(recordNumber, RecordState.valid, data);
	}

	/**
	 * Gets the record number associated with this {@code Subcontractor}.
	 * 
	 * @return {@code Subcontractors} record number
	 */
	public int getRecordNumber() {
		return this.recordNumber;
	}

	/**
	 * The {@code RecordState} of the {@code Subcontractor} entry in the
	 * database.
	 * 
	 * @return the state
	 */
	public RecordState getState() {
		return this.state;
	}

	/**
	 * Sets the state of this data record.
	 * 
	 * @param state
	 *            A {@code RecordState} containing this {@code Subcontractor}
	 *            entry
	 */
	public void setState(final RecordState state) {
		this.state = state;
	}

	/**
	 * Gets the name of the {@code Subcontractor}.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this {@code Subcontractor}.
	 * 
	 * @param name
	 *            the name of the {@code Subcontractor}
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the the locality in which this {@code Subcontractor} works.
	 * 
	 * @return the location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Sets the location of this {@code Subcontractor}.
	 * 
	 * @param location
	 *            the location of the {@code Subcontractor}
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * Gets the the types of work performed.
	 * 
	 * @return the specialties
	 */
	public String getSpecialties() {
		return this.specialties;
	}

	/**
	 * Sets the specialties of this {@code Subcontractor}.
	 * 
	 * @param specialties
	 *            the specialties of the {@code Subcontractor}
	 */
	public void setSpecialties(final String specialties) {
		this.specialties = specialties;
	}

	/**
	 * Gets the the number of staff in the organization.
	 * 
	 * @return the size
	 */
	public String getSize() {
		return this.size;
	}

	/**
	 * Sets the size of this {@code Subcontractor}.
	 * 
	 * @param size
	 *            the size of the {@code Subcontractor}
	 */
	public void setSize(final String size) {
		this.size = size;
	}

	/**
	 * Gets the the charge per hour for the {@code Subcontractor}.
	 * 
	 * @return the rate
	 */
	public String getRate() {
		return this.rate;
	}

	/**
	 * Sets the rate of this {@code Subcontractor}.
	 * 
	 * @param rate
	 *            the rate of the {@code Subcontractor}
	 */
	public void setRate(final String rate) {
		this.rate = rate;
	}

	/**
	 * Gets the customer ID.
	 * 
	 * @return this {@code Subcontractors} associated customer ID
	 */
	public String getOwner() {
		return this.owner;
	}

	/**
	 * Sets the owner for this {@code Subcontractor}.
	 * 
	 * @param owner
	 *            new owner of this {@code Subcontractor}
	 */
	public void setOwner(final String owner) {
		this.owner = owner;
	}

	/**
	 * Returns the attribute values of this {@code Subcontractor} in String
	 * array format.
	 * 
	 * @return string array of the attributes associated with this
	 *         {@code Subcontractor}
	 */
	public String[] getData() {
		final String[] data = new String[DatabaseSchema.FIELD_COUNT];
		data[Subcontractor.INDEX_NAME] = this.name;
		data[Subcontractor.INDEX_LOCATION] = this.location;
		data[Subcontractor.INDEX_SPECIALTIES] = this.specialties;
		data[Subcontractor.INDEX_SIZE] = this.size;
		data[Subcontractor.INDEX_RATE] = this.rate;
		data[Subcontractor.INDEX_OWNER] = this.owner;

		return data;
	}

	/**
	 * Check if this {@code Subcontractor} is booked, i.e. its owner attribute
	 * is not null and not empty.
	 * 
	 * @return true if this {@code Subcontractor} is booked.
	 */
	public boolean isBooked() {
		return this.owner != null && !"".equals(this.owner);
	}

	/**
	 * Sets the attribute values of this {@code Subcontractor} from the passed
	 * in data.
	 * 
	 * @param data
	 *            the new data
	 */
	public void setData(final String[] data) {
		if (data == null || data.length != DatabaseSchema.FIELD_COUNT) {
			throw new IllegalArgumentException("invalid data detected!");
		}
		this.name = data[Subcontractor.INDEX_NAME].trim();
		this.location = data[Subcontractor.INDEX_LOCATION].trim();
		this.specialties = data[Subcontractor.INDEX_SPECIALTIES].trim();
		this.size = data[Subcontractor.INDEX_SIZE].trim();
		this.rate = data[Subcontractor.INDEX_RATE].trim();
		this.owner = data[Subcontractor.INDEX_OWNER].trim();
	}

	/**
	 * Returns a String representation of this {@code Subcontractor}.
	 * 
	 * @return String representation of this {@code Subcontractor}.
	 */
	@Override
	public String toString() {
		return "Subcontractor [recordNumber:" + this.recordNumber + ", state:" + this.state
				+ ", name:" + this.name + ", location:" + this.location + ", specialties:"
				+ this.specialties + ", size:" + this.size + ", rate:" + this.rate + ", owner:"
				+ this.owner + "]";
	}

}
