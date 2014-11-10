package pl.grm.boll.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;

import pl.grm.boll.boxes.DialogBox;
import pl.grm.boll.lib.LauncherDB;
import pl.grm.boll.lib.Result;

public class ConnHandler {
	private LauncherDB	dbHandler;
	private Logger		logger;
	private JTextArea	console;
	
	public ConnHandler(Logger logger) {
		this.logger = logger;
		try {
			dbHandler = connect();
		}
		catch (AccessException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (RemoteException e) {
			DialogBox dBox = new DialogBox();
			dBox.setInfo("Check Your Network Connection");
			dBox.setModal(true);
			dBox.showBox();
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (NotBoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	/**
	 * Connects to server with dBControls.
	 * 
	 * @return {@link LauncherDB} interface server implementation
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws AccessException
	 */
	private static LauncherDB connect() throws RemoteException, NotBoundException, AccessException {
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		LauncherDB dbHandler = (LauncherDB) registry.lookup("dBConfBindHandler");
		return dbHandler;
	}
	
	private Boolean checkIfExists(String login) {
		Result result = null;
		String resultString;
		try {
			result = dbHandler.checkIfExists(login);
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			return false;
		}
		if (result.getException() == null) {
			if (result.getResultString() != null) {
				resultString = result.getResultString();
				if (resultString.equals(login)) { return true; }
			} else {
				String str = "Brak gracza o padanym loginie: " + login + "\n";
				logger.info(str);
				console.append(str);
			}
		} else {
			logger.log(Level.SEVERE, result.getException().toString(), result.getException());
		}
		return false;
	}
	
	/**
	 * @param login
	 * @param password
	 * @return true if correct login & password
	 */
	public Boolean login(String login, char[] password) {
		Result result = null;
		if (checkIfExists(login)) {
			try {
				result = dbHandler.checkPasswd(login, String.valueOf(password));
			}
			catch (RemoteException e) {
				logger.log(Level.SEVERE, e.toString(), e);
				return false;
			}
			boolean correct = result.isResultBoolean();
			if (correct) { return true; }
			String str = "B³edne has³o. \n";
			logger.info(str);
			console.append(str);
		}
		return false;
	}
	
	public void setConsole(JTextArea console) {
		this.console = console;
	}
}