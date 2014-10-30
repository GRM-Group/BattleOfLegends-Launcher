package pl.grm.boll;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import pl.grm.boll.config.ConfigHandler;

public class Presenter {
	private JFrame mainWindow;
	private ConfigHandler configHandler;
	private JTextArea console;

	public Presenter() {
		configHandler = new ConfigHandler();
		configHandler.readConfigFile();
	}

	public void addWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.console = mainWindow.getLeftPanel().getConsole();
	}
}
