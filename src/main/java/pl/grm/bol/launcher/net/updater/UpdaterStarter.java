package pl.grm.bol.launcher.net.updater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;

import javax.swing.JProgressBar;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import pl.grm.bol.lib.BLog;
import pl.grm.bol.lib.Config;
import pl.grm.bol.lib.FileOperation;
import pl.grm.bol.lib.MD5HashChecksum;

public class UpdaterStarter {
	private static String		version	= "";
	private static String		fileName;
	private static String		jarFileAbsPath;
	private static JProgressBar	progressBar;
	private static BLog			logger;
	
	/**
	 * Called by other main class than this updater.
	 * <p>
	 * It downloads this jar with that class main in classpath and run it with
	 * parameters :
	 * 
	 * @param logger
	 * @param progressBar
	 * @return true if everything went fine. Otherwise false.
	 */
	public static synchronized boolean startUpdater(BLog logger, JProgressBar progressBar) {
		UpdaterStarter.logger = logger;
		UpdaterStarter.progressBar = progressBar;
		try {
			jarFileAbsPath = FileOperation.getCurrentJarPath(UpdaterStarter.class);
		}
		catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		File dir = new File(Config.BOL_MAIN_PATH);
		if (!dir.exists()) {
			FileOperation.createBOLDir();
		}
		checkoutUpdaterVersion();
		if (!correctFileExists()) {
			logger.info("Downloading updater ...");
			downloadUpdater();
		}
		if (startProcess(dir)) { return true; }
		return false;
	}
	
	/**
	 * Check version of launcher on the web server.
	 */
	private static void checkoutUpdaterVersion() {
		Ini sIni = new Ini();
		URL url;
		try {
			url = new URL(Config.SERVER_VERSION_LINK);
			sIni.load(url);
			version = sIni.get("Updater", "last_version");
			fileName = "BoL-Launcher_Updater-" + version + Config.RELEASE_TYPE;
		}
		catch (MalformedURLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (InvalidFileFormatException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	/**
	 * If file exists than check checksum
	 * 
	 * @return true if correct file already exists on computer
	 */
	private static boolean correctFileExists() {
		File file = new File(Config.BOL_MAIN_PATH + fileName);
		if (file.exists()) {
			try {
				return MD5HashChecksum.isFileCorrect(new File(fileName));
			}
			catch (IOException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
		}
		return false;
	}
	
	/**
	 * Downloads Updater jar to run it.
	 */
	private static void downloadUpdater() {
		progressBar.setValue(14);
		try {
			URL website = new URL(Config.SERVER_SITE_LINK + "jenkins/artifacts/" + fileName);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(Config.BOL_MAIN_PATH + fileName);
			progressBar.setValue(35);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			progressBar.setValue(65);
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
		progressBar.setValue(80);
	}
	
	public static boolean startProcess(File confDir) {
		logger.info("Starting Process...");
		ProcessBuilder processBuilder = new ProcessBuilder(Config.JAVA_PATH, "-jar",
				Config.BOL_MAIN_PATH + fileName, jarFileAbsPath, Config.LAUNCHER_PID,
				Config.USER_DIR);
		progressBar.setValue(85);
		try {
			processBuilder.directory(confDir);
			progressBar.setValue(90);
			processBuilder.start();
			progressBar.setValue(95);
			return true;
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}
}
