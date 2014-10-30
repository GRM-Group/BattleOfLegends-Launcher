package pl.grm.boll.config;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class ConfigHandler {
	private Wini ini;
	private File file;
	private String fileName = "config.ini";

	public ConfigHandler() {

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
}
