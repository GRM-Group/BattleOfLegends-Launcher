package pl.grm.boll.components;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LeftPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public LeftPanel() {
		setLayout(new CardLayout(0, 0));
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(100, 100));
		textArea.setEditable(false);
		add(textArea);
	}
}
