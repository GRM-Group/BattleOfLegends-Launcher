package pl.grm.bol.launcher.core;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import pl.grm.bol.launcher.MainWindow;
import pl.grm.bol.launcher.Presenter;
import pl.grm.bol.launcher.boxes.SettingsDialog;
import pl.grm.bol.launcher.panels.RightPanel;
import pl.grm.bol.launcher.updater.GameStarter;
import pl.grm.bol.lib.BLog;

public class SwingWorkersImpl {
	private static Presenter	presenter;
	private static char[]		pass;
	
	public static void setPresenter(Presenter presenter) {
		SwingWorkersImpl.presenter = presenter;
	}
	
	public static SwingWorker<Boolean, Void> Login(char[] password) {
		pass = password;
		final MainWindow mainWindow = presenter.getMainWindow();
		final RightPanel rightPanel = mainWindow.getRightPanel();
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				rightPanel.getLoginPanel().getLoginButton().setEnabled(false);
				rightPanel.getGamePanel().getProgressBar().setIndeterminate(true);
				boolean success = presenter.getConfigHandler().login(presenter.getLogin(), pass);
				pass = null;
				return success;
			}
			
			@Override
			protected void done() {
				try {
					
					rightPanel.getLoginPanel().getLoginButton().setEnabled(true);
					rightPanel.getGamePanel().getProgressBar().setIndeterminate(false);
					if (super.get()) {
						presenter.getConfigHandler().getLogger().info("Logged successfully!");
						rightPanel.getLoginPanel().setVisible(false);
						rightPanel.getLoggedPanel().getLblLoggedAs().setText(presenter.getLogin());
						rightPanel.getLoggedPanel().setVisible(true);
					} else {
						presenter.getConfigHandler().getLogger().info("Problem with logging in!");
					}
				}
				catch (InterruptedException e) {
					presenter.getConfigHandler().getLogger().log(Level.SEVERE, e.toString(), e);
				}
				catch (ExecutionException e) {
					presenter.getConfigHandler().getLogger().log(Level.SEVERE, e.toString(), e);
				}
			}
		};
		return worker;
	}
	
	public static SwingWorker<Void, Void> Register() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				presenter.getMainWindow().getRightPanel().getGamePanel().getProgressBar()
						.setIndeterminate(true);
				ConfigHandler.openWebpage("http://grm-dev.pl/bol/web/rejestracja.php");
				return null;
			}
			
			@Override
			protected void done() {
				presenter.getConfigHandler().getLogger().info("Register page has been opened!\n");
				presenter.getMainWindow().getRightPanel().getGamePanel().getProgressBar()
						.setIndeterminate(false);
			}
		};
		return worker;
	}
	
	public static SwingWorker<Boolean, Void> Setings() {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				SettingsDialog setDBox = new SettingsDialog(presenter);
				setDBox.setVisible(true);
				setDBox.setModal(true);
				return null;
			}
			
			@Override
			protected void done() {
				presenter.getConfigHandler().getLogger().info("Settings");
			}
		};
		return worker;
	}
	
	public static SwingWorker<Boolean, Void> Start() {
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			BLog	logger	= presenter.getConfigHandler().getLogger();
			
			@Override
			protected Boolean doInBackground() throws Exception {
				presenter.getGamePanel().getLaunchButton().setEnabled(false);
				JProgressBar progressBar = presenter.getGamePanel().getProgressBar();
				progressBar.setValue(5);
				if (!presenter.getConfigHandler().isUpToDate("Game")) {
					progressBar.setValue(7);
					logger.info("Game must be updated!");
					int confirmed = JOptionPane.showConfirmDialog(presenter.getMainWindow(),
							"Are you sure you want to update the game?",
							"Exit Program Message Box", JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION) {
						logger.info("Game Updating ...");
						progressBar.setStringPainted(true);
						progressBar.setToolTipText("Updating Game");
						progressBar.setString("Updating Game");
						progressBar.setValue(9);
					} else {
						logger.info("Game update cancelled!");
						return false;
					}
				} else {
					logger.info("Game is up to date");
					progressBar.setValue(20);
					logger.info("Starting game");
				}
				GameStarter gameStarter = new GameStarter();
				gameStarter.start(presenter);
				logger.info("Game update failed!");
				return true;
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
				presenter.getGamePanel().getLaunchButton().setEnabled(true);
				JProgressBar progressBar = presenter.getGamePanel().getProgressBar();
				progressBar.setValue(0);
				progressBar.setStringPainted(false);
			}
		};
		return worker;
	}
	
	public static SwingWorker<Void, Void> Logout() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				presenter.getLoginPanel().setVisible(true);
				presenter.getLoggedPanel().setVisible(false);
				presenter.getConfigHandler().getLogger().info("Logout!");
				return null;
			}
		};
		return worker;
	}
}
