package pl.grm.bol.launcher.panels;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import pl.grm.bol.launcher.Presenter;

/**
 * Contains login/passwd fields and login button.
 */
public class LoginPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private JTextField			loginField;
	private JPasswordField		passwordField;
	private JButton				registerButton;
	private JButton				loginButton;
	private JCheckBox			autologinCheckBox;
	private JCheckBox			rememberCheckBox;
	private JLabel				hasloLabel;
	private JLabel				loginLabel;
	private Presenter			presenter;
	
	/**
	 * Create the login panel.
	 * 
	 * @param presenterT
	 */
	public LoginPanel(Presenter presenterT) {
		this.presenter = presenterT;
		setLayout(new GridLayout(8, 2, 0, 0));
		setBorder(new TitledBorder(new MatteBorder(new ImageIcon("java2sLogo.gif")), "Account"));
		loginLabel = new JLabel("Login:");
		add(loginLabel);
		loginField = new JTextField();
		loginField.setColumns(10);
		add(loginField);
		hasloLabel = new JLabel("Password:");
		add(hasloLabel);
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					loginButton.doClick();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		add(passwordField);
		rememberCheckBox = new JCheckBox("Remember password");
		rememberCheckBox.setMnemonic('p');
		add(rememberCheckBox);
		autologinCheckBox = new JCheckBox("Auto login");
		autologinCheckBox.setMnemonic('a');
		add(autologinCheckBox);
		loginButton = new JButton("Login");
		loginButton.setMnemonic('l');
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.pressedLoginButton(loginField.getText(), passwordField.getPassword());
			}
		});
		add(loginButton);
		registerButton = new JButton("Register");
		registerButton.setMnemonic('r');
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.pressedRegisterButton();
			}
		});
		add(registerButton);
		setBackground(presenter.getBgColor());
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
