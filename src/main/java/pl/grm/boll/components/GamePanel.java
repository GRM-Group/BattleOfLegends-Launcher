package pl.grm.boll.components;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Contains Start button, progress Bar and game info
 */
public class GamePanel extends JPanel {
	private JButton			launchButton;
	private JCheckBox		typeCheckBox;
	private JProgressBar	updateProgressBar;
	private JLabel			patchLabel;
	
	/**
	 * Create the game panel.
	 */
	public GamePanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		patchLabel = new JLabel("_______________");
		add(patchLabel);
		
		updateProgressBar = new JProgressBar();
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
}
