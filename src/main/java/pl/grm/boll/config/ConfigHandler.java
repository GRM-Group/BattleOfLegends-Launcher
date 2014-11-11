package pl.grm.boll.config;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JTextArea;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import pl.grm.boll.Presenter;
import pl.grm.boll.rmi.ConnHandler;

public class ConfigHandler {
	private String			APP_DATA		= System.getenv("APPDATA");
	private String			BoL_Conf_Loc	= APP_DATA + "\\BOL\\";
	private String			logFileName		= "launcher.log";
	private String			configFileName	= "config.ini";
	private Wini			ini;
	private File			file;
	private Presenter		presenter;
	private ConnHandler		connHandler;
	private FileHandler		fHandler;
	private Logger			logger;
	private JTextArea		console;
	private FileOperation	fileOp;
	
	public ConfigHandler(Presenter presenter) {
		this.presenter = presenter;
		setupLogger();
		connHandler = new ConnHandler(logger);
		fileOp = new FileOperation(logger);
		presenter.setLogger(logger);
	}
	
	public boolean checkLastVersion() {
		Ini sIni = new Ini();
		FileReader fr;
		VersionComparator cmp = new VersionComparator();
		try {
			URL url = new URL("http://grm-dev.pl/bol/version.ini");
			sIni.load(url);
			String sVersion = sIni.get("Launcher", "last_version");
			String lVersion = ini.get("Launcher", "version");
			
			int result = cmp.compare(sVersion, lVersion);
			String lol = "==";
			if (result < 0) {
				lol = "<";
			} else if (result > 0) {
				lol = ">";
			}
			System.out.printf("%s %s %s \n", sVersion, lol, lVersion);
			return true;
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
			createIniFile();
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
	
	private void createIniFile() {
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
		catch (Exception e) {
			
		}
	}
	
	public Boolean login(String login, char[] password) {
		return connHandler.login(login, Hashing.hash(new String(password), "MD5"));
	}
	
	public void setConsole(JTextArea console) {
		this.console = console;
		connHandler.setConsole(console);
	}
	
	public FileOperation getFileOp() {
		return fileOp;
	}
}
