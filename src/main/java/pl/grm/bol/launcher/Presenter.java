package pl.grm.bol.launcher;

import java.awt.Color;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import pl.grm.bol.launcher.boxes.SettingsDialog;
import pl.grm.bol.launcher.config.BLog;
import pl.grm.bol.launcher.config.ConfigHandler;
import pl.grm.bol.launcher.net.updater.UpdaterStarter;
import pl.grm.bol.launcher.panels.AdvPanel;
import pl.grm.bol.launcher.panels.GamePanel;
import pl.grm.bol.launcher.panels.LoggedPanel;
import pl.grm.bol.launcher.panels.LoginPanel;
import pl.grm.boll.lib.FileOperation;

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
	private char[]			password;
	private Color			bgColor	= new Color(0, 139, 139);
	private BLog			logger;
	private JTextArea		console;
	
	/**
	 * Presenter Constructor
	 */
	public Presenter() {
		configHandler = new ConfigHandler(this);
		try {
			configHandler.setIni(FileOperation.readConfigFile(ConfigHandler.class));
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds reference to mainWindow and its components.
	 * 
	 * @param mainWindow
	 */
	public void addWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		saveComponentsRefs();
		configHandler.setConsole(console);
		logger.info("\u4eca\u65e5\u306f.");
		if (configHandler.getConnHandler().isConnected()) {
			logger.info("You are online");
		} else {
			logger.info("You are offline");
		}
	}
	
	/**
	 * Saves references to objects in panels.
	 */
	private void saveComponentsRefs() {
		this.console = this.mainWindow.getLeftPanel().getConsole();
		this.loginPanel = this.mainWindow.getRightPanel().getLoginPanel();
		this.loggedPanel = this.mainWindow.getRightPanel().getLoggedPanel();
		this.advPanel = this.mainWindow.getRightPanel().getAdvPanel();
		this.gamePanel = this.mainWindow.getRightPanel().getGamePanel();
	}
	
	public synchronized void pressedLoginButton(String loginT, char[] passwordT) {
		this.login = loginT;
		this.password = passwordT;
		if (!configHandler.getConnHandler().isConnected()) {
			if (!configHandler.getConnHandler().reconnect(mainWindow)) {
				logger.info("No connection to server!");
				return;
			}
		}
		logger.info("Loging in ...\n");
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				loginPanel.getLoginButton().setEnabled(false);
				gamePanel.getProgressBar().setIndeterminate(true);
				boolean success = configHandler.login(login, password);
				return success;
			}
			
			@Override
			protected void done() {
				try {
					loginPanel.getLoginButton().setEnabled(true);
					gamePanel.getProgressBar().setIndeterminate(false);
					if (super.get()) {
						logger.info("Logged successfully!");
						loginPanel.setVisible(false);
						loggedPanel.getLblLoggedAs().setText(login);
						loggedPanel.setVisible(true);
					} else {
						logger.info("Problem with logging in!");
					}
				}
				catch (InterruptedException e) {
					logger.log(Level.SEVERE, e.toString(), e);
				}
				catch (ExecutionException e) {
					logger.log(Level.SEVERE, e.toString(), e);
				}
			}
		};
		worker.execute();
	}
	
	public synchronized void pressedRegisterButton() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				gamePanel.getProgressBar().setIndeterminate(true);
				ConfigHandler.openWebpage("http://grm-dev.pl/bol/web/rejestracja.php");
				return null;
			}
			
			@Override
			protected void done() {
				logger.info("Open register site!\n");
				gamePanel.getProgressBar().setIndeterminate(false);
			}
		};
		worker.execute();
	}
	
	public synchronized void pressedSettingsButton() {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				SettingsDialog setDBox = new SettingsDialog(Presenter.this);
				setDBox.setVisible(true);
				setDBox.setModal(true);
				return null;
			}
			
			@Override
			protected void done() {
				logger.info("Settings");
			}
		};
		worker.execute();
	}
	
	public synchronized void pressedStartButton() {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				gamePanel.getLaunchButton().setEnabled(false);
				JProgressBar progressBar = gamePanel.getProgressBar();
				progressBar.setValue(5);
				if (configHandler.launcherIsUpToDate()) {
					logger.info("Launcher is up to date");
					progressBar.setValue(20);
					logger.info("Start game");
				} else {
					progressBar.setValue(7);
					logger.info("Launcher must be updated!");
					int confirmed = JOptionPane.showConfirmDialog(mainWindow,
							"Are you sure you want to update the launcher?",
							"Exit Program Message Box", JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION) {
						logger.info("Launcher Updating ...");
						progressBar.setStringPainted(true);
						progressBar.setToolTipText("Updating launcher");
						progressBar.setString("Updating launcher");
						progressBar.setValue(9);
						if (UpdaterStarter.startUpdater(logger, progressBar)) {
							progressBar.setToolTipText("Restarting launcher");
							progressBar.setString("Restarting launcher");
							progressBar.setValue(100);
							return true;
						}
						logger.info("Launcher update failed!");
					} else {
						logger.info("Launcher update cancelled!");
					}
				}
				return false;
			}
			
			@Override
			protected void done() {
				try {
					if (super.get()) {
						Thread.sleep(2000L);
						System.exit(0);
					}
				}
				catch (InterruptedException e) {
					logger.log(Level.SEVERE, e.toString(), e);
				}
				catch (ExecutionException e) {
					logger.log(Level.SEVERE, e.toString(), e);
				}
				gamePanel.getLaunchButton().setEnabled(true);
				JProgressBar progressBar = gamePanel.getProgressBar();
				progressBar.setValue(0);
				progressBar.setStringPainted(false);
			}
		};
		worker.execute();
	}
	
	public void pressedLogoutButton() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				loginPanel.setVisible(true);
				loggedPanel.setVisible(false);
				logger.info("Logout!");
				return null;
			}
		};
		worker.execute();
	}
	
	public MainWindow getMainWindow() {
		return mainWindow;
	}
	
	public Color getBgColor() {
		return this.bgColor;
	}
	
	public void setLogger(BLog logger) {
		this.logger = logger;
	}
	
	public JTextArea getConsole() {
		return this.console;
	}
	
	public ConfigHandler getConfigHandler() {
		return configHandler;
	}
}
