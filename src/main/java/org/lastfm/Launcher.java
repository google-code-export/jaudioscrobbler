package org.lastfm;

import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;

public class Launcher {
	public static void main(String[] args) {
		new ApplicationState();
		new ScrobblerController(new HelperScrobbler(), new MainWindow(), new LoginWindow());
	}
}
