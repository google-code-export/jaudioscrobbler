package org.lastfm;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class Launcher {
	
	private ConfigurableApplicationContext applicationContext;

	public Launcher() {
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
		
		this.applicationContext = getApplicationContext();
		HelperScrobbler helperScrobbler = applicationContext.getBean(HelperScrobbler.class);
		MainWindow mainWindow = applicationContext.getBean(MainWindow.class);
		LoginWindow loginWindow = applicationContext.getBean(LoginWindow.class);
		
		new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	private ConfigurableApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext( "/spring/applicationContext.xml" );
		}
		return applicationContext;
	}

	public static void main(String[] args) {
		new Launcher();
	}
}
