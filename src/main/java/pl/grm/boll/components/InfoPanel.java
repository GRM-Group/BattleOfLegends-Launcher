package pl.grm.boll.components;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class InfoPanel extends JPanel {

	private JLabel versioniLabel;
	private JLabel versionLabel;
	private JLabel banerLabel;
	private JButton settingsButton;

	/**
	 * Create the panel.
	 */
	public InfoPanel() {

		versioniLabel = new JLabel("Game version:");

		versionLabel = new JLabel("0.0.0");

		banerLabel = new JLabel("_________");

		settingsButton = new JButton("Opcje");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(versioniLabel)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				banerLabel,
																				GroupLayout.DEFAULT_SIZE,
																				135,
																				Short.MAX_VALUE)
																		.addContainerGap(
																				91,
																				Short.MAX_VALUE))
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				versionLabel)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				90,
																				Short.MAX_VALUE)
																		.addComponent(
																				settingsButton)
																		.addGap(19)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(banerLabel,
										GroupLayout.PREFERRED_SIZE, 256,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(versioniLabel)
												.addComponent(versionLabel)
												.addComponent(settingsButton))
								.addContainerGap()));
		setLayout(groupLayout);

	}

	public JLabel getVersionLabel() {
		return versionLabel;
	}

	public JLabel getBanerLabel() {
		return banerLabel;
	}

	public JButton getSettingsButton() {
		return settingsButton;
	}

}
