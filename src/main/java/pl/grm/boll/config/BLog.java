package pl.grm.boll.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;

public class BLog {
	private Logger		logger;
	private JTextArea	console;
	
	public BLog(Logger logger) {
		this.logger = logger;
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
