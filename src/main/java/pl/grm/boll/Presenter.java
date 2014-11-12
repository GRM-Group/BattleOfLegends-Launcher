package pl.grm.boll;

import java.awt.Color;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import pl.grm.boll.boxes.SettingsDialog;
import pl.grm.boll.config.ConfigHandler;
import pl.grm.boll.panels.AdvPanel;
import pl.grm.boll.panels.GamePanel;
import pl.grm.boll.panels.LoggedPanel;
import pl.grm.boll.panels.LoginPanel;
import pl.grm.boll.updater.Updater;

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
	private Logger			logger;
	private JTextArea		console;
	
	/**
	 * Presenter Constructor
	 */
	public Presenter() {
		configHandler = new ConfigHandler(this);
		configHandler.setIni(configHandler.getFileOp().readConfigFile());
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
		console.append("Logowanie ... \n");
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
						console.append("Zalogowano pomyslnie\n");
						logger.info("Zalogowano pomyslnie\n");
						loginPanel.setVisible(false);
						loggedPanel.getLblLoggedAs().setText(login);
						loggedPanel.setVisible(true);
					} else {
						console.append("Wystapil problem z logowaniem.\n");
						logger.info("Wystapil problem z logowaniem.\n");
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
				console.append("Otwieranie przegladarki ... \n");
				gamePanel.getProgressBar().setIndeterminate(true);
				ConfigHandler.openWebpage("http://grm-dev.pl/bol/web/rejestracja.php");
				return null;
			}
			
			@Override
			protected void done() {
				console.append("Otwarto Przegladarke. \n");
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
				console.append("Opcje\n");
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
				if (configHandler.isUpToDate()) {
					console.append("Launcher is up to date\n");
					progressBar.setValue(20);
				} else {
					progressBar.setValue(7);
					console.append("Launcher must be updated!\n");
					
					progressBar.setStringPainted(true);
					progressBar.setToolTipText("Updating launcher");
					progressBar.setString("Updating launcher");
					progressBar.setValue(9);
					if (Updater.startUpdater(progressBar)) {
						progressBar.setToolTipText("Restarting launcher");
						progressBar.setString("Restarting launcher");
						progressBar.setValue(100);
						Thread.sleep(2000L);
						return true;
					}
				}
				return false;
			}
			
			@Override
			protected void done() {
				console.append("Start game\n");
				try {
					if (super.get()) {
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
				console.append("Wylogowano.\n");
				logger.info("Wylogowano.\n");
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
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public JTextArea getConsole() {
		return this.console;
	}
	
	public ConfigHandler getConfigHandler() {
		return configHandler;
	}
	
}
