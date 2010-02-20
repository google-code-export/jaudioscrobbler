package org.lastfm.gui;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestMainWindow {
	@Test
	public void shouldNotEnableSendAndCompleteButton() throws Exception {
		MainWindow mainWindow = new MainWindow();
		assertFalse("sendButton should not be enable", mainWindow.getSendButton().isEnabled());
		assertFalse("completeButton should not be enable", mainWindow.getCompleteButton().isEnabled());
	}
}
