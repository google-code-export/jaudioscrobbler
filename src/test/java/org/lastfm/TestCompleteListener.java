package org.lastfm;

import static org.junit.Assert.assertNotNull;
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

		controller = new ScrobblerController();
		mainWindow.getCompleteMetadataButton().setEnabled(true);
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
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

		mainWindow.descriptionTable = table;
		
		mainWindow.getCompleteMetadataButton().doClick();

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

		mainWindow.descriptionTable = table;

		Exception serverUnavailableException = new ServerUnavailableException();

		when(service.getAlbum(anyString(), anyString())).thenThrow(serverUnavailableException);

		controller.service = service;

		mainWindow.getCompleteMetadataButton().doClick();
		verify(mainWindow.getDescriptionTable().getModel(), never()).setValueAt(anyString(),
				anyInt(), anyInt());
	}
}
