package pl.grm.boll.boxes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import pl.grm.boll.Presenter;

public class SettingsDialog extends JDialog {
	private final JPanel	launcherOptPanel	= new JPanel();
	private JButton			confirmButton;
	private JButton			applyButton;
	private JButton			cancelButton;
	private JTabbedPane		tabbedPane;
	private JPanel			gameMainOptPanel;
	private JPanel			gameGraphicOptPanel;
	private JPanel			gameSoundOptPanel;
	
	/**
	 * Create the dialog.
	 * 
	 * @param presenter
	 */
	public SettingsDialog(Presenter presenter) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setBackground(presenter.getBgColor());
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			tabbedPane.addTab("New tab", null, launcherOptPanel, null);
			launcherOptPanel.setLayout(new FlowLayout());
			launcherOptPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				gameMainOptPanel = new JPanel();
				tabbedPane.addTab("New tab", null, gameMainOptPanel, null);
			}
			{
				gameGraphicOptPanel = new JPanel();
				tabbedPane.addTab("New tab", null, gameGraphicOptPanel, null);
			}
			{
				gameSoundOptPanel = new JPanel();
				tabbedPane.addTab("New tab", null, gameSoundOptPanel, null);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				confirmButton = new JButton("Confirm");
				confirmButton.setActionCommand("OK");
				buttonPane.add(confirmButton);
				getRootPane().setDefaultButton(confirmButton);
			}
			{
				applyButton = new JButton("Apply");
				buttonPane.add(applyButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
