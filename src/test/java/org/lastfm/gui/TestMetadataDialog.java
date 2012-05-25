package org.lastfm.gui;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.event.Events;
import org.lastfm.helper.MetadataHelper;
import org.lastfm.model.MetadataAlbumValues;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMetadataDialog {
	private static final int HEIGHT = 450;
	private static final int WIDTH = 400;
	private static final String ARTIST = "Armin Van Buuren";
	private static final String ALBUM = "Mirage";
	private static final String GENRE = "Trance";
	private static final String YEAR = "2010";
	private static final String TRACKS = "16";
	private static final String CD = "1";
	private static final String CDS = "1";
	
	private static final String ARTIST_INPUT = "artistInput";
	private static final String ALBUM_INPUT = "albumInput";
	private static final String GENRE_INPUT = "genreInput";
	private static final String YEAR_INPUT = "yearImput";
	private static final String TRACKS_INPUT = "tracksInput";
	private static final String CD_INPUT = "cdInput";
	private static final String CDS_INPUT = "cdsInput";

	private static final String APPLY_BUTTON_NAME = "applyButton";
	
	@Mock
	private ControlEngineConfigurator controlEngineConfigurator;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private MetadataHelper metadataHelper;
	@Mock
	private MetadataAlbumValues metadataAlbumValues;
	
	private String message = "message";
	private FrameFixture window;
	private MetadataDialog metadataDialog;
	private JFrame frame = new JFrame();
	private MainWindow mainWindow = new MainWindow();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(controlEngineConfigurator.getControlEngine()).thenReturn(controlEngine);
	}
	
	@Test
	public void shouldSetAlbumValues() throws Exception {
		when(metadataHelper.createMetadataAlbumVaues()).thenReturn(metadataAlbumValues);
		
		metadataDialog = new MetadataDialog(mainWindow, controlEngineConfigurator, message);
		metadataDialog.setMetadataHelper(metadataHelper);
		frame.add(metadataDialog.getContentPane());
		
		window = new FrameFixture(frame);
		window.show();
		window.resizeTo(new Dimension(WIDTH,HEIGHT));
		window.textBox(ARTIST_INPUT).enterText(ARTIST);
		window.textBox(ALBUM_INPUT).enterText(ALBUM);
		window.textBox(GENRE_INPUT).enterText(GENRE);
		window.textBox(YEAR_INPUT).enterText(YEAR);
		window.textBox(TRACKS_INPUT).enterText(TRACKS);
		window.textBox(CD_INPUT).enterText(CD);
		window.textBox(CDS_INPUT).enterText(CDS);
		
		window.button(APPLY_BUTTON_NAME).click();
		
		verify(metadataAlbumValues).setArtist(ARTIST);
		verify(metadataAlbumValues).setAlbum(ALBUM);
		verify(metadataAlbumValues).setGenre(GENRE);
		verify(metadataAlbumValues).setYear(YEAR);
		verify(metadataAlbumValues).setTracks(TRACKS);
		verify(metadataAlbumValues).setCd(CD);
		verify(metadataAlbumValues).setCds(CDS);
		verify(controlEngine).fireEvent(Events.READY_TO_APPLY, new ValueEvent<MetadataAlbumValues>(metadataAlbumValues));
	}

}
