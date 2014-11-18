package pl.grm.bol.launcher;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import pl.grm.bol.launcher.core.ConfigHandler;
import pl.grm.bol.launcher.core.SwingWorkersImpl;
import pl.grm.bol.launcher.panels.AdvPanel;
import pl.grm.bol.launcher.panels.GamePanel;
import pl.grm.bol.launcher.panels.LoggedPanel;
import pl.grm.bol.launcher.panels.LoginPanel;
import pl.grm.bol.lib.BLog;

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
	private LoginPanel		loginPanel;
	private LoggedPanel		loggedPanel;
	private AdvPanel		advPanel;
	private GamePanel		gamePanel;
	private String			login;
	private Color			bgColor	= new Color(0, 139, 139);
	private BLog			logger;
	private JTextArea		console;
	
	public Presenter() {
		configHandler = new ConfigHandler(this);
	}
	
	/**
	 * Adds reference to mainWindow.
	 * 
	 * @param mainWindow
	 */
	public void addWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		saveComponentsRefs();
		logger.setConsole(console);
		logger.info("\u4eca\u65e5\u306f.");
		if (configHandler.getConnHandler().isConnected()) {
			logger.info("You are online");
		} else {
			logger.info("You are offline");
		}
		SwingWorkersImpl.setPresenter(this);
	}
	
	/**
	 * Saves references to objects in panels.
	 */
	private void saveComponentsRefs() {
		this.console = mainWindow.getLeftPanel().getConsole();
		this.loginPanel = mainWindow.getRightPanel().getLoginPanel();
		this.loggedPanel = mainWindow.getRightPanel().getLoggedPanel();
		this.advPanel = mainWindow.getRightPanel().getAdvPanel();
		this.gamePanel = mainWindow.getRightPanel().getGamePanel();
	}
	
	public synchronized void pressedLoginButton(String loginT, char[] password) {
		this.login = loginT;
		if (!configHandler.getConnHandler().isConnected()) {
			if (!configHandler.getConnHandler().reconnect(mainWindow)) {
				logger.info("No connection to server!");
				return;
			}
		}
		logger.info("Loging in ...\n");
		SwingWorker<Boolean, Void> worker = SwingWorkersImpl.Login(password);
		worker.execute();
	}
	
	public synchronized void pressedRegisterButton() {
		SwingWorker<Void, Void> worker = SwingWorkersImpl.Register();
		worker.execute();
	}
	
	public synchronized void pressedSettingsButton() {
		SwingWorker<Boolean, Void> worker = SwingWorkersImpl.Setings();
		worker.execute();
	}
	
	public synchronized void pressedStartButton() {
		SwingWorker<Boolean, Void> worker = SwingWorkersImpl.Start();
		worker.execute();
	}
	
	public synchronized void pressedLogoutButton() {
		SwingWorker<Void, Void> worker = SwingWorkersImpl.Logout();
		worker.execute();
	}
	
	public void setLogger(BLog logger) {
		this.logger = logger;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}
	
	public Color getBgColor() {
		return this.bgColor;
	}
	
	public JTextArea getConsole() {
		return this.console;
	}
	
	public ConfigHandler getConfigHandler() {
		return configHandler;
	}
	
	public String getLogin() {
		return login;
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	public AdvPanel getAdvPanel() {
		return advPanel;
	}
	
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}
	
	public LoggedPanel getLoggedPanel() {
		return loggedPanel;
	}
	
}
