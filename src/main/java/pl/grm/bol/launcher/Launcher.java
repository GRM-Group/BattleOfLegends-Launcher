package pl.grm.bol.launcher;

import javax.swing.SwingUtilities;

import pl.grm.bol.launcher.core.ConfigHandler;
import pl.grm.bol.lib.TypeOfProject;
import pl.grm.bol.lib.net.UpdateFrame;

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
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					UpdateFrame updateDBox = new UpdateFrame("Launcher Updater Downloader",
							TypeOfProject.LAUNCHER);
					updateDBox.setVisible(true);
					updateDBox.getButtonUpdate().setEnabled(true);
				}
			});
		}
	}
}