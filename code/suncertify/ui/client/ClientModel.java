package suncertify.ui.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import suncertify.application.DBService;
import suncertify.db.RecordAlreadyBookedException;
import suncertify.db.RecordNotFoundException;
import suncertify.domain.Subcontractor;

/**
 * Model of the {@link Subcontractor} objects used by the {@link ClientView}
 * frame.
 * <p>
 * {@code ClientModel} extends the {@link AbstractTableModel} as it provides
 * default implementations for most of the methods in the
 * <code>TableModel</code> interface. It takes care of the management of
 * listeners and provides some conveniences for generating
 * <code>TableModelEvents</code> and dispatching them to the listeners.
 * <p>
 * {@code ClientModel} implements the {@link SubcontractorModel} interface which
 * needs to manage the different requests via the
 * {@link SubcontractorController}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ClientModel extends AbstractTableModel implements SubcontractorModel {

	private static final long serialVersionUID = -3233583889750841588L;

	private static final String[] COLUMN_NAMES = { "Name", "Location", "Specialties", "Size",
			"Rate", "Owner" };

	private List<Subcontractor> subContractors = new ArrayList<Subcontractor>();

	private final DBService dbService;

	/**
	 * Constructs a new table model that contains the relevant
	 * {@code Subcontractor(s)} based on User requests via the
	 * {@link ClientView} and {@link SubcontractorController}.
	 * 
	 * @param dbService
	 *            <i>data access object</i> for interaction with the database
	 *            containing {@code Subcontractors}
	 */
	public ClientModel(final DBService dbService) {
		this.dbService = dbService;
	}

	/** {@inheritDoc} */
	@Override
	public int getColumnCount() {
		return ClientModel.COLUMN_NAMES.length;
	}

	/** {@inheritDoc} */
	@Override
	public String getColumnName(final int column) {
		return ClientModel.COLUMN_NAMES[column];
	}

	/** {@inheritDoc} */
	@Override
	public int getRowCount() {
		return this.subContractors.size();
	}

	/** {@inheritDoc} */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		Object value = null;
		if (this.subContractors != null && this.subContractors.size() > rowIndex) {
			final Subcontractor subContractor = this.subContractors.get(rowIndex);
			switch (columnIndex) {
			case Subcontractor.INDEX_NAME:
				value = subContractor.getName();
				break;
			case Subcontractor.INDEX_LOCATION:
				value = subContractor.getLocation();
				break;
			case Subcontractor.INDEX_SPECIALTIES:
				value = subContractor.getSpecialties();
				break;
			case Subcontractor.INDEX_SIZE:
				value = subContractor.getSize();
				break;
			case Subcontractor.INDEX_RATE:
				value = subContractor.getRate();
				break;
			case Subcontractor.INDEX_OWNER:
				value = subContractor.getOwner();
				break;
			}
		}
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public void bookSubcontractor(final Subcontractor subContractor, final String customerID,
			final int currentRowSelection) throws RecordNotFoundException,
			RecordAlreadyBookedException {
		try {
			this.subContractors = this.dbService.bookSubcontractor(subContractor,
					this.subContractors, customerID);

		} finally {
			this.fireTableRowsUpdated(currentRowSelection, currentRowSelection);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void searchSubcontractor(final String name, final String location,
			final boolean exactMatchRequired) {
		this.subContractors = this.dbService
				.searchSubcontractor(name, location, exactMatchRequired);
		this.fireTableDataChanged();
	}

	/** {@inheritDoc} */
	@Override
	public void retrieveAllSubcontractors() {
		this.subContractors = this.dbService.retrieveAllSubcontractors();
		this.fireTableDataChanged();
	}

	/** {@inheritDoc} */
	@Override
	public TableModel getTableModel() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Subcontractor getSubcontractor(final int index) {
		Subcontractor subContractor = null;
		if (this.subContractors != null && this.subContractors.size() > index) {
			subContractor = this.subContractors.get(index);
		}
		return subContractor;
	}

}
