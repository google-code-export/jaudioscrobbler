package org.lastfm;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.control.DefaultEngine;
import org.lastfm.controller.ScrobblerController;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;


public class TestLauncher {
	@Mock
	private DefaultEngine defaultEngine;
	@Mock
	private ConfigurableApplicationContext context;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldInitialize() throws Exception {
		when(context.getBean(DefaultEngine.class)).thenReturn(defaultEngine);
		
		new Launcher(context);
		
		verify(context).getBean(DefaultEngine.class);
		verify(defaultEngine).start();
		verify(context).getBean(ScrobblerController.class);
	}
}
