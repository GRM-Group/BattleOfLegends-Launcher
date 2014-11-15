package pl.grm.bol.launcher.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import pl.grm.bol.launcher.Presenter;

public class LoggedPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private Presenter			presenter;
	private JPanel				loggedAsPanel;
	private JLabel				lblLoggedAs;
	private JButton				btnLogout;
	private JPanel				playerInfoPanel;
	
	/**
	 * Create the panel.
	 * 
	 * @param presenterT
	 */
	public LoggedPanel(Presenter presenterT) {
		this.presenter = presenterT;
		setLayout(new BorderLayout(0, 0));
		setBorder(new TitledBorder(new MatteBorder(new ImageIcon("java2sLogo.gif")), "Account"));
		
		loggedAsPanel = new JPanel();
		loggedAsPanel.setBackground(presenter.getBgColor());
		loggedAsPanel.setOpaque(false);
		add(loggedAsPanel, BorderLayout.NORTH);
		
		JLabel lblloggedAsL = new JLabel("Logged as:");
		loggedAsPanel.add(lblloggedAsL);
		
		lblLoggedAs = new JLabel("Cos");
		loggedAsPanel.add(lblLoggedAs);
		
		playerInfoPanel = new JPanel();
		playerInfoPanel.setBackground(presenter.getBgColor());
		playerInfoPanel.setOpaque(false);
		playerInfoPanel.setLayout(new GridLayout(1, 0, 0, 0));
		add(playerInfoPanel, BorderLayout.CENTER);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.pressedLogoutButton();
			}
		});
		add(btnLogout, BorderLayout.SOUTH);
		setOpaque(false);
	}
	
	public JLabel getLblLoggedAs() {
		return this.lblLoggedAs;
	}
	
	public JPanel getPlayerInfoPanel() {
		return this.playerInfoPanel;
	}
	
}
