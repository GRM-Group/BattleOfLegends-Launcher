package pl.grm.boll;

import javax.swing.JFrame;

import pl.grm.boll.config.ConfigHandler;

public class Presenter {
	private JFrame mainWindow;
	private ConfigHandler configHandler;

	public Presenter() {
		configHandler = new ConfigHandler();
	}

	public void addWindow(JFrame mainWindow) {
		this.mainWindow = mainWindow;
	}
}
