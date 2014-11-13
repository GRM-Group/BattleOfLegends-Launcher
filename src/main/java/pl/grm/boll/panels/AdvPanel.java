package pl.grm.boll.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.grm.boll.Presenter;

/**
 * Contains advertises
 */
public class AdvPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private JLabel				banerLabel;
	private Presenter			presenter;
	
	/**
	 * Create the info/settings panel.
	 */
	public AdvPanel(Presenter presenter) {
		this.presenter = presenter;
		banerLabel = new JLabel("_________");
		setLayout(new GridLayout(2, 3, 0, 0));
		add(banerLabel);
		setOpaque(false);
	}
	
	/**
	 * @return {@link JLabel} banner
	 */
	public JLabel getBanerLabel() {
		return banerLabel;
	}
}
