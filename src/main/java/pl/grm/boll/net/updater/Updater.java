package pl.grm.boll.net.updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JProgressBar;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import pl.grm.boll.config.ConfigHandler;
import pl.grm.boll.lib.FileOperation;

public class Updater {
	private static String		APP_DATA		= System.getenv("APPDATA");
	private static String		BoL_Conf_Path	= APP_DATA + "\\BOL\\";
	private static String		version			= "0.0.0";
	private static String		fileName;
	private static String		jarFileAbsPath;
	private static JProgressBar	progressBar;
	private static Logger		logger;
	private static FileHandler	fHandler;
	private static String		logFileName		= "updater.log";
	private static String		launcherPId;
	private static String		launcherDirPath;
	
	public static void main(String[] args) {
		setupLogger();
		if (!assignArgs(args)) { return; }
		try {
			killExec("taskkill /pid " + launcherPId);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		try {
			Thread.sleep(4000L);
		}
		catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		checkoutServerVersion();
		downloadNewLauncher();
		madeBackup();
		if (moveNewLauncherFromTemp()) {
			updateConfig();
			deleteBackupFile();
			runLauncher();
		} else {
			restoreBackup();
		}
	}
	
	/**
	 * Credit args to fields
	 * 
	 * @param args
	 * @return true if successufully assigned.
	 */
	private static boolean assignArgs(String[] args) {
		if (args.length != 3) {
			logger.info("Bad arguments!");
			return false;
		}
		jarFileAbsPath = args[0];
		launcherPId = args[1];
		launcherDirPath = args[2];
		if (jarFileAbsPath.contains("/BoL-Launcher_Client/bin/")) { return false; }
		return true;
	}
	
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
		Updater.progressBar = progressBar;
		try {
			Updater.jarFileAbsPath = FileOperation.getCurrentJar(Updater.class);
		}
		catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		File confDir = new File(BoL_Conf_Path);
		if (!confDir.exists()) {
			confDir.mkdir();
		}
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
			Process process = processBuilder.start();
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
		checkoutServerVersion();
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
	 * Kills specified proccess
	 * 
	 * @param processString
	 * @return list Of Proccess
	 * @throws IOException
	 */
	private static ArrayList<String> killExec(String processString) throws IOException {
		String outStr = "";
		ArrayList<String> processOutList = new ArrayList<String>();
		int i = -1;
		
		Process p = Runtime.getRuntime().exec(processString);
		// OutputStream out = p.getOutputStream();
		InputStream in = p.getInputStream();
		
		x11 : while ((i = in.read()) != -1) {
			if ((char) i == '\n') {
				processOutList.add((outStr));
				outStr = "";
				continue x11;
			}
			outStr += (char) i;
		}
		return processOutList;
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
	
	/**
	 * Downloads new Launcher.
	 */
	private static void downloadNewLauncher() {
		fileName = "BoL-Launcher-" + version + "-SNAPSHOT.jar";
		try {
			URL website = new URL(ConfigHandler.SERVER_LINK + "jenkins/artifacts/" + fileName);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			fos = new FileOutputStream(fileName);
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
	
	/**
	 * change the name of old file by adding '_old'
	 */
	private static synchronized void madeBackup() {
		String fileNameC = jarFileAbsPath.substring(0, jarFileAbsPath.length() - 4);
		File oldFile = new File(jarFileAbsPath);
		File newFile = new File(fileNameC + "_old.jar");
		if (!newFile.exists()) {
			if (oldFile.renameTo(newFile)) {
				logger.info("Old file backuped.");
			} else {
				logger.info("Smth went wrong.");
			}
		} else {
			logger.info("File Exists");
		}
	}
	
	/**
	 * Restore backuped file '_old'
	 */
	private static void restoreBackup() {
		String fileNameC = jarFileAbsPath.substring(0, jarFileAbsPath.length() - 4);
		fileNameC = fileNameC.concat("_old.jar");
		File oldFile = new File(fileNameC);
		File newFile = new File(jarFileAbsPath);
		if (!newFile.exists()) {
			if (oldFile.renameTo(newFile)) {
				logger.info("Backup restored!");
			} else {
				logger.info("Smth went wrong.");
			}
		} else {
			logger.info("File Exists");
		}
	}
	
	/**
	 * delete (with '_old') file
	 */
	private static void deleteBackupFile() {
		String fileNameC = jarFileAbsPath.substring(0, jarFileAbsPath.length() - 4);
		File file = new File(fileNameC + "_old.jar");
		file.delete();
		try {
			File file2 = new File(FileOperation.getCurrentJar(Updater.class));
			file2.deleteOnExit();
		}
		catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	/**
	 * replace old Launcher with new one
	 * 
	 * @return
	 */
	private static boolean moveNewLauncherFromTemp() {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			File fromFile = new File(BoL_Conf_Path + fileName);
			File toFile = new File(launcherDirPath + "\\" + fileName);
			logger.info("New file: " + launcherDirPath + "\\" + fileName);
			inStream = new FileInputStream(fromFile);
			outStream = new FileOutputStream(toFile);
			
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
			fromFile.delete();
			logger.info("Launcher updated successfully!");
			return true;
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}
	
	/**
	 * Update config ini with launcher param version
	 */
	private static void updateConfig() {
		try {
			FileOperation.writeConfigParamLauncher(FileOperation.readConfigFile(Updater.class),
					"version", version);
		}
		catch (IOException | IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	/**
	 * Runs the Launcher
	 */
	private static void runLauncher() {
		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin" + separator + "java";
		File dir = new File(launcherDirPath);
		ProcessBuilder processBuilder = new ProcessBuilder(javaPath, "-jar", launcherDirPath + "\\"
				+ fileName);
		try {
			processBuilder.directory(dir);
			Process process = processBuilder.start();
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	/**
	 * Configure Logger to log infos & warnings
	 */
	private static void setupLogger() {
		logger = Logger.getLogger(ConfigHandler.class.getName());
		try {
			fHandler = new FileHandler(BoL_Conf_Path + logFileName, 1048476, 1, true);
			logger.addHandler(fHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fHandler.setFormatter(formatter);
		}
		catch (SecurityException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
}
