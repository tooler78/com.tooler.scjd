package suncertify.ui.client;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import suncertify.application.LaunchMode;
import suncertify.db.RecordAlreadyBookedException;
import suncertify.db.RecordNotFoundException;
import suncertify.domain.Subcontractor;

/**
 * The interface {@code Model} is used to represent the supported interaction
 * between the user via the {@link SubcontractorController} when launched in
 * either of the following two launch states:
 * <ul>
 * <li>{@link LaunchMode#NETWORK_CLIENT}.</li>
 * <li>{@link LaunchMode#STANDALONE_CLIENT}.</li>
 * </ul>
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface SubcontractorModel {

	/**
	 * Adds a listener to the list that's notified each time a change to the
	 * {@code Model} occurs.
	 * 
	 * @param listener
	 *            the TableModelListener to be informed of any {@code Model}
	 *            changes
	 */
	public void addTableModelListener(final TableModelListener listener);

	/**
	 * Attempts to book the {@link Subcontractor} with the specified customer
	 * record number.
	 * 
	 * @param subContractor
	 *            {@code Subcontractor} that will be booked.
	 * @param customerID
	 *            a value representing a customer who wants to book a specific
	 *            {@code Subcontractor}
	 * @param currentRowSelection
	 *            a value representing the current row selection associated to
	 *            the {@code Subcontractor} to be booked
	 * @throws RecordNotFoundException
	 *             if no {@code Subcontractor} with the given record number
	 *             exists or if it is marked as deleted
	 * @throws RecordAlreadyBookedException
	 *             if the record is already booked
	 */
	public void bookSubcontractor(final Subcontractor subContractor,
			final String customerID, final int currentRowSelection)
			throws RecordNotFoundException, RecordAlreadyBookedException;

	/**
	 * Returns the {@link Subcontractor} at the specified position.
	 * <p>
	 * If no {@code Subcontractors} are present or there is no
	 * {@code Subcontractor} at the specified index, null is returned.
	 * 
	 * @param index
	 *            index of the {@code Subcontractor}
	 * @return {@code Subcontractor} at the specified index or null if it does
	 *         not exist
	 */
	public Subcontractor getSubcontractor(final int index);

	/**
	 * Gets the {@link TableModel} associated with this {@code Model} instance.
	 * 
	 * @return the {@code TableModel} associated with this {@code Model}
	 *         instance.
	 */
	public TableModel getTableModel();

	/**
	 * Search for all valid {@code Subcontractor(s)} that match the specified
	 * search criteria.
	 * 
	 * @param name
	 *            Search criteria for the <i>name</i> field
	 * @param location
	 *            Search criteria for the <i>location</i> field
	 * @param exactMatchRequired
	 *            boolean value to represent if the name/location should be
	 *            exactly matched
	 */
	public void searchSubcontractor(final String name, final String location,
			final boolean exactMatchRequired);

	/**
	 * Retrieves all valid {@code Subcontractors}.
	 */
	public void retrieveAllSubcontractors();

}
