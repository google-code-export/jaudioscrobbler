package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.controller.service.CoverArtService;
import org.lastfm.helper.MusicBrainzDelegator;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataException;
import org.lastfm.metadata.MetadataWriter;
import org.lastfm.model.MusicBrainzTrack;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.ServerUnavailableException;


public class TestCompleteController {
	private static final String ERROR = "Error";

	@InjectMocks
	private CompleteController controller = new CompleteController();
	
	@Mock
	private MusicBrainzDelegator musicBrainzDelegator;
	@Mock
	private MetadataWriter metadataWriter;
	@Mock
	private Metadata metadata;
	@Mock
	private File file;
	@Mock
	private CoverArtService coverArtService;

	private String artist = "Dave Deen";
	private String title = "Footprints (Original Mix)";
	private String album = "Footprints EP";
	private Integer trackNumber = 10;
	private Integer totalTracks = 25;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(metadata.getArtist()).thenReturn(artist);
		when(metadata.getTitle()).thenReturn(title);
		when(metadata.getAlbum()).thenReturn(album);
		when(metadata.getTrackNumber()).thenReturn("" + trackNumber);
		when(metadata.getTotalTracks()).thenReturn("" + totalTracks);
		when(coverArtService.completeCoverArt(metadata)).thenReturn(ActionResult.METADATA_COMPLETE);
	}
	
	
	@Test
	public void shouldCompleteMetadata() throws Exception {
		MusicBrainzTrack musicBrainzTrack = setExpectations();
		when(musicBrainzDelegator.getAlbum(artist, title)).thenReturn(musicBrainzTrack);
		when(metadata.getAlbum()).thenReturn("");
		ActionResult result = controller.completeAlbumMetadata(metadata);
		
		verify(musicBrainzDelegator).getAlbum(artist, title);
		verify(metadata).setAlbum(album);
		verify(metadata).setTrackNumber("" + trackNumber);
		verify(metadata).setTotalTracks("" + totalTracks);
		assertEquals(ActionResult.METADATA_SUCCESS, result);
	}


	private MusicBrainzTrack setExpectations() {
		MusicBrainzTrack musicBrainzTrack = new MusicBrainzTrack();
		musicBrainzTrack.setAlbum(album);
		musicBrainzTrack.setTrackNumber(trackNumber);
		musicBrainzTrack.setTotalTrackNumber(totalTracks);
		return musicBrainzTrack;
	}
	
	@Test
	public void shouldDetectAnErrorInService() throws Exception {
		when(metadata.getAlbum()).thenReturn("");
		when(musicBrainzDelegator.getAlbum(artist, title)).thenThrow(new ServerUnavailableException());
		
		ActionResult result = controller.completeAlbumMetadata(metadata);
		
		assertEquals(ActionResult.METADATA_ERROR, result);
	}
	
	@Test
	public void shouldNotFoundAlbum() throws Exception {
		when(metadata.getAlbum()).thenReturn("");
		MusicBrainzTrack musicBrainzTrack = new MusicBrainzTrack();
		when(musicBrainzDelegator.getAlbum(artist, title)).thenReturn(musicBrainzTrack);
		
		ActionResult result = controller.completeAlbumMetadata(metadata);

		verify(metadata, never()).setAlbum(album);
		assertEquals(ActionResult.METADATA_NOT_FOUND, result);
	}
	
	@Test
	public void shouldCompleteAlbumInMetadata() throws Exception {
		when(metadata.getFile()).thenReturn(file);
		
		ActionResult result = controller.completeAlbum(metadata);
		
		verify(metadataWriter).setFile(file);
		verify(metadataWriter).writeAlbum(album);
		verify(metadataWriter).writeTrackNumber(trackNumber.toString());
		verify(metadataWriter).writeTotalTracksNumber(totalTracks.toString());
		assertEquals(ActionResult.UPDATED, result);
	}
	
	@Test
	public void shouldNotUpdateMetadata() throws Exception {
		when(metadata.getFile()).thenReturn(file);
		when(metadataWriter.writeAlbum(album)).thenThrow(new MetadataException(ERROR));
		
		ActionResult result = controller.completeAlbum(metadata);
		
		assertEquals(ActionResult.WRITE_METADATA_ERROR, result);
	}
	
	@Test
	public void shouldReturnMetadataCompleteIfHasAlbum() throws Exception {
		when(metadata.getAlbum()).thenReturn(album);
		ActionResult result = controller.completeAlbumMetadata(metadata);
		
		assertEquals(ActionResult.METADATA_COMPLETE, result);
	}
	
	@Test
	public void shouldCompleteCoverArtMetadata() throws Exception {
		controller.completeCoverArtMetadata(metadata);
		
		verify(coverArtService).completeCoverArt(metadata);
	}
}
