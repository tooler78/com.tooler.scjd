package suncertify.ui.client;

/**
 * The view renders the contents of a {@link SubcontractorModel}. It specifies
 * exactly how the {@code Model} data should be presented. If the {@code Model}
 * data changes, the {@code View} must update its presentation as needed. This
 * is achieved by using a push model, in which the {@code View} registers itself
 * with the {@code Model} for change notifications via
 * {@link SubcontractorModel#addTableModelListener(javax.swing.event.TableModelListener)}
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public interface ClientView {

	/**
	 * Display error message to the user, encountered via the
	 * {@link SubcontractorController} invoking an action in the
	 * {@link SubcontractorModel}.
	 * 
	 * @param errorMessage
	 *            the error message to be displayed to the user
	 */
	public void displayErrorMessage(final String errorMessage);

	/**
	 * The {@link ClientView} should render the GUI to be visible to the user.
	 */
	public void display();

	/**
	 * Gets the current row selection made by the user in the {@code View}
	 * 
	 * @return the integer value representing the current {@code Subcontractor}
	 *         selection
	 */
	public int getCurrentRowSelection();
}
