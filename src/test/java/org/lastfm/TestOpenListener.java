package org.lastfm;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;

public class TestOpenListener {
	private static final String MY_ROOT_PATH = "MyRootPath";
	private HelperScrobbler helperScrobbler;
	private MainWindow mainWindow;
	private LoginWindow loginWindow;
	private ScrobblerController controller;

	@Before
	public void initialize() {
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		mainWindow = new MainWindow();

		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	@Test
	public void shouldGetFilesFromRoot() throws Exception {
		List<File> fileList = new ArrayList<File>();
		
		File root = Mockito.mock(File.class);
		FileUtils fileUtils = Mockito.mock(FileUtils.class);
		JFileChooser fileChooser = Mockito.mock(JFileChooser.class);
		
		Mockito.when(fileUtils.getFileList(root)).thenReturn(fileList);
		Mockito.when(root.getAbsolutePath()).thenReturn(MY_ROOT_PATH);
		Mockito.when(fileChooser.showOpenDialog(mainWindow.getPanel())).thenReturn(0);
		Mockito.when(fileChooser.getSelectedFile()).thenReturn(root);
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		
		mainWindow.openButton.doClick();
		Mockito.verify(helperScrobbler).getMetadataList(fileList);
	}
}
