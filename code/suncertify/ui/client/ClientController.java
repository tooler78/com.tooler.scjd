package suncertify.ui.client;

import java.util.regex.Pattern;

import suncertify.db.RecordAlreadyBookedException;
import suncertify.db.RecordNotFoundException;
import suncertify.domain.Subcontractor;

/**
 * Client component controller used in conjunction with the {@link ClientView}.
 * <p>
 * The controller implements a method for each action possible in the
 * {@code View}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ClientController implements SubcontractorController {

	private static final int INVALID_SELECTION = -1;

	private final SubcontractorModel model;
	private final ClientView view;

	/**
	 * Instantiates a new {@code ClientController} object with a reference to
	 * the {@link SubcontractorModel} object used to provide the expected client
	 * use cases issued from the {@link ClientView}.
	 * 
	 * @param model
	 *            the {@code SubcontractorModel} that this
	 *            {@code SubcontractorController} will interact with
	 */
	public ClientController(final SubcontractorModel model) {
		this.model = model;
		this.view = new ClientWindow(this, this.model);
	}

	/** {@inheritDoc} */
	@Override
	public void bookSubcontractor(final String customerID) {
		if (this.invalidCustomerID(customerID)) {
			this.view
					.displayErrorMessage("Please enter a valid Customer ID before attempting to book a Subcontractor."
							+ "\nIt should be in the format of an 8 digit number, for example 12345678.");
			return;
		}
		final int currentRowSelection = this.view.getCurrentRowSelection();
		if (currentRowSelection == ClientController.INVALID_SELECTION) {
			this.view
					.displayErrorMessage("There is currently no Subcontractor selected in the table");
			return;
		}
		try {
			final Subcontractor contractorToBeBooked = this.model
					.getSubcontractor(currentRowSelection);
			this.model.bookSubcontractor(contractorToBeBooked, customerID,
					currentRowSelection);
		} catch (final RecordNotFoundException recordNotFoundException) {
			this.view
					.displayErrorMessage("The selected Subcontractor information can not be found!");
		} catch (final RecordAlreadyBookedException recordAlreadyBookedException) {
			this.view
					.displayErrorMessage("The selected Subcontractor is already booked.");
		}
	}

	private boolean invalidCustomerID(final String customerID) {
		if (Pattern.matches("[a-zA-Z]+", customerID) == false
				&& customerID.length() == 8) {
			return false;
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void displayView() {
		this.view.display();
	}

	/** {@inheritDoc} */
	@Override
	public void searchSubcontractor(final String name, final String location,
			final boolean exactMatchRequired) {
		this.model.searchSubcontractor(name, location, exactMatchRequired);
	}

	/** {@inheritDoc} */
	@Override
	public void retrieveAllSubcontractors() {
		this.model.retrieveAllSubcontractors();
	}

	/** {@inheritDoc} */
	@Override
	public void stopClient() {
		System.exit(0);
	}

}