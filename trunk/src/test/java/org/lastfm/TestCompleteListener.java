package org.lastfm;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;

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
	public void shouldSearchByArtistAndTrackName() throws Exception {
		MusicBrainzService service = mock(MusicBrainzService.class);
		JTable table = Mockito.mock(JTable.class);
		TableModel model = Mockito.mock(TableModel.class);
		
		String expectedArtist = "Paul Van Dyk";
		
		when(service.getArtist(Mockito.anyString(), Mockito.anyString())).thenReturn(expectedArtist);
		
		controller.service = service;

		when(table.getModel()).thenReturn(model);
		when(model.getValueAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn("");
		when(table.getRowCount()).thenReturn(1);

		mainWindow.table = table;
		
		mainWindow.completeMetadataButton.doClick();
		
		assertEquals(expectedArtist,service.getArtist(Mockito.anyString(), Mockito.anyString()));
		
	}
}
