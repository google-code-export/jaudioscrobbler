package org.lastfm.gui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;


public class TestMainWindow {
	private MainWindow mainWindow;
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
	}

	@Test
	public void shouldNotEnableSendAndCompleteButton() throws Exception {
		mainWindow = new MainWindow();
		assertFalse("sendButton should not be enable", mainWindow.getSendButton().isEnabled());
		assertFalse("completeButton should not be enable", mainWindow.getCompleteButton().isEnabled());
	}
}
