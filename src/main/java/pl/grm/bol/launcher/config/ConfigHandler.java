package pl.grm.bol.launcher.config;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import javax.swing.JTextArea;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import pl.grm.bol.launcher.Presenter;
import pl.grm.bol.launcher.math.PasswordHash;
import pl.grm.bol.launcher.math.VersionComparator;
import pl.grm.bol.launcher.net.rmi.ConnHandler;
import pl.grm.bol.lib.BLog;
import pl.grm.bol.lib.Config;

public class ConfigHandler {
	public static final String	LOG_FILE_NAME		= "launcher.log";
	public static final String	LAUNCHER_VERSION	= "0.0.2";
	private Wini				ini;
	private ConnHandler			connHandler;
	private BLog				logger;
	private String				login;
	
	public ConfigHandler(Presenter presenter) {
		logger = new BLog("launcher.log");
		connHandler = new ConnHandler(logger);
		presenter.setLogger(logger);
	}
	
	public String getServerConfig(String site, String x, String y) {
		Ini sIni = new Ini();
		URL url;
		try {
			url = new URL(site);
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
		return sIni.get(x, y);
	}
	
	public boolean isUpToDate(String string) {
		Ini sIni = new Ini();
		VersionComparator cmp = new VersionComparator();
		try {
			URL url = new URL(Config.SERVER_VERSION_LINK);
			sIni.load(url);
			String sVersion = sIni.get(string, "last_version");
			String lVersion = ini.get(string, "version");
			
			int result = cmp.compare(sVersion, lVersion);
			if (result <= 0) {
				return true;
			} else if (result > 0) { return false; }
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
		return false;
	}
	
	public static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		}
		catch (Exception e) {
			
		}
	}
	
	public Boolean login(String login, char[] password) {
		this.login = login;
		String pass = new String(password);
		String salt = connHandler.getSalt(login);
		String hash = PasswordHash.hash(pass, salt);
		return connHandler.login(login, hash);
	}
	
	public void setConsole(JTextArea console) {
		logger.setConsole(console);
	}
	
	public ConnHandler getConnHandler() {
		return this.connHandler;
	}
	
	public BLog getLogger() {
		return this.logger;
	}
	
	public Wini getIni() {
		return ini;
	}
	
	public void setIni(Wini ini) {
		this.ini = ini;
	}
	
	public String getLogin() {
		return login;
	}
}
