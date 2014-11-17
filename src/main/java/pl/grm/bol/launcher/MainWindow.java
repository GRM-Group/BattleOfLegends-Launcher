package pl.grm.bol.launcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import pl.grm.bol.launcher.panels.LeftPanel;
import pl.grm.bol.launcher.panels.RightPanel;

/**
 * Main Window of Game Launcher
 * Contains left and right Panels inside own contentPane Panel.
 * Gets a reference to Presenter object.
 */
public class MainWindow extends JFrame {
	private static final long	serialVersionUID	= 1L;
	private JSplitPane			contentPane;
	private RightPanel			rightPanel;
	private LeftPanel			leftPanel;
	private Presenter			presenter;
	
	/**
	 * Create the frame.
	 */
	public MainWindow(Presenter presenterT) {
		this.presenter = presenterT;
		setLAF();
		
		leftPanel = new LeftPanel(presenter);
		rightPanel = new RightPanel(presenter);
		setMainPane();
		presenter.addWindow(this);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Battle Of Legends Launcher");
		setMinimumSize(new Dimension(400, 400));
		setBackground(presenter.getBgColor());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				rightPanel.getLoginPanel().getLoginField().requestFocus();
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(MainWindow.this,
						"Are you sure you want to exit the program?", "Exit Program Message Box",
						JOptionPane.YES_NO_OPTION);
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
		pack();
	}
	
	public void setLAF() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMainPane() {
		contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		contentPane.setDividerLocation(380);
		contentPane.setContinuousLayout(true);
		contentPane.setOneTouchExpandable(true);
		contentPane.setPreferredSize(setupBounds());
		contentPane.setBackground(presenter.getBgColor());
		setContentPane(contentPane);
	}
	
	/**
	 * Calculates frame's dimensions
	 * 
	 * @return {@link Dimension} (x,y)
	 */
	private Dimension setupBounds() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		int frameWidth = screenWidth / 2 - screenWidth / 20;
		int frameHeight = frameWidth * 3 / 4;
		setBounds(screenWidth / 4, screenHeight / 2 - frameHeight / 2, 0, 0);
		Dimension dim = new Dimension(frameWidth, frameHeight);
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
