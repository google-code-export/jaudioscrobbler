package org.lastfm;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.control.DefaultEngine;
import org.lastfm.controller.ScrobblerController;
import org.lastfm.helper.ApplicationContextSingleton;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to launch ALL the process
 */

public class Launcher {
	private Log log = LogFactory.getLog(getClass());

	public Launcher(ConfigurableApplicationContext applicationContext) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException ulf) {
			log.error(ulf, ulf);
		} catch (ClassNotFoundException cne) {
			log.error(cne, cne);
		} catch (InstantiationException ine) {
			log.error(ine, ine);
		} catch (IllegalAccessException ile) {
			log.error(ile, ile);
		}

		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		defaultEngine.start();
		applicationContext.getBean(ScrobblerController.class);
	}
	
	public static void main(String[] args) {
		new Launcher(ApplicationContextSingleton.getApplicationContext());
	}
}
