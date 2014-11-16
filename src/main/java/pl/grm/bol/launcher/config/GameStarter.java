package pl.grm.bol.launcher.config;

import javax.swing.JProgressBar;

import pl.grm.bol.launcher.Presenter;
import pl.grm.bol.lib.BLog;

public class GameStarter {
	private static Presenter	presenter;
	private static JProgressBar	progresBar;
	private static BLog			logger;
	
	public static void start(Presenter presenter) {
		GameStarter.presenter = presenter;
		progresBar = presenter.getMainWindow().getRightPanel().getGamePanel().getProgressBar();
		logger = new BLog("gameinit.log");
		logger.info("Starting update ...");
		// TODO Auto-generated method stub
		
	}
}
