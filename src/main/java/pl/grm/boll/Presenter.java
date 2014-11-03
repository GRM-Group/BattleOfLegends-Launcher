package pl.grm.boll;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import pl.grm.boll.config.ConfigHandler;

/**
 * Presenter from MVP model.
 * <p>
 * Contains all launcher logic and send all data to main model -> Game
 * <p>
 * This presenter has also some kind of temp model.
 */
public class Presenter {
	private MainWindow mainWindow;
	private ConfigHandler configHandler;
	private JTextArea console;
	private JPanel loginPanel;
	private JPanel infoPanel;
	private JPanel gamePanel;

	/**
	 * Presenter Constructor
	 */
	public Presenter() {
		configHandler = new ConfigHandler(this);
		configHandler.readConfigFile();
	}

	/**
	 * Adds reference to mainWindow and its components.
	 * 
	 * @param mainWindow
	 */
	public void addWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		saveComponentsRefs();
	}

	private void saveComponentsRefs() {
		this.console = this.mainWindow.getLeftPanel().getConsole();
		this.loginPanel = this.mainWindow.getRightPanel().getLoginPanel();
		this.infoPanel = this.mainWindow.getRightPanel().getInfoPanel();
		this.gamePanel = this.mainWindow.getRightPanel().getGamePanel();
	}

	public synchronized void pressedLoginButton(ActionEvent e) {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				return null;
			}

			@Override
			protected void done() {
				console.append("Login\n");
			}
		};
		worker.execute();
	}

	public synchronized void pressedRegisterButton(String loginField,
			char[] passwordField) {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				return configHandler.register(loginField, passwordField);
			}

			@Override
			protected void done() {
				try {
					console.append(super.get().toString());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		worker.execute();
	}

	public synchronized void pressedSettingsButton() {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
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
				return null;
			}

			@Override
			protected void done() {
				console.append("Start\n");
			}
		};
		worker.execute();
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}
}
