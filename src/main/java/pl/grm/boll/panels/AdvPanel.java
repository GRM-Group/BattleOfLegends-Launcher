package pl.grm.boll.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.grm.boll.Presenter;

/**
 * Contains advertises
 */
public class AdvPanel extends JPanel {
	private JLabel	banerLabel;
	
	/**
	 * Create the info/settings panel.
	 */
	public AdvPanel(Presenter presenter) {
		banerLabel = new JLabel("_________");
		setLayout(new GridLayout(2, 3, 0, 0));
		add(banerLabel);
		setBackground(presenter.getBgColor());
	}
	
	/**
	 * @return {@link JLabel} banner
	 */
	public JLabel getBanerLabel() {
		return banerLabel;
	}
}
