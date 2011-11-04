package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.controller.service.CoverArtService;
import org.lastfm.helper.CompleteHelper;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestCoverArtService {
	@InjectMocks
	private CoverArtService coverArtService = new CoverArtService();
	
	@Mock
	private Metadata metadata;
	@Mock
	private ImageIcon imageIcon;
	@Mock
	private CompleteHelper completeHelper;
	@Mock
	private LastfmAlbum lastfmAlbum;

	private String year = "1990";
	private String genre = "French house";

	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetCoverArtFromLastfm() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getCoverArt(metadata)).thenReturn(lastfmAlbum);
		when(lastfmAlbum.getImageIcon()).thenReturn(imageIcon);
		
		ActionResult result = coverArtService.completeCoverArt(metadata);
		
		verify(metadata).setLastfmCoverArt(imageIcon);
		assertEquals(ActionResult.COVER_ART_SUCCESS, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmCoverArtMetadataDueToError() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getCoverArt(metadata)).thenReturn(lastfmAlbum);
		
		ActionResult result = coverArtService.completeYearLastfmMetadata(metadata);
		assertEquals(ActionResult.COVER_ART_ERROR, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmCoverArtMetadataDueToMetadataComplete() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(false);
		
		ActionResult result = coverArtService.completeCoverArt(metadata);
		assertEquals(ActionResult.METADATA_COMPLETE, result);
	}

	@Test
	public void shouldCompleteYearLastfmMetadata() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getYear(metadata)).thenReturn(lastfmAlbum);
		when(lastfmAlbum.getYear()).thenReturn(year);
		
		ActionResult result = coverArtService.completeYearLastfmMetadata(metadata);
		
		verify(metadata).setYear(year);
		assertEquals(ActionResult.YEAR_SUCCESS, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmYearMetadataDueToError() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getYear(metadata)).thenReturn(lastfmAlbum);
		
		ActionResult result = coverArtService.completeYearLastfmMetadata(metadata);
		assertEquals(ActionResult.YEAR_ERROR, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmYearMetadataDueToMetadataComplete() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(false);
		
		ActionResult result = coverArtService.completeYearLastfmMetadata(metadata);
		assertEquals(ActionResult.METADATA_COMPLETE, result);
	}
	
	@Test
	public void shouldCompleteGenreLastfmMetadata() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getGenre(metadata)).thenReturn(lastfmAlbum);
		when(lastfmAlbum.getGenre()).thenReturn(genre);
		
		ActionResult result = coverArtService.completeGenreLastfmMetadata(metadata);
		
		verify(metadata).setGenre(genre);
		assertEquals(ActionResult.GENRE_SUCCESS, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmGenreMetadataDueToError() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getGenre(metadata)).thenReturn(lastfmAlbum);
		
		ActionResult result = coverArtService.completeGenreLastfmMetadata(metadata);
		assertEquals(ActionResult.GENRE_ERROR, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmGenreMetadataDueToMetadataComplete() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(false);
		
		ActionResult result = coverArtService.completeGenreLastfmMetadata(metadata);
		assertEquals(ActionResult.METADATA_COMPLETE, result);
	}
}
