package org.lastfm.gui;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestLoginWindow {
	@Test
	public void shouldValidateGUIElements() throws Exception {
		LoginWindow loginWindow = new LoginWindow();
		
		assertEquals("sendButton", loginWindow.sendButton.getName());
		assertEquals("userName", loginWindow.userName.getName());
		assertEquals("password", loginWindow.password.getName());
	}

}
