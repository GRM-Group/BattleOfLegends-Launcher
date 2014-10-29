package pl.grm.boll.components;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class RightPanel extends JPanel {
	private JTextField fieldLogin;
	private JLabel login;
	private JPasswordField fieldPassword;
	private JLabel haslo;
	private JButton btnUruchom;
	private JLabel update;
	private JLabel wersjaGry;
	private JLabel version;
	private JCheckBox chckbxRemember;
	private JCheckBox chckbxAutoLogin;
	private GroupLayout groupLayout;
	private JButton btnLogin;
	private JProgressBar progressBar;
	private JCheckBox chckbxOnline;

	/**
	 * Create the panel.
	 */
	public RightPanel() {
		login = new JLabel("Login");
		fieldLogin = new JTextField();
		fieldLogin.setColumns(10);
		haslo = new JLabel("Has\u0142o");
		fieldPassword = new JPasswordField();
		btnLogin = new JButton("Login");
		btnLogin.setMnemonic('l');
		btnUruchom = new JButton("Uruchom");
		update = new JLabel("Najnowsza wersja gry!");
		wersjaGry = new JLabel("Wersja gry");
		version = new JLabel("0.0.0");
		progressBar = new JProgressBar();
		chckbxOnline = new JCheckBox("Online");
		chckbxRemember = new JCheckBox("Zapamietaj has\u0142o");
		chckbxAutoLogin = new JCheckBox("Auto login");
		groupLayout = setupLayout();

		setLayout(groupLayout);
	}

	private GroupLayout setupLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								progressBar,
																								GroupLayout.PREFERRED_SIZE,
																								178,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												wersjaGry)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												version)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				317,
																				Short.MAX_VALUE)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								haslo)
																						.addComponent(
																								login))
																		.addGap(18)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(
																								fieldLogin)
																						.addComponent(
																								fieldPassword,
																								GroupLayout.DEFAULT_SIZE,
																								147,
																								Short.MAX_VALUE)
																						.addComponent(
																								chckbxRemember,
																								Alignment.LEADING)
																						.addComponent(
																								chckbxAutoLogin,
																								Alignment.LEADING)
																						.addComponent(
																								btnLogin,
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createParallelGroup(
																												Alignment.LEADING)
																										.addComponent(
																												btnUruchom)
																										.addComponent(
																												chckbxOnline)))
																		.addGap(50))
														.addComponent(update))));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING,
																false)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								wersjaGry)
																						.addComponent(
																								version))
																		.addGap(327)
																		.addComponent(
																				update)
																		.addGap(12)
																		.addComponent(
																				progressBar,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(11)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								fieldLogin,
																								GroupLayout.PREFERRED_SIZE,
																								25,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								login,
																								GroupLayout.PREFERRED_SIZE,
																								25,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								fieldPassword,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								haslo))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				chckbxRemember)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				chckbxAutoLogin)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnLogin)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				chckbxOnline)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnUruchom)))
										.addContainerGap()));
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		return groupLayout;
	}
}
