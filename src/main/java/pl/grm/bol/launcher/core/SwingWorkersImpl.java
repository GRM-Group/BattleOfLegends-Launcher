package pl.grm.bol.launcher.core;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import javax.swing.SwingWorker;

import pl.grm.bol.launcher.Presenter;

public class SwingWorkersImpl {
	private static Presenter	presenter;
	private static char[]		pass;
	
	public static void setPresenter(Presenter presenter) {
		SwingWorkersImpl.presenter = presenter;
	}
	
	public static SwingWorker<Boolean, Void> Login(char[] password) {
		pass = password;
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				presenter.getMainWindow().getRightPanel().getLoginPanel().getLoginButton()
						.setEnabled(false);
				presenter.getMainWindow().getRightPanel().getGamePanel().getProgressBar()
						.setIndeterminate(true);
				boolean success = presenter.getConfigHandler().login(presenter.getLogin(), pass);
				pass = null;
				return success;
			}
			
			@Override
			protected void done() {
				try {
					presenter.getMainWindow().getRightPanel().getLoginPanel().getLoginButton()
							.setEnabled(true);
					presenter.getMainWindow().getRightPanel().getGamePanel().getProgressBar()
							.setIndeterminate(false);
					if (super.get()) {
						presenter.getConfigHandler().getLogger().info("Logged successfully!");
						presenter.getMainWindow().getRightPanel().getLoginPanel().setVisible(false);
						presenter.getMainWindow().getRightPanel().getLoggedPanel().getLblLoggedAs()
								.setText(presenter.getLogin());
						presenter.getMainWindow().getRightPanel().getLoggedPanel().setVisible(true);
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
}
