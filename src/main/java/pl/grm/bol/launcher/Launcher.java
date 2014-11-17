package pl.grm.bol.launcher;

import javax.swing.SwingUtilities;

import pl.grm.bol.launcher.boxes.LauncherUpdateDialog;
import pl.grm.bol.launcher.core.ConfigHandler;

/**
 * Launcher main class.
 */
public class Launcher {
	private static Presenter	presenter;
	private static MainWindow	mainWindow;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		presenter = new Presenter();
		ConfigHandler configHandler = presenter.getConfigHandler();
		if (configHandler.isUpToDate("Launcher")) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					mainWindow = new MainWindow(presenter);
					mainWindow.setVisible(true);
				}
			});
		} else {
			try {
				LauncherUpdateDialog updateDBox = new LauncherUpdateDialog();
				updateDBox.updateLauncher(presenter);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
