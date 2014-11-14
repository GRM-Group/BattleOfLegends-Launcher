package pl.grm.boll.net.updater;

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
import java.util.logging.Logger;

import javax.swing.JProgressBar;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import pl.grm.boll.config.ConfigHandler;
import pl.grm.boll.lib.FileOperation;

public class UpdaterStarter {
	private static String		APP_DATA		= System.getenv("APPDATA");
	private static String		BoL_Conf_Path	= APP_DATA + "\\BOL\\";
	private static String		version			= "0.0.0";
	private static String		fileName;
	private static String		jarFileAbsPath;
	private static JProgressBar	progressBar;
	private static Logger		logger;
	private static String		launcherPId;
	
	/**
	 * Called by other main class than this updater.
	 * <p>
	 * It downloads this jar with that class main in classpath and run it with
	 * parameters :
	 * 
	 * @param progressBar
	 * @return true if everything went fine. Otherwise false.
	 */
	public static synchronized boolean startUpdater(JProgressBar progressBar) {
		UpdaterStarter.progressBar = progressBar;
		try {
			UpdaterStarter.jarFileAbsPath = FileOperation.getCurrentJar(UpdaterStarter.class);
		}
		catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		File confDir = new File(BoL_Conf_Path);
		if (!confDir.exists()) {
			confDir.mkdir();
		}
		checkoutServerVersion();
		downloadUpdater();
		
		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin" + separator + "java";
		launcherPId = FileOperation.getProcessId(System.getProperty("user.dir")).trim();
		
		ProcessBuilder processBuilder = new ProcessBuilder(javaPath, "-jar",
				ConfigHandler.BoL_Conf_Loc + fileName, jarFileAbsPath, launcherPId,
				System.getProperty("user.dir"));
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
	
	/**
	 * Downloads Updater jar to run it.
	 */
	private static void downloadUpdater() {
		progressBar.setValue(14);
		fileName = "BoL-Updater-" + version + "-SNAPSHOT.jar";
		if (!new File(ConfigHandler.BoL_Conf_Loc + fileName).exists()) {
			try {
				URL website = new URL(ConfigHandler.SERVER_LINK + "jenkins/artifacts/" + fileName);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos;
				fos = new FileOutputStream(ConfigHandler.BoL_Conf_Loc + fileName);
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
		}
		progressBar.setValue(80);
	}
	
	/**
	 * Check version of launcher on the web server.
	 */
	private static void checkoutServerVersion() {
		Ini sIni = new Ini();
		URL url;
		try {
			url = new URL(ConfigHandler.SERVER_VERSION_LINK);
			sIni.load(url);
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
		version = sIni.get("Launcher", "last_version");
	}
}
