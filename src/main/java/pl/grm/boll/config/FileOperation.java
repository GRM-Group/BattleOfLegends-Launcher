package pl.grm.boll.config;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URLDecoder;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import pl.grm.boll.Presenter;

public class FileOperation {
	private static Logger	logger;
	
	public FileOperation() {
		
	}
	
	public static String getCurrentJar() throws UnsupportedEncodingException {
		String jarFileLoc = "";
		jarFileLoc = URLDecoder.decode(Presenter.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath(), "UTF-8");
		jarFileLoc = jarFileLoc.replace("file:/", "");
		int index = 100;
		if (jarFileLoc.contains("!")) {
			index = jarFileLoc.indexOf("!");
			jarFileLoc = jarFileLoc.substring(0, index);
		}
		if (jarFileLoc.contains("/")) {
			index = jarFileLoc.indexOf("!");
			if (index == 0) {
				jarFileLoc = jarFileLoc.substring(1, jarFileLoc.length());
			}
		}
		return jarFileLoc;
	}
	
	public static String getProcessId(final String fallback) {
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		final int index = jvmName.indexOf('@');
		
		if (index < 1) { return fallback; }
		try {
			return Long.toString(Long.parseLong(jvmName.substring(0, index)));
		}
		catch (NumberFormatException e) {}
		return fallback;
	}
	
	public Logger setupLauncherLogger(FileHandler fHandler) {
		logger = Logger.getLogger(ConfigHandler.class.getName());
		try {
			fHandler = new FileHandler(ConfigHandler.BoL_Conf_Loc + ConfigHandler.logFileName,
					1048476, 1, true);
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
		logger.info("Config&Log Location: " + ConfigHandler.BoL_Conf_Loc);
		logger.info("Launcher is running ...");
		return logger;
	}
	
	public Wini readConfigFile() {
		File dir = new File(ConfigHandler.BoL_Conf_Loc);
		Wini ini = null;
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(ConfigHandler.BoL_Conf_Loc + ConfigHandler.configFileName);
		if (!file.exists()) {
			createIniFile(file);
		}
		ini = readIni(file);
		return ini;
	}
	
	private static Wini readIni(File file) {
		Wini ini = null;
		try {
			ini = new Wini(file);
		}
		catch (InvalidFileFormatException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return ini;
	}
	
	private static void createIniFile(File file) {
		try {
			file.createNewFile();
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
}
