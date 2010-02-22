package org.lastfm;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class Launcher {
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		new ScrobblerController(new HelperScrobbler(), new MainWindow(), new LoginWindow());
	}
}
