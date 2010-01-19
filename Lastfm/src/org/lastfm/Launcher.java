package org.lastfm;

import org.lastfm.gui.Login;
import org.lastfm.gui.MainWindow;

public class Launcher {
	public static void main(String[] args) {
		new ApplicationState();
		new MainWindow();
		new Login();
	}
}
