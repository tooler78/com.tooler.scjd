package suncertify.ui.client;

import java.awt.GridBagConstraints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import suncertify.application.LaunchApplication;
import suncertify.ui.BrowseListener;
import suncertify.ui.UICommand;
import suncertify.ui.UserEntryPanel;

/**
 * This class defines the UI components required for retrieving configuration
 * information from the user when launched as a <i>non-networked client</i>.
 * Also as inherited from its <i>Superclass</i>, it must handle requests to
 * validate and store user entry as well as disable specified UI components when
 * requested.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class StandaloneUserEntry extends UserEntryPanel implements Observer {

	private static final long serialVersionUID = 3682207378104376464L;

	private static final String DB_LOCATION_LABEL = "Database location: ";
	private static final String DB_LOCATION_TOOLTIP = "The absolute path of the database file";

	private JTextField dbLocationField;
	private JButton browseButton;

	/** {@inheritDoc} */
	@Override
	public void addComponents() {
		this.dbLocationField = new JTextField(42);
		this.browseButton = new JButton(UICommand.BROWSE.getActionCommand());

		final JLabel dbLocationLabel = new JLabel(StandaloneUserEntry.DB_LOCATION_LABEL);
		UserEntryPanel.gridBag.setConstraints(dbLocationLabel, UserEntryPanel.constraints);
		this.add(dbLocationLabel);

		this.dbLocationField.setToolTipText(StandaloneUserEntry.DB_LOCATION_TOOLTIP);
		this.dbLocationField.setText(this.applicationProperties.getDatabaseLocation());
		UserEntryPanel.constraints.gridwidth = GridBagConstraints.RELATIVE;
		UserEntryPanel.gridBag.setConstraints(this.dbLocationField, UserEntryPanel.constraints);
		this.add(this.dbLocationField);

		this.browseButton.setText(UICommand.BROWSE.getActionCommand());
		this.browseButton.setToolTipText(UICommand.BROWSE.getActionCommandToolTip());
		this.browseButton.setMnemonic(UICommand.BROWSE.getKeyEvent());
		final BrowseListener browseListener = new BrowseListener();
		browseListener.addObserver(this);
		this.browseButton.addActionListener(browseListener);
		UserEntryPanel.constraints.gridwidth = GridBagConstraints.REMAINDER;
		UserEntryPanel.gridBag.setConstraints(this.browseButton, UserEntryPanel.constraints);
		this.add(this.browseButton);
	}

	/** {@inheritDoc} */
	@Override
	public boolean validateUserEntry() {
		boolean result = true;
		if (this.invalidDbLocation(this.dbLocationField.getText())) {
			LaunchApplication.showError("The database location entered is not a valid file!");
			result = false;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void storeUserEntry() {
		this.applicationProperties.setDatabaseLocation(this.dbLocationField.getText());
	}

	/** {@inheritDoc} */
	@Override
	public void disableControls() {
		this.dbLocationField.setEnabled(false);
		this.browseButton.setEnabled(false);
	}

	/** {@inheritDoc} */
	@Override
	public void update(final Observable observable, final Object object) {
		if (object instanceof String) {
			final String updatedDbLocation = (String) object;
			this.dbLocationField.setText(updatedDbLocation);
		}
	}

}
