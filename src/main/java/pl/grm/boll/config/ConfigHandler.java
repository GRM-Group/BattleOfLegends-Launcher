package pl.grm.boll.config;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JTextArea;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import pl.grm.boll.Presenter;
import pl.grm.boll.rmi.ConnHandler;

public class ConfigHandler {
	private String		APP_DATA		= System.getenv("APPDATA");
	private String		BoL_Conf_Loc	= APP_DATA + "\\BoL\\";
	private String		logFileName		= "launcher.log";
	private String		configFileName	= "config.ini";
	private Wini		ini;
	private File		file;
	private Presenter	presenter;
	private ConnHandler	connHandler;
	private FileHandler	fHandler;
	private Logger		logger;
	private JTextArea	console;
	
	public ConfigHandler(Presenter presenter) {
		this.presenter = presenter;
		setupLogger();
		connHandler = new ConnHandler(logger);
		presenter.setLogger(logger);
	}
	
	private void setupLogger() {
		logger = Logger.getLogger(ConfigHandler.class.getName());
		try {
			fHandler = new FileHandler(BoL_Conf_Loc + logFileName, 1048476, 1, true);
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
		logger.info("Config&Log Location: " + BoL_Conf_Loc);
	}
	
	public void readConfigFile() {
		File dir = new File(BoL_Conf_Loc);
		if (!dir.exists()) {
			dir.mkdir();
		}
		file = new File(BoL_Conf_Loc + configFileName);
		if (file.exists()) {
			readIni();
		} else {
			createFile();
		}
	}
	
	private void readIni() {
		try {
			ini = new Wini(file);
		}
		catch (InvalidFileFormatException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	private void createFile() {
		try {
			file.createNewFile();
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		}
		catch (Exception e) {}
	}
	
	public Boolean login(String login, char[] password) {
		return connHandler.login(login, password);
	}
	
	public void setConsole(JTextArea console) {
		this.console = console;
		connHandler.setConsole(console);
	}
}
