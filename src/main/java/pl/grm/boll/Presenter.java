package pl.grm.boll;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import pl.grm.boll.config.ConfigHandler;

/**
 * Presenter from MVP model.
 * <p>
 * Contains all launcher logic and send all data to main model -> Game
 * <p>
 * This presenter has also some kind of temp model.
 */
public class Presenter {
	private MainWindow		mainWindow;
	private ConfigHandler	configHandler;
	private JTextArea		console;
	private JPanel			loginPanel;
	private JPanel			infoPanel;
	private JPanel			gamePanel;
	
	/**
	 * Constructor
	 */
	public Presenter() {
		configHandler = new ConfigHandler();
		configHandler.readConfigFile();
	}
	
	/**
	 * Adds reference to mainWindow and its components.
	 * 
	 * @param mainWindow
	 */
	public void addWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.console = mainWindow.getLeftPanel().getConsole();
		this.loginPanel = this.mainWindow.getRightPanel().getLoginPanel();
		this.infoPanel = this.mainWindow.getRightPanel().getInfoPanel();
		this.gamePanel = this.mainWindow.getRightPanel().getGamePanel();
	}
}
