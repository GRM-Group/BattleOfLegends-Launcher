package pl.grm.boll.components;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Contains game buttons, progress Bar and game info
 */
public class GamePanel extends JPanel {
	private JButton			launchButton;
	private JCheckBox		typeCheckBox;
	private JProgressBar	updateProgressBar;
	private JLabel			patchLabel;
	private JButton			settingsButton;
	private JLabel			versioniLabel;
	private JLabel			versionLabel;
	private JLabel			label;
	
	/**
	 * Create the game panel.
	 */
	public GamePanel() {
		setLayout(new GridLayout(2, 4, 0, 0));
		
		versioniLabel = new JLabel("Game version:");
		add(versioniLabel);
		
		versionLabel = new JLabel("0.0.0");
		add(versionLabel);
		
		label = new JLabel("");
		add(label);
		
		settingsButton = new JButton("Opcje");
		add(settingsButton);
		
		patchLabel = new JLabel("_______________");
		add(patchLabel);
		
		updateProgressBar = new JProgressBar();
		updateProgressBar.setStringPainted(true);
		add(updateProgressBar);
		
		typeCheckBox = new JCheckBox("Online");
		add(typeCheckBox);
		
		launchButton = new JButton("Start");
		add(launchButton);
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
	public JProgressBar getUpdateProgressBar() {
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
