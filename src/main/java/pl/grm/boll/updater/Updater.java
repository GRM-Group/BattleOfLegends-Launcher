package pl.grm.boll.updater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JProgressBar;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import pl.grm.boll.config.ConfigHandler;

public class Updater {
	private static File log;
	private static String APP_DATA = System.getenv("APPDATA");
	private static String BoL_Conf_Loc = APP_DATA + "\\BOL\\";
	private static String version = "0.0.0";
	private static String fileName;
	private static String jarFileLoc;
	private static File dir;
	private static JProgressBar progressBar;
	private static Logger logger;
	private static FileHandler fHandler;
	private static String logFileName = "updater.log";
	private static String path;

	public static void main(String[] args) {
		setupLogger();
		for (int i = 0; i < args.length; i++) {
			logger.info(i + ": " + args[i]);
		}
		if (args.length != 2) {
			logger.info("Bledna ilosc argumentow!");
			return;
		}
		path = args[0];
		if (path.contains("/BoL-Launcher_Client/bin/")) {
			return;
		}
		checkoutServerVersion();
		downloadNewVersion();
		changeOldName();
		changeNewName();
		deleteOldFile();
		runLauncher();
	}
	public static synchronized boolean startUpdater(String jarFileLoc,
			JProgressBar progressBar) {
		Updater.jarFileLoc = jarFileLoc;
		Updater.progressBar = progressBar;
		dir = new File(BoL_Conf_Loc);
		if (!dir.exists()) {
			dir.mkdir();
		}
		progressBar.setValue(14);
		downloadUpdater();
		progressBar.setValue(80);
		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";

		ProcessBuilder processBuilder = new ProcessBuilder(javaPath, "-jar",
				ConfigHandler.BoL_Conf_Loc + fileName, jarFileLoc);
		Process process;
		progressBar.setValue(85);
		log = new File(BoL_Conf_Loc + "updater.log");
		try {
			processBuilder.directory(dir);
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(Redirect.appendTo(log));
			process = processBuilder.start();
			progressBar.setValue(90);
			assert processBuilder.redirectInput() == Redirect.PIPE;
			assert processBuilder.redirectOutput().file() == log;
			assert process.getInputStream().read() == -1;
			process.waitFor();
			progressBar.setValue(95);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	private static void downloadUpdater() {
		checkoutServerVersion();
		fileName = "BoL-Updater-" + version + "-SNAPSHOT.jar";
		if (!new File(ConfigHandler.BoL_Conf_Loc + fileName).exists()) {
			try {
				URL website = new URL(ConfigHandler.SERVER_LINK
						+ "jenkins/artifacts/" + fileName);
				ReadableByteChannel rbc = Channels.newChannel(website
						.openStream());
				FileOutputStream fos;
				fos = new FileOutputStream(ConfigHandler.BoL_Conf_Loc
						+ fileName);
				progressBar.setValue(35);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				progressBar.setValue(65);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void checkoutServerVersion() {
		Ini sIni = new Ini();
		URL url;
		try {
			url = new URL(ConfigHandler.SERVER_VERSION_LINK);
			sIni.load(url);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} catch (InvalidFileFormatException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		version = sIni.get("Launcher", "last_version");
	}

	private static void downloadNewVersion() {
		String fileName = "BoL-Launcher-" + version + "-SNAPSHOT.jar";
		try {
			URL website = new URL(ConfigHandler.SERVER_LINK
					+ "jenkins/artifacts/" + fileName);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			fos = new FileOutputStream(fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}

	private static void changeOldName() {
		String fileName = "";
		File file = new File(fileName);
		String fileNameC = fileName.substring(0, fileName.length() - 4);
		File file2 = new File(fileNameC + "_old.jar");
		if (file2.exists())
			try {
				throw new java.io.IOException("file exists");
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
		boolean success = file.renameTo(file2);
		if (!success) {

		}
	}
	private static void changeNewName() {
		// TODO Auto-generated method stub

	}

	private static void deleteOldFile() {
		// TODO Auto-generated method stub

	}

	private static void runLauncher() {
		// TODO Auto-generated method stub

	}

	private static void setupLogger() {
		logger = Logger.getLogger(ConfigHandler.class.getName());
		try {
			fHandler = new FileHandler(BoL_Conf_Loc + logFileName, 1048476, 1,
					true);
			logger.addHandler(fHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fHandler.setFormatter(formatter);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
}
