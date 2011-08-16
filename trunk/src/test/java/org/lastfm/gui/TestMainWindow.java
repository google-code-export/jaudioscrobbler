package org.lastfm.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestMainWindow {
	
	@Autowired
	private MainWindow mainWindow;


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
