package pl.grm.boll.components;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	private JTextField loginField;
	private JPasswordField passwordField;
	private JButton registerButton;
	private JButton loginButton;
	private JCheckBox autologinCheckBox;
	private JCheckBox rememberCheckBox;
	private JLabel hasloLabel;
	private JLabel loginLabel;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(new GridLayout(8, 2, 0, 0));

		loginLabel = new JLabel("Login:");
		add(loginLabel);

		loginField = new JTextField();
		add(loginField);
		loginField.setColumns(10);

		hasloLabel = new JLabel("Has\u0142o:");
		add(hasloLabel);

		passwordField = new JPasswordField();
		add(passwordField);

		rememberCheckBox = new JCheckBox("Zapamitaj has\u0142o");
		add(rememberCheckBox);

		autologinCheckBox = new JCheckBox("Auto login");
		add(autologinCheckBox);

		loginButton = new JButton("Login");
		add(loginButton);

		registerButton = new JButton("Rejestracja");
		add(registerButton);

	}

	public JTextField getLoginField() {
		return loginField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getRegisterButton() {
		return registerButton;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JCheckBox getAutologinCheckBox() {
		return autologinCheckBox;
	}

	public JCheckBox getRememberCheckBox() {
		return rememberCheckBox;
	}

}
