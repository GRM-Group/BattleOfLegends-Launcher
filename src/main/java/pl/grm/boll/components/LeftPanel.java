package pl.grm.boll.components;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import pl.grm.boll.Presenter;

/**
 * Contains Console
 */
public class LeftPanel extends JPanel {
	JTextArea			textArea;
	private Presenter	presenter;
	
	/**
	 * Create the left panel.
	 * 
	 * @param presenter
	 */
	public LeftPanel(Presenter presenterT) {
		this.presenter = presenterT;
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
