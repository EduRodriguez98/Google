package server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Google extends UnicastRemoteObject implements IGoogle {
	
	private static final long serialVersionUID = 1L;
	public static HashMap<String, String> users = new HashMap<String, String>();
	
	protected Google() throws RemoteException {
		users.put("mireya.quintana@opendeusto.es", "1111");
	}

	public boolean validateUser(String username, String password) throws RemoteException {
		boolean validated;
		
		if(users.containsKey(username) && users.containsValue(password)) {
			// correct login details
			validated = true;
		} else {
			// non-existent user details or wrong user login details
			validated = false;
		}
		return validated;
	}
	
	public boolean registerUser(String login, String password) throws RemoteException {
		boolean registered = false;
		
		if(registered) {
			users.put(login, password);
		} else {
			System.err.println("Error trying to register the user.");
		}
		return registered;
	}
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("usage: java [policy] [codebase] server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {		
			IGoogle googleServer = new Google();
			Naming.rebind(name, googleServer);
			System.out.println("* Server '" + name + "' active and waiting...");
		} catch (Exception e) {
			System.err.println("- Exception running the server: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
