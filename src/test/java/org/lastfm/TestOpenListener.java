package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.mockito.Mockito;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

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
	private ArrayList<Metadata> metadataList;

	@Before
	public void initialize() {
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		mainWindow = new MainWindow();

		metadataList = new ArrayList<Metadata>();
		
		root = Mockito.mock(File.class);
		fileUtils = Mockito.mock(FileUtils.class);
		fileChooser = Mockito.mock(JFileChooser.class);

		fileList = new ArrayList<File>();

		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
	}

	@Test
	public void shouldGetFilesFromRoot() throws Exception {
		setExpectations();

		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		
		fileList.add(new File("src/test/resources/audio/Jaytech - Pepe Garden (Original Mix).mp3"));

		Mockito.when(fileUtils.getFileList(root)).thenReturn(fileList);
		mainWindow.getOpenButton().doClick();
		assertEquals(1, controller.metadataList.size());
		int row = 0;
		int col = 0;
		assertEquals(2, controller.mainWindow.getDescriptionTable().getRowCount());
		assertTrue(((String)controller.mainWindow.getDescriptionTable().getValueAt(row, col++)).contains("Jaytech"));
		assertTrue(((String)controller.mainWindow.getDescriptionTable().getValueAt(row, col++)).contains("Pepe Garden"));
		assertTrue(((String)controller.mainWindow.getDescriptionTable().getValueAt(row, col++)).contains("Everything is OK"));
		assertEquals(4,controller.mainWindow.getDescriptionTable().getValueAt(row, col++));
		assertEquals(461,controller.mainWindow.getDescriptionTable().getValueAt(row, col++));
		assertTrue(((String)controller.mainWindow.getDescriptionTable().getValueAt(row, col++)).contains("Ready"));
		assertTrue("completeButton should be enable", controller.mainWindow.getCompleteButton().isEnabled());
		assertTrue("sendButton should be enable", controller.mainWindow.getSendButton().isEnabled());
	}

	@Test
	public void shouldNotAddAScrobblingIfIsNotAaudioFile() throws Exception {
		setExpectations();

		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;

		File file = new File("resources/log4j.properties");
		fileList.add(file);

		mainWindow.getOpenButton().doClick();
		List<Metadata> metadataList = controller.metadataList;
		assertEquals(0, metadataList.size());
	}
	
	@Test 
	public void shouldHandleIOExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable ioException = new IOException();
		Mockito.when(fileUtils.getFileList(root)).thenThrow(ioException);

		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;

		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}
	
	@Test 
	public void shouldHandleTagExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable tagException = new TagException();
		Mockito.when(fileUtils.getFileList(root)).thenThrow(tagException);

		metadataList = new ArrayList<Metadata>();
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;

		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}
	
	@Test 
	public void shouldHandleReadOnlyFileExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable readOnlyFileException = new ReadOnlyFileException();
		Mockito.when(fileUtils.getFileList(root)).thenThrow(readOnlyFileException);

		metadataList = new ArrayList<Metadata>();
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;

		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}
	
	@Test 
	public void shouldHandleInvalidAudioFrameExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable invalidAudioFileException = new InvalidAudioFrameException(null);
		Mockito.when(fileUtils.getFileList(root)).thenThrow(invalidAudioFileException);

		metadataList = new ArrayList<Metadata>();
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;

		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}
	
	@Test 
	public void shouldHandleInvalidId3VersionExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable invalidId3VersionException = new InvalidId3VersionException();
		Mockito.when(fileUtils.getFileList(root)).thenThrow(invalidId3VersionException);

		metadataList = new ArrayList<Metadata>();
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;

		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}

	@Test 
	public void shouldHandleInterruptedExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable interruptedException = new InterruptedException();
		Mockito.when(fileUtils.getFileList(root)).thenThrow(interruptedException);
		
		metadataList = new ArrayList<Metadata>();
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;
		
		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}

	@Test 
	public void shouldHandleCannotReadExceptionWhenGetFilesFromRoot() throws Exception {
		setExpectations();
		
		Throwable cannotReadException = new CannotReadException();
		Mockito.when(fileUtils.getFileList(root)).thenThrow(cannotReadException);
		
		metadataList = new ArrayList<Metadata>();
		
		controller.fileChooser = fileChooser;
		controller.fileUtils = fileUtils;
		controller.metadataList = metadataList;
		
		mainWindow.getOpenButton().doClick();
		assertEquals(0, metadataList.size());
		assertEquals(ApplicationState.OPEN_ERROR, mainWindow.getLabel().getText());
	}

	private void setExpectations() throws InterruptedException, IOException, CannotReadException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		Mockito.when(fileUtils.getFileList(root)).thenReturn(fileList);
		Mockito.when(root.getAbsolutePath()).thenReturn(MY_ROOT_PATH);
		Mockito.when(fileChooser.showOpenDialog(mainWindow.getPanel())).thenReturn(0);
		Mockito.when(fileChooser.getSelectedFile()).thenReturn(root);
	}
}
