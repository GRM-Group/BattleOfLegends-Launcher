package pl.grm.boll.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import pl.grm.boll.Presenter;

/**
 * Contains all setting panels with fields, buttons, etc.
 */
public class RightPanel extends JPanel {
	private LoginPanel		loginPanel;
	private LoggedPanel		loggedPanel;
	private GamePanel		gamePanel;
	private AdvPanel		advPanel;
	private Presenter		presenter;
	private JLayeredPane	loggingPane;
	
	/**
	 * Create the right panel.
	 * 
	 * @param presenter
	 */
	public RightPanel(Presenter presenterT) {
		this.presenter = presenterT;
		setLayout(new BorderLayout(0, 0));
		
		loggingPane = new JLayeredPane();
		add(loggingPane, BorderLayout.NORTH);
		loggingPane.setLayout(new CardLayout(0, 0));
		loginPanel = new LoginPanel(presenter);
		loggingPane.add(loginPanel, "name_1217819113516207");
		loggedPanel = new LoggedPanel(presenter);
		loggingPane.add(loggedPanel, "name_1217819120903469");
		loggedPanel.setVisible(false);
		loginPanel.setVisible(true);
		advPanel = new AdvPanel(presenter);
		add(advPanel, BorderLayout.CENTER);
		gamePanel = new GamePanel(presenter);
		add(gamePanel, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(300, 400));
		setBackground(presenter.getBgColor());
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
	
	public LoggedPanel getLoggedPanel() {
		return loggedPanel;
	}
}
