package pl.grm.boll.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Contains all setting panels with fields, buttons, etc.
 */
public class RightPanel extends JPanel {
	private JPanel	loginPanel;
	private JPanel	gamePanel;
	private JPanel	infoPanel;
	
	/**
	 * Create the right panel.
	 */
	public RightPanel() {
		setLayout(new BorderLayout(0, 0));
		loginPanel = new LoginPanel();
		add(loginPanel, BorderLayout.NORTH);
		infoPanel = new AdvPanel();
		add(infoPanel, BorderLayout.CENTER);
		gamePanel = new GamePanel();
		add(gamePanel, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(300, 400));
	}
	
	/**
	 * @return {@link LoginPanel}
	 */
	public JPanel getLoginPanel() {
		return loginPanel;
	}
	
	/**
	 * @return {@link AdvPanel}
	 */
	public JPanel getInfoPanel() {
		return infoPanel;
	}
	
	/**
	 * @return {@link GamePanel}
	 */
	public JPanel getGamePanel() {
		return gamePanel;
	}
}
