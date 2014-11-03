package pl.grm.boll.config;

import java.io.File;
import java.io.IOException;

import javax.swing.JTextArea;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import pl.grm.boll.Presenter;
import pl.grm.boll.database.DBConnect;

public class ConfigHandler {
	private Wini ini;
	private File file;
	private String fileName = "config.ini";
	private Presenter presenter;
	private DBConnect dbConnect;

	public ConfigHandler(Presenter presenter) {
		this.presenter = presenter;
		dbConnect = new DBConnect();
	}

	public void readConfigFile() {
		file = new File(fileName);
		if (file.exists()) {
			readIni();
		} else {
			createFile();
		}
	}

	private void readIni() {
		try {
			ini = new Wini(file);
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createFile() {
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean register(String login, char[] password) {
		JTextArea console = presenter.getMainWindow().getLeftPanel()
				.getConsole();
		console.append(login + "\n");
		dbConnect.register(login, password);
		return dbConnect.isConnected();
	}
}
