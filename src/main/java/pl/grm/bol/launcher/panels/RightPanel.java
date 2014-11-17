package pl.grm.bol.launcher.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import pl.grm.bol.launcher.Presenter;

/**
 * Contains all setting panels with fields, buttons, etc.
 */
public class RightPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private LoginPanel			loginPanel;
	private LoggedPanel			loggedPanel;
	private GamePanel			gamePanel;
	private AdvPanel			advPanel;
	private Presenter			presenter;
	private JLayeredPane		loggingPane;
	
	/**
	 * Create the right panel.
	 * 
	 * @param presenterT
	 */
	public RightPanel(Presenter presenterT) {
		this.presenter = presenterT;
		setLayout(new BorderLayout(0, 0));
		setMinimumSize(new Dimension(300, 400));
		
		loggingPane = new JLayeredPane();
		loggingPane.setLayout(new CardLayout(0, 0));
		add(loggingPane, BorderLayout.NORTH);
		
		loginPanel = new LoginPanel(presenter);
		loginPanel.setVisible(true);
		loggingPane.add(loginPanel);
		
		loggedPanel = new LoggedPanel(presenter);
		loggedPanel.setVisible(false);
		loggingPane.add(loggedPanel);
		
		advPanel = new AdvPanel(presenter);
		add(advPanel, BorderLayout.CENTER);
		
		gamePanel = new GamePanel(presenter);
		add(gamePanel, BorderLayout.SOUTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		Color color1 = presenter.getBgColor();
		Color color2 = color1.brighter();
		GradientPaint gp = new GradientPaint(0, 20, color1, 80, 10, color2, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
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
	
	/**
	 * @return {@link LoggedPanel}
	 */
	public LoggedPanel getLoggedPanel() {
		return loggedPanel;
	}
}
