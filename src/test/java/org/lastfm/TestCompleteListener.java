package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 * 
 */

public class TestCompleteListener {
	private ScrobblerController controller;
	private List<Metadata> metadataList;
    
	private MainWindow mainWindow;
	
	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	private LoginWindow loginWindow;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mainWindow = new MainWindow();

		metadataList = new ArrayList<Metadata>();

		controller = new ScrobblerController(this.helperScrobbler, mainWindow, this.loginWindow);
		mainWindow.getCompleteButton().setEnabled(true);
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
	}
	
	@Test
	public void shouldUpdateAlbumOnSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);

		String expectedAlbum = "Global";
		String expectedStatus = ApplicationState.NEW_METADATA;

		when(service.getAlbum(anyString(), anyString())).thenReturn(expectedAlbum);

		controller.service = service;

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(anyInt(), anyInt())).thenReturn("");
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
		assertEquals(expectedAlbum, service.getAlbum(anyString(), anyString()));
		verify(mainWindow.getDescriptionTable().getModel()).setValueAt(expectedAlbum, 0, ApplicationState.ALBUM_COLUMN);
		verify(mainWindow.getDescriptionTable().getModel())
				.setValueAt(expectedStatus, 0, ApplicationState.STATUS_COLUMN);
		verify(mainWindow.getDescriptionTable().getModel()).setValueAt(0, 0, ApplicationState.TRACK_NUMBER_COLUMN);
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertFalse("sendButton should not be enabled", mainWindow.getSendButton().isEnabled());
		assertTrue("openButton should be enabled", mainWindow.getOpenButton().isEnabled());
		assertEquals(MainWindow.APPLY, mainWindow.getCompleteButton().getText());
	}

	private void createMetadataList() {
		Metadata metadata = mock(Metadata.class);
		metadataList.add(metadata);
	}

	@Test
	public void shouldNotUpdateAlbumOnSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);

		String expectedAlbum = "";
		String expectedStatus = ApplicationState.NEW_METADATA;

		when(service.getAlbum(anyString(), anyString())).thenReturn(expectedAlbum);

		controller.service = service;
		createMetadataList();
		controller.metadataList = metadataList;

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(anyInt(), anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;
		
		mainWindow.getCompleteButton().doClick();

		assertNotNull("metadataList should not be null", controller.metadataList);
		verify(service, never()).getTrackNumber(anyString());
		verify(mainWindow.getDescriptionTable().getModel(), never()).setValueAt(expectedAlbum, 0,
				ApplicationState.ALBUM_COLUMN);
		verify(mainWindow.getDescriptionTable().getModel(), never()).setValueAt(expectedStatus, 0,
				ApplicationState.STATUS_COLUMN);
	}

	@Test
	public void shouldCatchServerUnavailableExceptionWheSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(anyInt(), anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;

		Exception serverUnavailableException = new ServerUnavailableException();

		when(service.getAlbum(anyString(), anyString())).thenThrow(serverUnavailableException);

		controller.service = service;

		mainWindow.getCompleteButton().doClick();
		verify(mainWindow.getDescriptionTable().getModel(), never()).setValueAt(anyString(),
				anyInt(), anyInt());
	}

	@Test
	public void shouldWriteAlbumAndTrackNumerInFile() throws Exception {
		mainWindow.getCompleteButton().setText(MainWindow.APPLY);
		List<MetadataBean> metadataBeanList = new ArrayList<MetadataBean>();
		
		

		MetadataWriter writer = mock(MetadataWriter.class);

		MetadataBean bean = mock(MetadataBean.class);
		metadataBeanList.add(bean);

		controller.metadataWriter = writer;
		controller.metadataBeanList = metadataBeanList;
		
		mainWindow.getCompleteButton().doClick();
		Thread.sleep(2000);
		
		verify(writer).setFile(bean.getFile());
		verify(writer).writeArtist(bean.getArtist());
		verify(writer).writeTrackName(bean.getTrackName());
		verify(writer).writeAlbum(bean.getAlbum());
		verify(bean).getTrackNumber();
		verify(writer).writeTrackNumber(anyString());
		assertEquals(ApplicationState.METADATA_UPDATED, mainWindow.getDescriptionTable().getModel().getValueAt(bean.getRow(),
				ApplicationState.STATUS_COLUMN));

	}
}
