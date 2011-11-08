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

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCompleteMetadataFromLastfm() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(true);
		when(completeHelper.getLastFM(metadata)).thenReturn(lastfmAlbum);
		when(lastfmAlbum.getImageIcon()).thenReturn(imageIcon);
		
		ActionResult result = coverArtService.completeLastFM(metadata);
		
		verify(metadata).setLastfmCoverArt(imageIcon);
		assertEquals(ActionResult.LAST_FM_SUCCESS, result);
	}
	
	@Test
	public void shouldNotCompleteLastfmCoverArtMetadataDueToMetadataComplete() throws Exception {
		when(completeHelper.canLastFMHelpToComplete(metadata)).thenReturn(false);
		
		ActionResult result = coverArtService.completeLastFM(metadata);
		assertEquals(ActionResult.METADATA_COMPLETE, result);
	}
}
