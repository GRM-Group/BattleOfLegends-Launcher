package pl.grm.boll.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.grm.boll.Presenter;

public class LoggedPanel extends JPanel {

	private Presenter presenter;
	private JPanel loggedAsPanel;
	private JLabel lblLoggedAs;
	private JButton btnLogout;
	private JPanel playerInfoPanel;

	/**
	 * Create the panel.
	 * 
	 * @param presenterT
	 */
	public LoggedPanel(Presenter presenterT) {
		this.presenter = presenterT;
		setLayout(new BorderLayout(0, 0));

		loggedAsPanel = new JPanel();
		loggedAsPanel.setBackground(presenter.getBgColor());
		add(loggedAsPanel, BorderLayout.NORTH);

		JLabel lblloggedAsL = new JLabel("Logged as:");
		loggedAsPanel.add(lblloggedAsL);

		lblLoggedAs = new JLabel("Cos");
		loggedAsPanel.add(lblLoggedAs);

		playerInfoPanel = new JPanel();
		playerInfoPanel.setBackground(presenter.getBgColor());
		add(playerInfoPanel, BorderLayout.CENTER);
		playerInfoPanel.setLayout(new GridLayout(1, 0, 0, 0));

		btnLogout = new JButton("Logout");
		add(btnLogout, BorderLayout.SOUTH);
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.pressedLogoutButton();
			}
		});
		setBackground(presenter.getBgColor());
	}

	public JLabel getLblLoggedAs() {
		return this.lblLoggedAs;
	}

	public JPanel getPlayerInfoPanel() {
		return this.playerInfoPanel;
	}

}
