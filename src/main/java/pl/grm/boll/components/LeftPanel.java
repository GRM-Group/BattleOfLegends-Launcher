package pl.grm.boll.components;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Contains Console
 */
public class LeftPanel extends JPanel {
	JTextArea	textArea;
	
	/**
	 * Create the left panel.
	 */
	public LeftPanel() {
		setLayout(new CardLayout(0, 0));
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(100, 100));
		textArea.setEditable(false);
		add(textArea);
	}
	
	/**
	 * @return {@link JTextArea} Console
	 */
	public JTextArea getConsole() {
		return textArea;
	}
}
