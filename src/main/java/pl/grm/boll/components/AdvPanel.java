package pl.grm.boll.components;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains advertises
 */
public class AdvPanel extends JPanel {
	private JLabel	banerLabel;
	
	/**
	 * Create the info/settings panel.
	 */
	public AdvPanel() {
		
		banerLabel = new JLabel("_________");
		setLayout(new GridLayout(2, 3, 0, 0));
		add(banerLabel);
		
	}
	
	/**
	 * @return {@link JLabel} banner
	 */
	public JLabel getBanerLabel() {
		return banerLabel;
	}
}
