package pl.grm.boll.boxes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogBox extends JDialog {
	private JPanel		buttonPanel;
	private JPanel		infoPanel;
	private Dimension	screenSize;
	
	/**
	 * Create the dialog.
	 */
	public DialogBox() {
		infoPanel = new JPanel();
		getContentPane().add(infoPanel, BorderLayout.CENTER);
		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		JButton okBtn = new JButton();
		okBtn.setMnemonic('o');
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okBtn.setText("OK");
		buttonPanel.add(okBtn);
		Dimension dim = setupBounds();
		setPreferredSize(dim);
		setBounds(screenSize.width / 4, screenSize.height / 4, dim.width, dim.height);
	}
	
	public void setInfo(String string) {
		JLabel labelInfo = new JLabel(string);
		labelInfo.setFont(new Font("Arial", 1, 20));
		infoPanel.add(labelInfo);
	}
	
	public void showBox() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Dimension setupBounds() {
		Dimension dim;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		int frameWidth = screenWidth / 5;
		int frameHeight = frameWidth * 1 / 2;
		setBounds(screenWidth / 4, screenHeight / 2 - frameHeight / 2, 0, 0);
		dim = new Dimension(frameWidth, frameHeight);
		return dim;
	}
}
