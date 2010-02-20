package org.lastfm;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestCompleteListener {
	private HelperScrobbler helperScrobbler;
	private MainWindow mainWindow;
	private LoginWindow loginWindow;
	private ScrobblerController controller;

	@Before
	public void initialize(){
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		mainWindow = new MainWindow();
		
		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	@Test
	public void shouldUpdateAlbumOnSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = Mockito.mock(JTable.class);
		TableModel model = Mockito.mock(TableModel.class);
		
		String expectedAlbum = "Global";
		String expectedStatus = ApplicationState.NEW_METADATA;
		
		when(service.getAlbum(Mockito.anyString(), Mockito.anyString())).thenReturn(expectedAlbum);
		
		controller.service = service;

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;
		
		assertEquals(0, mainWindow.getProgressBar().getValue());
		assertFalse("progressBar should not be visible", mainWindow.getProgressBar().isVisible());
		
		mainWindow.getCompleteButton().setEnabled(true);
		mainWindow.getCompleteButton().doClick();
		
		assertFalse("sendButton should not be enable", mainWindow.getSendButton().isEnabled());
		assertFalse("openButton should not be enable", mainWindow.getOpenButton().isEnabled());
		
		Thread.sleep(1000);
		
		assertTrue("progressBar should be visible", mainWindow.getProgressBar().isVisible());
		assertEquals(expectedAlbum,service.getAlbum(Mockito.anyString(), Mockito.anyString()));
		verify(mainWindow.getDescritionTable().getModel()).setValueAt(expectedAlbum, 0, ApplicationState.ALBUM_COLUMN);
		verify(mainWindow.getDescritionTable().getModel()).setValueAt(expectedStatus, 0, ApplicationState.STATUS_COLUMN);
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertTrue("sendButton should be enable", mainWindow.getSendButton().isEnabled());
		assertTrue("openButton should be enable", mainWindow.getOpenButton().isEnabled());
		assertEquals(ApplicationState.APPLY, mainWindow.getCompleteButton().getText());
	}
	
	@Test
	public void shouldNotUpdateAlbumOnSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = Mockito.mock(JTable.class);
		TableModel model = Mockito.mock(TableModel.class);
		
		String expectedAlbum = "";
		String expectedStatus = ApplicationState.NEW_METADATA;
		
		when(service.getAlbum(Mockito.anyString(), Mockito.anyString())).thenReturn(expectedAlbum);
		
		controller.service = service;

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;
		mainWindow.getCompleteButton().doClick();
		
		assertEquals(expectedAlbum,service.getAlbum(Mockito.anyString(), Mockito.anyString()));
		verify(mainWindow.getDescritionTable().getModel(), Mockito.never()).setValueAt(expectedAlbum, 0, ApplicationState.ALBUM_COLUMN);
		verify(mainWindow.getDescritionTable().getModel(), Mockito.never()).setValueAt(expectedStatus, 0, ApplicationState.STATUS_COLUMN);
	}
	
	@Test
	public void shouldCatchServerUnavailableExceptionWheSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = Mockito.mock(JTable.class);
		TableModel model = Mockito.mock(TableModel.class);
		
		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;
		
		Exception serverUnavailableException = new ServerUnavailableException();
		
		when(service.getAlbum(Mockito.anyString(), Mockito.anyString())).thenThrow(serverUnavailableException );
		
		controller.service = service;
		
		mainWindow.getCompleteButton().doClick();
		verify(mainWindow.getDescritionTable().getModel(), Mockito.never()).setValueAt(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
	}
}
