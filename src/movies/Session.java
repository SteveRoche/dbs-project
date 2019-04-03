package movies;

import java.util.ArrayList;
import java.util.List;

public class Session {
	private static Session instance;
	private static String username;
	private static boolean loggedIn;
	private static List<SessionObserver> observers = new ArrayList<SessionObserver>();

	private Session(String username) {
		Session.username = username;
		loggedIn = true;
	}

	public static void login(String username) {
		if (instance == null) {
			instance = new Session(username);
		}

		if (!loggedIn) {
			Session.username = username;
			Session.loggedIn = true;
		}
		notifyAllObservers();
	}

	public static void logout() {
		if (loggedIn) {
			Session.username = null;
			Session.loggedIn = false;
		}
		notifyAllObservers();
	}

	public static String getUser() {
		return username;
	}

	public static boolean getLoggedIn() {
		return loggedIn;
	}

	public static void attach(SessionObserver observer) {
		observers.add(observer);
	}

	public static void notifyAllObservers() {
		for (SessionObserver observer : observers) {
			observer.update();
		}
	}
}
