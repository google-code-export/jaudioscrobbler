package org.lastfm.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.junit.Before;
import org.junit.Test;

//TODO: Inject Spring
public class TestMainWindow {
	
	private MainWindow mainWindow = new MainWindow();

	@Before
	public void setup(){
		this.mainWindow.getFrame().setEnabled(true);
	}

	@Test
	public void shouldNotEnableSendAndCompleteButton() throws Exception {
		assertFalse("sendButton should not be enabled", this.mainWindow.getSendButton().isEnabled());
		assertFalse("completeButton should not be enabled", this.mainWindow.getCompleteButton().isEnabled());
		assertFalse("descriptionTable should be disabled", this.mainWindow.getDescriptionTable().isEnabled());
	}
	
	@Test
	public void shouldVerifyKeyStrokeActionWasRegistered() throws Exception {
		assertEquals(2, mainWindow.inputMap.size());
		assertEquals(2, mainWindow.openButton.getActionMap().size());
		
		KeyStroke ctrlo = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		assertEquals(MainWindow.CTRL_O, mainWindow.inputMap.get(ctrlo));
		assertEquals(MainWindow.ENTER, mainWindow.inputMap.get(enter));
	}
	
	@Test
	public void shouldVerifyDirectoryTextFieldNotEditable() throws Exception {
		assertFalse(mainWindow.directorySelected.isEnabled());
	}
}
