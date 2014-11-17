package pl.grm.bol.launcher.net.rmi;

import java.awt.Component;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import pl.grm.bol.lib.BLog;
import pl.grm.bol.lib.LauncherDB;
import pl.grm.bol.lib.Result;

public class ConnHandler {
	private LauncherDB	dbHandler;
	private BLog		logger;
	private boolean		connected	= false;
	
	public ConnHandler(BLog logger) {
		this.logger = logger;
		boolean redo;
		do {
			redo = false;
			try {
				dbHandler = connect();
				redo = false;
				setConnected(true);
			}
			catch (AccessException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
			catch (RemoteException e) {
				Object[] opts = {"Reconnect", "Stay Ofline"};
				int confirmed = JOptionPane.showOptionDialog(null,
						"Connection failed.\nWant to try to connect again?",
						"Reconnect Message Box", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null, opts, opts[0]);
				if (confirmed == JOptionPane.YES_OPTION) {
					logger.info("Trying to connect ...");
					redo = true;
				}
				logger.log(Level.SEVERE, e.toString(), e);
			}
			catch (NotBoundException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
		}
		while (redo);
	}
	
	public String getSalt(String login) {
		try {
			return dbHandler.checkSalt(login).getResultString();
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return null;
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
		Registry registry = LocateRegistry.getRegistry("localhost", 2234);
		LauncherDB dbHandler = (LauncherDB) registry.lookup("dBConfBindHandler");
		return dbHandler;
	}
	
	/**
	 * Tries to reconnect to Server.
	 * 
	 * @param component
	 *            can be null.
	 * @return true if connected, otherwise false
	 */
	public boolean reconnect(Component component) {
		boolean redo;
		do {
			redo = false;
			try {
				dbHandler = connect();
				redo = false;
				setConnected(true);
				return true;
			}
			catch (AccessException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
			catch (RemoteException e) {
				int confirmed = JOptionPane.showConfirmDialog(component,
						"Connection lost.\nDo You want to try to reconnect?",
						"Reconnect Message Box", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					logger.info("Trying to reconnect ...");
					redo = true;
				}
				logger.log(Level.SEVERE, e.toString(), e);
			}
			catch (NotBoundException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
		}
		while (redo);
		return false;
	}
	
	private Boolean checkIfExists(String login) {
		Result result = null;
		String resultString;
		try {
			result = dbHandler.checkIfExists(login);
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			setConnected(false);
			return false;
		}
		if (result.getException() == null) {
			if (result.getResultString() != null) {
				resultString = result.getResultString();
				if (resultString.equals(login)) { return true; }
			} else {
				String str = "Bad login: " + login;
				logger.info(str);
			}
		} else {
			logger.log(Level.SEVERE, result.getException().toString(),
					result.getException());
		}
		return false;
	}
	
	/**
	 * @param login
	 * @param pass
	 * @return true if correct login & password
	 */
	public Boolean login(String login, String pass) {
		Result result = null;
		if (checkIfExists(login)) {
			try {
				result = dbHandler.checkPasswd(login, pass);
			}
			catch (RemoteException e) {
				logger.log(Level.SEVERE, e.toString(), e);
				setConnected(false);
				return false;
			}
			boolean correct = result.isResultBoolean();
			if (correct) { return true; }
			String str = "Bad password!\n";
			logger.info(str);
		}
		return false;
	}
	
	public int getPlayerPermissionLevel(String login) {
		int permLvl = 10;
		try {
			permLvl = dbHandler.getPlayerPermissionLevel(login).getResultInt();
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return permLvl;
	}
	
	public boolean isConnected() {
		return this.connected;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}