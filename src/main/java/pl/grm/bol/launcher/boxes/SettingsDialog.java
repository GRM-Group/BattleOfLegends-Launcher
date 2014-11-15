package pl.grm.bol.launcher.boxes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pl.grm.bol.launcher.Presenter;

public class SettingsDialog extends JDialog {
	private JButton confirmButton;
	private JButton applyButton;
	private JButton cancelButton;
	private JTabbedPane tabbedPane;
	private LauncherOptPanel launcherOptPanel;
	private GameMainOptPanel gameMainOptPanel;
	private GameGraphicOptPanel gameGraphicOptPanel;
	private GameSoundOptPanel gameSoundOptPanel;
	private Presenter presenter;
	private JButton resDefButton;

	/**
	 * Create the dialog.
	 * 
	 * @param presenter
	 */
	public SettingsDialog(Presenter presenter) {
		this.presenter = presenter;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setBackground(presenter.getBgColor());

		setupTabs();
		setupButtons();
	}

	private void setupTabs() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(presenter.getBgColor());

		launcherOptPanel = new LauncherOptPanel();
		tabbedPane.addTab("Launcher Options", null, launcherOptPanel, null);

		gameMainOptPanel = new GameMainOptPanel();
		tabbedPane.addTab("Game Options", null, gameMainOptPanel, null);

		gameGraphicOptPanel = new GameGraphicOptPanel();
		tabbedPane.addTab("Graphic Game Options", null, gameGraphicOptPanel,
				null);

		gameSoundOptPanel = new GameSoundOptPanel();
		tabbedPane.addTab("Sound Game Options", null, gameSoundOptPanel, null);

		getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}

	private void setupButtons() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		resDefButton = new JButton("Restore defaults");
		resDefButton.setActionCommand("Defaults");
		buttonPane.add(resDefButton);

		confirmButton = new JButton("Confirm");
		confirmButton.setActionCommand("Confirm");
		buttonPane.add(confirmButton);

		applyButton = new JButton("Apply");
		applyButton.setActionCommand("Apply");
		buttonPane.add(applyButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		getRootPane().setDefaultButton(confirmButton);
	}
}
