package org.lastfm;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class Launcher {
	
	private ConfigurableApplicationContext applicationContext;
	HelperScrobbler helperScrobbler;
	MainWindow mainWindow;
	LoginWindow loginWindow;
	private Log log = LogFactory.getLog(Launcher.class);

	public Launcher() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException lfe) {
			log.error(lfe,lfe);
		} catch (ClassNotFoundException cne) {
			log.error(cne,cne);
		} catch (InstantiationException ine) {
			log.error(ine,ine);
		} catch (IllegalAccessException ile) {
			log.error(ile,ile);
		}
		
		this.applicationContext = getApplicationContext();
		helperScrobbler = applicationContext.getBean(HelperScrobbler.class);
		mainWindow = applicationContext.getBean(MainWindow.class);
		loginWindow = applicationContext.getBean(LoginWindow.class);
		
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
