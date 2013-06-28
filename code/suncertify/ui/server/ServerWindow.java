package suncertify.ui.server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import suncertify.ui.UICommand;

/**
 * Server component view used in conjunction with the {@link ServerController}
 * class.
 * <p>
 * The view will display all GUI related components and interact with the
 * {@code ServerController} via action buttons in the {@code ServerView}.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
public class ServerWindow extends JFrame implements ActionListener, ServerStateListener, ServerView {

	private static final long serialVersionUID = 1881033106774792490L;

	private final ServerStateController controller;
	private final ServerStateModel model;

	private final ServerInfoPanel infoPanel = new ServerInfoPanel();
	private final ServerUserEntry userEntryPanel = new ServerUserEntry();

	private final JButton startButton = new JButton();
	private final JButton exitButton = new JButton();

	/**
	 * Instantiates a new {@code ServerView} object with a reference to the
	 * {@link ServerController} object.
	 * 
	 * @param controller
	 *            Translates the user's interactions with the view into actions
	 *            that the {@code ServerController} will perform
	 * @param model
	 *            notifies this {@code ServerView} when there has been a change
	 *            in its state.
	 */
	public ServerWindow(final ServerStateController controller, final ServerStateModel model) {
		super("Bodgitt and Scarper");
		this.controller = controller;
		this.model = model;
		this.model.addServerStateListener(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(this.infoPanel, BorderLayout.NORTH);
		this.add(this.userEntryPanel, BorderLayout.CENTER);
		this.add(this.serverCommandsPanel(), BorderLayout.SOUTH);

		this.pack();

		// Center on screen
		this.setLocationRelativeTo(null);
	}

	private JPanel serverCommandsPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.setupButton(this.startButton, UICommand.START);
		panel.add(this.startButton);

		this.setupButton(this.exitButton, UICommand.EXIT);
		panel.add(this.exitButton);

		return panel;
	}

	private void setupButton(final JButton button, final UICommand command) {
		button.addActionListener(this);
		button.setText(command.getActionCommand());
		button.setToolTipText(command.getActionCommandToolTip());
		button.setMnemonic(command.getKeyEvent());
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent actionEvent) {
		final String actionCommand = actionEvent.getActionCommand();
		if (actionCommand.equals(this.startButton.getActionCommand())) {
			ServerWindow.this.controller.startServer();
		} else if (actionCommand.equals(this.exitButton.getActionCommand())) {
			ServerWindow.this.controller.stopServer();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void display() {
		this.setVisible(true);
	}

	/** {@inheritDoc} */
	@Override
	public ServerInfoPanel getInfoPanel() {
		return this.infoPanel;
	}

	/** {@inheritDoc} */
	@Override
	public void disableStartControls() {
		this.userEntryPanel.disableControls();
		this.startButton.setEnabled(false);
	}

	/** {@inheritDoc} */
	@Override
	public void handleStateChange(final ServerStateNotification stateNotification) {
		this.controller.stateChanged(stateNotification);
	}

	/** {@inheritDoc} */
	@Override
	public boolean validateUserEntry() {
		return this.userEntryPanel.validateUserEntry();
	}

	/** {@inheritDoc} */
	@Override
	public void storeUserSettings() {
		this.userEntryPanel.storeUserEntry();
	}

}
