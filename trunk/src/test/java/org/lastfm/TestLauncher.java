package org.lastfm;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLauncher {
	
	@Test
	public void shouldValidateObjects() throws Exception {
		Launcher launcher = new Launcher();
		assertNotNull(launcher.helperScrobbler);
		assertNotNull(launcher.loginWindow);
		assertNotNull(launcher.mainWindow);
	}

}
