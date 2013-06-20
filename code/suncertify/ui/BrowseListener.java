package suncertify.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import suncertify.application.ApplicationProperties;

/**
 * This class is a utility class for browsing the file system via a
 * {@link JFileChooser}. Files of type {@link UserEntryPanel#DATABASE_EXTENSION}
 * is the expected user selection. This class extends the {@link Observable}
 * class and notifies all interested classes of any state change in the chosen
 * database location by the user.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class BrowseListener extends Observable implements ActionListener {

	private static final String DATABASE_CHOOSER_DESCRIPTION = "Database files (*."
			+ ApplicationProperties.DATABASE_EXTENSION + ")";

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent actionEvent) {
		final JFileChooser chooser = new JFileChooser(
				System.getProperty("user.dir"));
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return BrowseListener.DATABASE_CHOOSER_DESCRIPTION;
			}

			@Override
			public boolean accept(final File file) {
				if (file.isFile()) {
					return file.getName().endsWith(
							ApplicationProperties.DATABASE_EXTENSION);
				}
				return true;
			}
		});

		if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
			final String dbLocation = chooser.getSelectedFile().toString();
			this.setChanged();
			this.notifyObservers(dbLocation);
		}
	}

}
