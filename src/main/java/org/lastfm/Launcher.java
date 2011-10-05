package org.lastfm;

import org.lastfm.action.control.DefaultEngine;
import org.lastfm.controller.ScrobblerController;
import org.lastfm.helper.ApplicationContextSingleton;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to launch ALL the process
 */

public class Launcher {
	public Launcher(ConfigurableApplicationContext applicationContext) {
		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		defaultEngine.start();
		applicationContext.getBean(ScrobblerController.class);
	}
	
	public static void main(String[] args) {
		new Launcher(ApplicationContextSingleton.getApplicationContext());
	}
}
