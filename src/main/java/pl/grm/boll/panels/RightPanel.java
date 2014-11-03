package pl.grm.boll.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import pl.grm.boll.Presenter;

/**
 * Contains all setting panels with fields, buttons, etc.
 */
public class RightPanel extends JPanel {
	private LoginPanel	loginPanel;
	private GamePanel	gamePanel;
	private AdvPanel	advPanel;
	private Presenter	presenter;
	
	/**
	 * Create the right panel.
	 * 
	 * @param presenter
	 */
	public RightPanel(Presenter presenterT) {
		this.presenter = presenterT;
		setLayout(new BorderLayout(0, 0));
		loginPanel = new LoginPanel(presenter);
		add(loginPanel, BorderLayout.NORTH);
		advPanel = new AdvPanel();
		add(advPanel, BorderLayout.CENTER);
		gamePanel = new GamePanel(presenter);
		add(gamePanel, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(300, 400));
	}
	
	/**
	 * @return {@link LoginPanel}
	 */
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}
	
	/**
	 * @return {@link AdvPanel}
	 */
	public AdvPanel getAdvPanel() {
		return advPanel;
	}
	
	/**
	 * @return {@link GamePanel}
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}
}
