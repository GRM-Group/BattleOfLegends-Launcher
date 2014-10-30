package pl.grm.boll;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import pl.grm.boll.components.LeftPanel;
import pl.grm.boll.components.RightPanel;

public class MainWindow extends JFrame {
	private JPanel contentPane;
	private JPanel leftPanel, rightPanel;
	private Presenter presenter;

	/**
	 * Create the frame.
	 */
	public MainWindow(Presenter presenter) {
		this.presenter = presenter;
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		setTitle("Battle Of Legends Launcher");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.setPreferredSize(setupBounds());
		leftPanel = new LeftPanel();
		contentPane.add(leftPanel);
		rightPanel = new RightPanel();
		contentPane.add(rightPanel);
		pack();
		presenter.addWindow(this);
	}

	private Dimension setupBounds() {
		Dimension dim;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		int frameWidth = screenWidth / 2 - screenWidth / 20;
		int frameHeight = frameWidth * 3 / 4;
		setBounds(screenWidth / 4, screenHeight / 2 - frameHeight / 2, 0, 0);
		dim = new Dimension(frameWidth, frameHeight);

		return dim;
	}

	@Override
	public JPanel getContentPane() {
		return contentPane;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public JPanel getRightPanel() {
		return rightPanel;
	}
}
