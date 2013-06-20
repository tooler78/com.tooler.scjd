package suncertify.ui.client;

import suncertify.application.GracefulShutdownHook;
import suncertify.application.LaunchMode;
import suncertify.domain.Subcontractor;

/**
 * The {@code Controller} translates the user's interactions with the
 * {@link ClientView} into actions that the {@link SubcontractorModel} will
 * perform.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface SubcontractorController {

	/**
	 * Tries to book a {@link Subcontractor} with the specified customer record
	 * number.
	 * 
	 * @param customerID
	 *            a value representing a customer who wants to book a specific
	 *            {@code Subcontractor}
	 */
	public void bookSubcontractor(final String customerID);

	/**
	 * Displays the {@link ClientView} object associated with this
	 * {@code Controller} instance.
	 */
	public void displayView();

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

	/**
	 * Stops the client application. All cleanup required on exit depending on
	 * the {@link LaunchMode} will be handled in the
	 * {@link GracefulShutdownHook} registered on startup of the application
	 */
	public void stopClient();

}
