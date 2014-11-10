package pl.grm.boll.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.grm.boll.boxes.DialogBox;
import pl.grm.boll.lib.LauncherDB;
import pl.grm.boll.lib.Result;

public class ConnHandler {
	private LauncherDB	dbHandler;
	private Logger		logger;
	
	public ConnHandler(Logger logger) {
		this.logger = logger;
		try {
			dbHandler = connect();
		}
		catch (AccessException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		catch (RemoteException e) {
			DialogBox dBox = new DialogBox();
			dBox.setInfo("Check Your Network Connection");
			dBox.setModal(true);
			dBox.showBox();
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
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
	private static LauncherDB connect() throws RemoteException, NotBoundException,
			AccessException {
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		LauncherDB dbHandler = (LauncherDB) registry.lookup("dBConfBindHandler");
		return dbHandler;
	}
	
	public Boolean register(String login, char[] password) {
		return checkIfExists(login);
	}
	
	private Boolean checkIfExists(String login) {
		Result result;
		String resultString;
		try {
			result = dbHandler.checkIfExists(login);
			if (result.getResultString() != null) {
				resultString = result.getResultString();
				if (resultString.equals(login)) { return true; }
			}
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean login(String login, char[] password) {
		Result result;
		if (checkIfExists(login)) {
			try {
				result = dbHandler.checkPasswd(login, String.valueOf(password));
				return result.isResultBoolean();
			}
			catch (RemoteException e) {
				logger.log(Level.SEVERE, e.toString(), e);
				e.printStackTrace();
			}
		}
		return false;
	}
}