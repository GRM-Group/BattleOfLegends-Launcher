package pl.grm.boll.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class RightPanel extends JPanel {
	private JPanel loginPanel;
	private JPanel gamePanel;
	private JPanel infoPanel;

	/**
	 * Create the panel.
	 */
	public RightPanel() {
		setLayout(new BorderLayout(0, 0));
		loginPanel = new LoginPanel();
		add(loginPanel, BorderLayout.NORTH);
		gamePanel = new GamePanel();
		add(gamePanel, BorderLayout.SOUTH);
		infoPanel = new InfoPanel();
		add(infoPanel, BorderLayout.CENTER);
	}

	public JPanel getLoginPanel() {
		return loginPanel;
	}

	public JPanel getGamePanel() {
		return gamePanel;
	}

	public JPanel getInfoPanel() {
		return infoPanel;
	}
}
