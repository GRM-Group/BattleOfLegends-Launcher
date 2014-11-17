package pl.grm.bol.launcher.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;

import javax.swing.JProgressBar;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import pl.grm.bol.launcher.Presenter;
import pl.grm.bol.launcher.net.rmi.ConnHandler;
import pl.grm.bol.lib.BLog;
import pl.grm.bol.lib.Config;

public class GameStarter {
	private static Presenter	presenter;
	private static JProgressBar	progresBar;
	private static BLog			logger;
	private static String		version;
	private static File			gameFile;
	private static String		gameFilePath;
	
	public GameStarter() {
		logger = new BLog("gameinit.log");
	}
	
	public void start(Presenter presenter) {
		GameStarter.presenter = presenter;
		progresBar = presenter.getMainWindow().getRightPanel().getGamePanel().getProgressBar();
		logger.info("Starting update ...");
		boolean runAgain;
		do {
			runAgain = false;
			getServerVersion();
			if (gameFileExists()) {
				switch (getPlayerPermissionLevel()) {
					case 0 :
						runGame(false);
						break;
					case 1 :
						break;
					case 2 :
						break;
					case 3 :
						break;
					case 4 :
						break;
					case 5 :
						break;
					case 6 :
						runGame(true);
						runDev();
						break;
					default :
						logger.info("Wrong Permission Level: ");
				}
			} else {
				logger.info("Problem with game file \n Trying to redownload");
				downloadGame();
				runAgain = true;
			}
		}
		while (runAgain);
		
		// TODO Auto-generated method stub
		
	}
	
	public static void downloadGame(String... params) {
		if (params.length != 0) {
			version = params[0];
		}
		try {
			String fileName = "BattleOfLegends-" + version + "-SNAPSHOT.jar";
			URL website = new URL(Config.SERVER_SITE_LINK + "jenkins/artifacts/" + fileName);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			fos = new FileOutputStream(Config.BOL_MAIN_PATH + fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (MalformedURLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	private static void runDev() {
		runGame(false, true, false);
	}
	
	/**
	 * 0 param: run Server? 1 param: run Client to game Server? 2 param: open to
	 * web?
	 * 
	 * @param runParams
	 */
	private static void runGame(boolean... runParams) {
		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin" + separator + "java";
		String dirPath = System.getProperty("user.dir");
		File dir = new File(dirPath);
		String[] params = {"a", "b", "c"};
		if (runParams[0]) {
			params[0] = "runWithServerToConnect";
		}
		ProcessBuilder processBuilder = new ProcessBuilder(javaPath, "-jar", gameFilePath,
				params[0]);
		try {
			processBuilder.directory(dir);
			processBuilder.start();
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		if (runParams.length >= 2 && runParams[0] && runParams[1]) {
			params[1] = "";
			try {
				Thread.sleep(2000L);
			}
			catch (InterruptedException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
			if (runParams[2]) {
				params[2] = "";
			} else {
				params[2] = "";
			}
			
			ProcessBuilder processBuilder2 = new ProcessBuilder(javaPath, "-jar", gameFilePath,
					"-cp", "pl.grm.bol.devwindow.BattleOfLegendsDev", params[1], params[2]);
			try {
				processBuilder2.directory(dir);
				processBuilder2.start();
			}
			catch (IOException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
		}
	}
	
	private static int getPlayerPermissionLevel() {
		ConnHandler connHandler = presenter.getConfigHandler().getConnHandler();
		if (!connHandler.isConnected())
			connHandler.reconnect(progresBar);
		String login = presenter.getConfigHandler().getLogin();
		int permLvl = connHandler.getPlayerPermissionLevel(login);
		return permLvl;
	}
	
	private static void getServerVersion() {
		Ini sIni = new Ini();
		try {
			URL url = new URL(Config.SERVER_VERSION_LINK);
			sIni.load(url);
			version = sIni.get("Game", "last_version");
		}
		catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (InvalidFileFormatException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static boolean gameFileExists() {
		gameFilePath = Config.BOL_MAIN_PATH + "BattleOfLegends-" + version + "-SNAPSHOT.jar";
		gameFile = new File(gameFilePath);
		if (gameFile.exists()) { return true; }
		return false;
	}
}
