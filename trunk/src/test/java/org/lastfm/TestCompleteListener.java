package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataBean;
import org.lastfm.metadata.MetadataWriter;
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
	private List<Metadata> metadataList;

	@Before
	public void initialize() {
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		mainWindow = new MainWindow();

		metadataList = new ArrayList<Metadata>();

		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		mainWindow.getCompleteButton().setEnabled(true);
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
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

		createMetadataList();
		controller.metadataList = metadataList;

		assertNotNull("metadataList should not be null", controller.metadataList);
		assertEquals(0, mainWindow.getProgressBar().getValue());
		assertFalse("progressBar should not be visible", mainWindow.getProgressBar().isVisible());
		assertEquals(MainWindow.COMPLETE_BUTTON, mainWindow.getCompleteButton().getText());

		mainWindow.getCompleteButton().setEnabled(true);
		mainWindow.getCompleteButton().doClick();

		assertFalse("openButton should not be enable", mainWindow.getOpenButton().isEnabled());

		Thread.sleep(200);

		assertTrue("progressBar should be visible", mainWindow.getProgressBar().isVisible());
		assertEquals(expectedAlbum, service.getAlbum(Mockito.anyString(), Mockito.anyString()));
		verify(mainWindow.getDescritionTable().getModel()).setValueAt(expectedAlbum, 0, ApplicationState.ALBUM_COLUMN);
		verify(mainWindow.getDescritionTable().getModel())
				.setValueAt(expectedStatus, 0, ApplicationState.STATUS_COLUMN);
		verify(mainWindow.getDescritionTable().getModel()).setValueAt("0", 0, ApplicationState.TRACK_NUMBER_COLUMN);
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertTrue("sendButton should be enable", mainWindow.getSendButton().isEnabled());
		assertTrue("openButton should be enable", mainWindow.getOpenButton().isEnabled());
		assertEquals(MainWindow.APPLY, mainWindow.getCompleteButton().getText());
	}

	private void createMetadataList() {
		Metadata metadata = Mockito.mock(Metadata.class);
		metadataList.add(metadata);
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
		createMetadataList();
		controller.metadataList = metadataList;

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;
		
		mainWindow.getCompleteButton().doClick();

		assertNotNull("metadataList should not be null", controller.metadataList);
		verify(service, Mockito.never()).getTrackNumber(Mockito.anyString());
		verify(mainWindow.getDescritionTable().getModel(), Mockito.never()).setValueAt(expectedAlbum, 0,
				ApplicationState.ALBUM_COLUMN);
		verify(mainWindow.getDescritionTable().getModel(), Mockito.never()).setValueAt(expectedStatus, 0,
				ApplicationState.STATUS_COLUMN);
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

		when(service.getAlbum(Mockito.anyString(), Mockito.anyString())).thenThrow(serverUnavailableException);

		controller.service = service;

		mainWindow.getCompleteButton().doClick();
		verify(mainWindow.getDescritionTable().getModel(), Mockito.never()).setValueAt(Mockito.anyString(),
				Mockito.anyInt(), Mockito.anyInt());
	}

	@Test
	public void shouldWriteAlbumAndTrackNumerInFile() throws Exception {
		mainWindow.getCompleteButton().setText(MainWindow.APPLY);
		List<MetadataBean> metadataBeanList = new ArrayList<MetadataBean>();

		MetadataWriter writer = Mockito.mock(MetadataWriter.class);

		MetadataBean bean = Mockito.mock(MetadataBean.class);
		Mockito.when(bean.getRow()).thenReturn(0);
		metadataBeanList.add(bean);

		controller.metadataWriter = writer;
		controller.metadataBeanList = metadataBeanList;
		
		mainWindow.getCompleteButton().doClick();
		Thread.sleep(1000);
		
		assertEquals(ApplicationState.METADATA_UPDATED, mainWindow.getDescritionTable().getModel().getValueAt(bean.getRow(),
				ApplicationState.STATUS_COLUMN));

	}
}
