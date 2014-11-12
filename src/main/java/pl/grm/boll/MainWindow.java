package pl.grm.boll;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import pl.grm.boll.panels.LeftPanel;
import pl.grm.boll.panels.RightPanel;

/**
 * Main Window of Game Launcher
 * Contains left and right Panels inside own contentPane Panel.
 * Gets a reference to Presenter object.
 */
public class MainWindow extends JFrame {
	private static final long	serialVersionUID	= 1L;
	private JPanel				contentPane;
	private RightPanel			rightPanel;
	private LeftPanel			leftPanel;
	private Presenter			presenter;
	
	/**
	 * Create the frame.
	 */
	public MainWindow(Presenter presenterT) {
		this.presenter = presenterT;
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		setTitle("Battle Of Legends Launcher");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.setPreferredSize(setupBounds());
		leftPanel = new LeftPanel(presenter);
		contentPane.add(leftPanel);
		rightPanel = new RightPanel(presenter);
		contentPane.add(rightPanel);
		setMinimumSize(new Dimension(400, 400));
		setBackground(presenter.getBgColor());
		pack();
		presenter.addWindow(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(MainWindow.this,
						"Are you sure you want to exit the program?",
						"Exit Program Message Box", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					presenter.getConfigHandler().getLogger().info("Launcher Closing ...");
					try {
						Thread.sleep(1000L);
					}
					catch (InterruptedException e1) {}
					dispose();
				}
			}
		});
	}
	
	/**
	 * Calculates frame's dimensions
	 * 
	 * @return {@link Dimension} (x,y)
	 */
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
	
	/**
	 * @return {@link LeftPanel} of Launcher
	 */
	public LeftPanel getLeftPanel() {
		return leftPanel;
	}
	
	/**
	 * @return {@link RightPanel} of Launcher
	 */
	public RightPanel getRightPanel() {
		return rightPanel;
	}
}
