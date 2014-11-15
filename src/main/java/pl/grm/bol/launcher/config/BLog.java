package pl.grm.bol.launcher.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;

import pl.grm.bol.lib.FileOperation;

public class BLog {
	private Logger logger;
	private JTextArea console;

	public BLog() {
		Logger loggerR = null;
		try {
			loggerR = FileOperation.setupLogger(ConfigHandler.class);
		} catch (ClassNotFoundException | IllegalArgumentException
				| IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		this.logger = loggerR;
	}

	public synchronized void log(Level level, String msg, Throwable thrown) {
		logger.log(level, msg, thrown);
		if (console != null) {
			console.append(msg + "\n");
		}
	}

	public synchronized void info(String msg) {
		logger.info(msg);
		if (console != null) {
			console.append(msg + "\n");
		}
	}

	public JTextArea getConsole() {
		return console;
	}

	public void setConsole(JTextArea console) {
		this.console = console;
	}
}
