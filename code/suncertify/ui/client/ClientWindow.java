package suncertify.ui.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import suncertify.application.LaunchApplication;
import suncertify.ui.UICommand;

/**
 * Client component view used in conjunction with the {@link ClientController}
 * class.
 * <p>
 * The view will display all GUI related components and interact with the
 * {@code ClientController} via action buttons in the {@code ClientView}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ClientWindow extends JFrame implements ActionListener, ClientView,
		TableModelListener {

	private static final long serialVersionUID = -1264495915446348184L;

	private final SubcontractorController controller;
	private final SubcontractorModel model;

	private static final int INVALID_SELECTION = -1;

	private static final String EMPTY_STRING = "";
	private static final String NAME_LABEL = "Name:";
	private static final String NAME_TOOLTIP = "The name of the Subcontractor";
	private static final String LOCATION_LABEL = "Location:";
	private static final String LOCATION_TOOLTIP = "The locality in which the Subcontractor works";
	private static final String CUSTOMER_ID_LABEL = "Customer ID:";
	private static final String CUSTOMER_ID_TOOLTIP = "<html>Customer's ID for booking the Subcontractor."
			+ "<br>It should be in the format of an 8 digit number, for example <i>12345678</i></html>";
	private static final String EXACT_MATCH_LABEL = "Exactly match Name and/or Location specified";
	private static final String TABLE_TOOLTIP = "This table displays the list of Sub-Contrctors.";

	private final JTextField nameField = new JTextField(25);
	private final JTextField locationField = new JTextField(25);
	private final JTextField customerIdField = new JTextField(25);

	private final JButton bookButton = new JButton();
	private final JButton clearButton = new JButton();
	private final JButton displayAllButton = new JButton();
	private final JButton searchButton = new JButton();

	private final JCheckBox exactMatchCheckBox = new JCheckBox();

	private final JTable subContractorTable;

	/**
	 * Instantiates a new {@code ClientView} object with a reference to the
	 * {@link ClientController} object.
	 * 
	 * @param controller
	 *            The controller translates the user's interactions with the
	 *            view into actions that the {@code ClientModel} will perform
	 */
	public ClientWindow(final SubcontractorController controller,
			final SubcontractorModel model) {
		this.controller = controller;
		this.model = model;
		this.model.addTableModelListener(this);
		this.subContractorTable = this.createTable();
		this.initComponenets();
		this.controller.retrieveAllSubcontractors();
	}

	private void initComponenets() {
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addMenuBar();

		this.setupButton(this.bookButton, UICommand.BOOK);
		this.setupButton(this.clearButton, UICommand.CLEAR);
		this.setupButton(this.displayAllButton, UICommand.DISPLAYALL);
		this.setupButton(this.searchButton, UICommand.SEARCH);

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Search", new SearchCriteriaPanel());
		tabbedPane.addTab("Book", new BookPanel());
		this.getContentPane().add(tabbedPane, BorderLayout.NORTH);

		this.subContractorTable.setToolTipText(ClientWindow.TABLE_TOOLTIP);
		this.subContractorTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final JScrollPane scrollPane = new JScrollPane(this.subContractorTable);
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5, 5, 5, 5),
				BorderFactory.createEtchedBorder()));
		this.add(scrollPane, BorderLayout.CENTER);

		this.pack();
		this.setSize(1000, 650);
		// Center on screen
		this.setLocationRelativeTo(null);
	}

	private void addMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent actionEvent) {
				ClientWindow.this.controller.stopClient();
			}
		});
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
		fileMenu.add(quitMenuItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		this.setJMenuBar(menuBar);
	}

	private void setupButton(final JButton button, final UICommand command) {
		button.addActionListener(this);
		button.setText(command.getActionCommand());
		button.setToolTipText(command.getActionCommandToolTip());
		button.setMnemonic(command.getKeyEvent());
	}

	private JTable createTable() {
		final JTable table = new JTable(this.model.getTableModel());
		table.setPreferredScrollableViewportSize(new Dimension(900, 500));
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(false);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setDragEnabled(false);

		return table;
	}

	/**
	 * A {@code JPanel} which will have components on it to support a user
	 * searching the database for matching {@code Subcontractors}.
	 */
	private class SearchCriteriaPanel extends JPanel {

		private static final long serialVersionUID = -8291347519900913105L;

		public SearchCriteriaPanel() {
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints constraints = new GridBagConstraints();
			this.setLayout(gridBag);

			constraints.insets = new Insets(2, 2, 2, 2);
			constraints.anchor = GridBagConstraints.LINE_START;

			final JLabel nameLabel = new JLabel(ClientWindow.NAME_LABEL);
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.EAST;
			gridBag.setConstraints(nameLabel, constraints);
			this.add(nameLabel);

			ClientWindow.this.nameField
					.setToolTipText(ClientWindow.NAME_TOOLTIP);
			constraints.gridwidth = GridBagConstraints.RELATIVE;
			gridBag.setConstraints(ClientWindow.this.nameField, constraints);
			this.add(ClientWindow.this.nameField);

			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			gridBag.setConstraints(ClientWindow.this.searchButton, constraints);
			this.add(ClientWindow.this.searchButton);

			final JLabel locationLabel = new JLabel(ClientWindow.LOCATION_LABEL);
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.EAST;
			gridBag.setConstraints(locationLabel, constraints);
			this.add(locationLabel);

			ClientWindow.this.locationField
					.setToolTipText(ClientWindow.LOCATION_TOOLTIP);
			constraints.gridwidth = GridBagConstraints.RELATIVE;
			constraints.anchor = GridBagConstraints.WEST;
			gridBag.setConstraints(ClientWindow.this.locationField, constraints);
			this.add(ClientWindow.this.locationField);

			constraints.gridwidth = GridBagConstraints.REMAINDER;
			gridBag.setConstraints(ClientWindow.this.displayAllButton,
					constraints);
			this.add(ClientWindow.this.displayAllButton);

			ClientWindow.this.exactMatchCheckBox
					.setText(ClientWindow.EXACT_MATCH_LABEL);
			ClientWindow.this.exactMatchCheckBox.setSelected(true);
			constraints.gridwidth = 0;
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.NONE;
			gridBag.setConstraints(ClientWindow.this.exactMatchCheckBox,
					constraints);
			this.add(ClientWindow.this.exactMatchCheckBox);
		}
	}

	/**
	 * A {@code JPanel} which will have components on it to support a user
	 * booking a specific {@code Subcontractor} for a given <i>customer ID</i>.
	 */
	private class BookPanel extends JPanel {

		private static final long serialVersionUID = 4882353367634558401L;

		public BookPanel() {
			final GridBagLayout layout = new GridBagLayout();
			this.setLayout(layout);

			final GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(2, 2, 2, 2);
			constraints.anchor = GridBagConstraints.LINE_START;

			final JLabel customerIdLabel = new JLabel(
					ClientWindow.CUSTOMER_ID_LABEL);
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.EAST;
			layout.setConstraints(customerIdLabel, constraints);
			this.add(customerIdLabel);

			ClientWindow.this.customerIdField
					.setToolTipText(ClientWindow.CUSTOMER_ID_TOOLTIP);
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.anchor = GridBagConstraints.WEST;
			layout.setConstraints(ClientWindow.this.customerIdField,
					constraints);
			this.add(ClientWindow.this.customerIdField);

			final JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.add(ClientWindow.this.bookButton);
			buttonPanel.add(ClientWindow.this.clearButton);
			constraints.gridx = 2;
			constraints.gridy = 1;
			layout.setConstraints(buttonPanel, constraints);
			this.add(buttonPanel);
		}
	}

	/**
	 * Invoked when an action occurs in the view via the action command buttons.
	 */
	@Override
	public void actionPerformed(final ActionEvent actionEvent) {
		final String actionCommand = actionEvent.getActionCommand();
		if (actionCommand.equals(this.bookButton.getActionCommand())) {
			this.controller.bookSubcontractor(this.customerIdField.getText());
		} else if (actionCommand.equals(this.clearButton.getActionCommand())) {
			this.clearCustomerID();
		} else if (actionCommand.equals(this.displayAllButton
				.getActionCommand())) {
			this.displayAll();
		} else if (actionCommand.equals(this.searchButton.getActionCommand())) {
			this.seach();
		}
	}

	private void clearCustomerID() {
		this.customerIdField.setText(ClientWindow.EMPTY_STRING);
		this.customerIdField.requestFocusInWindow();
	}

	private void displayAll() {
		this.nameField.setText(ClientWindow.EMPTY_STRING);
		this.locationField.setText(ClientWindow.EMPTY_STRING);
		this.controller.retrieveAllSubcontractors();
	}

	private void seach() {
		// Fetch search criteria from the text fields
		String name = this.nameField.getText();
		String location = this.locationField.getText();

		// The searchRecords method needs to be passed null if
		// name/location contain empty strings
		if (ClientWindow.EMPTY_STRING.equals(name)) {
			name = null;
		}
		if (ClientWindow.EMPTY_STRING.equals(location)) {
			location = null;
		}
		this.controller.searchSubcontractor(name, location,
				this.exactMatchCheckBox.isSelected());
	}

	/** {@inheritDoc} */
	@Override
	public void display() {
		this.setVisible(true);
	}

	/** {@inheritDoc} */
	@Override
	public void displayErrorMessage(final String errorMessage) {
		LaunchApplication.showError(errorMessage);
	}

	/** {@inheritDoc} */
	@Override
	public int getCurrentRowSelection() {
		return this.subContractorTable.getSelectedRow();
	}

	/** {@inheritDoc} */
	@Override
	public void tableChanged(final TableModelEvent tableModelEvent) {
		this.subContractorTable.setModel((TableModel) tableModelEvent
				.getSource());
		final int previousSelection = tableModelEvent.getColumn();
		if (previousSelection != ClientWindow.INVALID_SELECTION) {
			this.subContractorTable.setRowSelectionInterval(previousSelection,
					previousSelection);
		}
	}

}
