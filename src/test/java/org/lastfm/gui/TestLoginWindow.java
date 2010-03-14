package org.lastfm.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestLoginWindow {
	
	@Autowired
	private LoginWindow loginWindow;

	@Test
	public void shouldValidateGUIElements() throws Exception {
		assertEquals("sendButton", loginWindow.sendButton.getName());
		assertEquals("userName", loginWindow.userName.getName());
		assertEquals("password", loginWindow.password.getName());
	}
}
