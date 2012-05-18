package org.lastfm.helper;

import static org.junit.Assert.assertNotNull;

import org.asmatron.messengine.engines.DefaultEngine;
import org.junit.Test;
import org.lastfm.controller.ScrobblerController;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextSingleton {

	@Test
	public void shouldCreateAnApplicationContext() throws Exception {
		ConfigurableApplicationContext applicationContext = ApplicationContextSingleton.getApplicationContext();
		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		ScrobblerController scrobblerController = applicationContext.getBean(ScrobblerController.class);
		assertNotNull(defaultEngine);
		assertNotNull(scrobblerController);
	}

}
