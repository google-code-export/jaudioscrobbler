package org.lastfm.helper;

import static org.junit.Assert.assertNotNull;

import org.asmatron.messengine.engines.DefaultEngine;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextSingleton {

	@Test
	public void shouldCreateAnApplicationContext() throws Exception {
		ConfigurableApplicationContext applicationContext = ApplicationContextSingleton.getApplicationContext();
		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		MainWindow mainWindow = applicationContext.getBean(MainWindow.class);
		LoginWindow loginWindow = applicationContext.getBean(LoginWindow.class);
		assertNotNull(defaultEngine);
		assertNotNull(mainWindow);
		assertNotNull(loginWindow);
	}

}
