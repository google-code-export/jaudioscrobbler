package org.lastfm.gui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;


public class TestLoginWindow {
	private LoginWindow loginWindow;

	@After
	public void finalize(){
		loginWindow.getFrame().dispose();
	}
	
	@Test
	public void shouldValidateGUIElements() throws Exception {
		loginWindow = new LoginWindow();
		
		assertEquals("sendButton", loginWindow.sendButton.getName());
		assertEquals("userName", loginWindow.userName.getName());
		assertEquals("password", loginWindow.password.getName());
	}

}
