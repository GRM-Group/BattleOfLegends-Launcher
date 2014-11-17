package pl.grm.bol.launcher.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import pl.grm.bol.launcher.Presenter;

/**
 * Contains game buttons, progress Bar and game info
 */
public class GamePanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private JButton				launchButton;
	private JCheckBox			typeCheckBox;
	private JProgressBar		updateProgressBar;
	private JLabel				patchLabel;
	private JButton				settingsButton;
	private JLabel				versioniLabel;
	private JLabel				versionLabel;
	private JLabel				label;
	private Presenter			presenter;
	
	/**
	 * Create the game panel.
	 * 
	 * @param presenterT
	 */
	public GamePanel(Presenter presenterT) {
		super();
		this.presenter = presenterT;
		setLayout(new GridLayout(2, 4, 0, 0));
		setBorder(new TitledBorder(new MatteBorder(new ImageIcon("java2sLogo.gif")), "Game"));
		versioniLabel = new JLabel("Game version");
		add(versioniLabel);
		versionLabel = new JLabel(presenterT.getConfigHandler().getIni().get("Game", "version"));
		add(versionLabel);
		label = new JLabel("");
		add(label);
		settingsButton = new JButton("Settings");
		settingsButton.setMnemonic('e');
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.pressedSettingsButton();
			}
		});
		add(settingsButton);
		patchLabel = new JLabel("New version: "
				+ presenterT.getConfigHandler().getServerConfigOption("Game", "last_version"));
		add(patchLabel);
		updateProgressBar = new JProgressBar();
		updateProgressBar.setStringPainted(true);
		add(updateProgressBar);
		typeCheckBox = new JCheckBox("Online");
		typeCheckBox.setMnemonic('o');
		add(typeCheckBox);
		launchButton = new JButton("Start");
		launchButton.setMnemonic('s');
		launchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.pressedStartButton();
			}
		});
		add(launchButton);
		
		setOpaque(false);
	}
	
	/**
	 * @return {@link JButton}
	 */
	public JButton getLaunchButton() {
		return launchButton;
	}
	
	/**
	 * @return {@link JCheckBox}
	 */
	public JCheckBox getTypeCheckBox() {
		return typeCheckBox;
	}
	
	/**
	 * @return {@link JProgressBar}
	 */
	public JProgressBar getProgressBar() {
		return updateProgressBar;
	}
	
	/**
	 * @return {@link JButton} settings
	 */
	public JButton getSettingsButton() {
		return settingsButton;
	}
	
	/**
	 * @return {@link JLabel} version
	 */
	public JLabel getVersionLabel() {
		return versionLabel;
	}
}
