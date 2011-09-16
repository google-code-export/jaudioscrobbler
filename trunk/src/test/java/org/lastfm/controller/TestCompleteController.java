package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.helper.MusicBrainzDelegator;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataWriter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.ServerUnavailableException;


public class TestCompleteController {
	
	@InjectMocks
	private CompleteController controller = new CompleteController();
	
	@Mock
	private MusicBrainzDelegator service;
	@Mock
	private MetadataWriter metadataWriter;
	@Mock
	private Metadata metadata;
	@Mock
	private File file;

	private String artist = "Dave Deen";
	private String title = "Footprints (Original Mix)";
	private String album = "Footprints EP";
	private Integer trackNumber = 10;


	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(metadata.getArtist()).thenReturn(artist);
		when(metadata.getTitle()).thenReturn(title);
		when(metadata.getAlbum()).thenReturn(album);
		when(metadata.getTrackNumber()).thenReturn(trackNumber);
	}
	
	
	@Test
	public void shouldCompleteMetadata() throws Exception {
		when(service.getAlbum(artist, title)).thenReturn(album);
		ActionResult result = controller.completeMetadata(metadata);
		
		verify(service).getAlbum(artist, title);
		verify(metadata).setAlbum(album);
		assertEquals(ActionResult.METADATA_SUCCESS, result);
	}
	
	@Test
	public void shouldDetectAnErrorInService() throws Exception {
		when(service.getAlbum(artist, title)).thenThrow(new ServerUnavailableException());
		ActionResult result = controller.completeMetadata(metadata);
		
		assertEquals(ActionResult.METADATA_ERROR, result);
	}
	
	@Test
	public void shouldNotFoundAlbum() throws Exception {
		ActionResult result = controller.completeMetadata(metadata);
		
		verify(metadata, never()).setAlbum(album);
		assertEquals(ActionResult.METADATA_NOT_FOUND, result);
	}
	
	@Test
	public void shouldCompleteAlbumInMetadata() throws Exception {
		when(metadata.getFile()).thenReturn(file);
		
		controller.completeAlbum(metadata);
		
		verify(metadataWriter).setFile(file);
		verify(metadataWriter).writeArtist(artist);
		verify(metadataWriter).writeTrackName(title);
		verify(metadataWriter).writeAlbum(album);
		verify(metadataWriter).writeTrackNumber(trackNumber.toString());
	}
}
