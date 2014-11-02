package pl.grm.boll.components;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Contains login/passwd fields and login button.
 */
public class LoginPanel extends JPanel {
	private JTextField		loginField;
	private JPasswordField	passwordField;
	private JButton			registerButton;
	private JButton			loginButton;
	private JCheckBox		autologinCheckBox;
	private JCheckBox		rememberCheckBox;
	private JLabel			hasloLabel;
	private JLabel			loginLabel;
	
	/**
	 * Create the login panel.
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
	
	/**
	 * @return {@link JTextField}
	 */
	public JTextField getLoginField() {
		return loginField;
	}
	
	/**
	 * @return {@link JPasswordField}
	 */
	public JPasswordField getPasswordField() {
		return passwordField;
	}
	
	/**
	 * @return {@link JButton}
	 */
	public JButton getRegisterButton() {
		return registerButton;
	}
	
	/**
	 * @return {@link JButton}
	 */
	public JButton getLoginButton() {
		return loginButton;
	}
	
	/**
	 * @return {@link JCheckBox}
	 */
	public JCheckBox getAutologinCheckBox() {
		return autologinCheckBox;
	}
	
	/**
	 * @return {@link JCheckBox}
	 */
	public JCheckBox getRememberCheckBox() {
		return rememberCheckBox;
	}
}
