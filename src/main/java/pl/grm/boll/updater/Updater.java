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

	public static void main(String[] args) {
		checkoutServerVersion();
		downloadNewVersion();
		changeOldName();
		changeNewName();
		deleteOldFile();
		runLauncher();
	}

	public static boolean startUpdater(String jarFileLoc) {
		Updater.jarFileLoc = jarFileLoc;
		dir = new File(BoL_Conf_Loc);
		if (!dir.exists()) {
			dir.mkdir();
		}
		downloadUpdater();

		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";

		ProcessBuilder processBuilder = new ProcessBuilder(javaPath, "-jar",
				ConfigHandler.BoL_Conf_Loc + fileName, jarFileLoc,
				Updater.class.getName());
		Process process;

		log = new File(BoL_Conf_Loc + "updater.log");
		try {
			processBuilder.directory(dir);
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(Redirect.appendTo(log));
			process = processBuilder.start();
			assert processBuilder.redirectInput() == Redirect.PIPE;
			assert processBuilder.redirectOutput().file() == log;
			assert process.getInputStream().read() == -1;
			process.waitFor();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	private static void downloadUpdater() {
		fileName = "BoL-Updater-" + version + "-SNAPSHOT.jar";
		try {
			URL website = new URL(ConfigHandler.SERVER_LINK
					+ "jenkins/artifacts/" + fileName);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			fos = new FileOutputStream(ConfigHandler.BoL_Conf_Loc + fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void checkoutServerVersion() {
		Ini sIni = new Ini();
		URL url;
		try {
			url = new URL(ConfigHandler.SERVER_VERSION_LINK);
			sIni.load(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void changeOldName() {
		String fileName = "";
		File file = new File(fileName + ".jar");
		File file2 = new File(fileName + "_old.jar");
		if (file2.exists())
			try {
				throw new java.io.IOException("file exists");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
}
