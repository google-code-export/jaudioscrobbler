package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.MusicBrainzService;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.ServerUnavailableException;


public class TestCompleteController {
	
	@InjectMocks
	private CompleteController controller = new CompleteController();
	
	@Mock
	private MusicBrainzService service;
	@Mock
	private Metadata metadata;

	private String artist = "Dave Deen";
	private String title = "Footprints (Original Mix)";
	private String album = "Footprints EP";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(metadata.getArtist()).thenReturn(artist);
		when(metadata.getTitle()).thenReturn(title);
	}
	
	
	@Test
	public void shouldCompleteMetadata() throws Exception {
		when(service.getAlbum(artist, title)).thenReturn(album);
		int result = controller.completeMetadata(metadata);
		
		verify(service).getAlbum(artist, title);
		verify(metadata).setAlbum(album);
		assertEquals(ApplicationState.OK, result);
	}
	
	@Test
	public void shouldDetectAnErrorInService() throws Exception {
		when(service.getAlbum(artist, title)).thenThrow(new ServerUnavailableException());
		int result = controller.completeMetadata(metadata);
		
		assertEquals(ApplicationState.ERROR, result);
	}
	
	@Test
	public void shouldNotFoundAlbum() throws Exception {
		int result = controller.completeMetadata(metadata);
		
		verify(metadata, never()).setAlbum(album);
		assertEquals(ApplicationState.OK, result);
	}
}
