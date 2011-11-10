package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.controller.service.LastfmService;
import org.lastfm.helper.CompleteHelper;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestLastfmService {
	@InjectMocks
	private LastfmService coverArtService = new LastfmService();
	
	@Mock
	private Metadata metadata;
	@Mock
	private ImageIcon imageIcon;
	@Mock
	private CompleteHelper completeHelper;
	@Mock
	private LastfmAlbum lastfmAlbum;

	private String genre = "Minimal Techno";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCompleteMetadataFromLastfm() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getLastFM(metadata)).thenReturn(lastfmAlbum);
		when(lastfmAlbum.getImageIcon()).thenReturn(imageIcon);
		when(completeHelper.isSomethingNew(lastfmAlbum, metadata)).thenReturn(true);
		
		ActionResult result = coverArtService.completeLastFM(metadata);
		
		verify(completeHelper).isSomethingNew(lastfmAlbum, metadata);
		assertEquals(ActionResult.New, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmCoverArtMetadataDueToMetadataComplete() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(false);
		
		ActionResult result = coverArtService.completeLastFM(metadata);
		assertEquals(ActionResult.Complete, result);
	}
	
	@Test
	public void shouldNotCompleteGenreIfAlredyHasOne() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getLastFM(metadata)).thenReturn(lastfmAlbum);
		when(metadata.getGenre()).thenReturn(genre );
		
		coverArtService.completeLastFM(metadata);
		
		verify(metadata, never()).setGenre(isA(String.class));
	}
	
	@Test
	public void shouldReturnMetadataCompleteIfLastfmHasNotNewValues() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.isSomethingNew(lastfmAlbum, metadata)).thenReturn(false);
		
		ActionResult result = coverArtService.completeLastFM(metadata);
		assertEquals(ActionResult.Complete, result);
	}
}
