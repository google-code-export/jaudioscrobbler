package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
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
	private File root;
	private FileUtils fileUtils;
	private JFileChooser fileChooser;
	private List<File> fileList;

	@Before
	public void initialize() {
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		mainWindow = new MainWindow();

		root = Mockito.mock(File.class);
		fileUtils = Mockito.mock(FileUtils.class);
		fileChooser = Mockito.mock(JFileChooser.class);

		fileList = new ArrayList<File>();

		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}

	@Test
	public void shouldGetFilesFromRoot() throws Exception {
		setExpectations();

		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;

		mainWindow.openButton.doClick();
		Mockito.verify(fileUtils).getFileList(root);
	}

	@Test
	public void shouldNotAddAScrobblingIfIsNotAaudioFile() throws Exception {
		setExpectations();

		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;

		File file = new File("resources/log4j.properties");
		fileList.add(file);

		mainWindow.openButton.doClick();
		List<Metadata> metadataList = controller.getMetadataList();
		assertEquals(0, metadataList.size());
	}

	private void setExpectations() throws InterruptedException, IOException, CannotReadException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		Mockito.when(fileUtils.getFileList(root)).thenReturn(fileList);
		Mockito.when(root.getAbsolutePath()).thenReturn(MY_ROOT_PATH);
		Mockito.when(fileChooser.showOpenDialog(mainWindow.getPanel())).thenReturn(0);
		Mockito.when(fileChooser.getSelectedFile()).thenReturn(root);
	}
}
