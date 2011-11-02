package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.controller.service.CoverArtService;
import org.lastfm.helper.LastFMAlbumHelper;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;


public class TestCoverArtService {
	@InjectMocks
	private CoverArtService coverArtService = new CoverArtService();
	
	@Mock
	private LastFMAlbumHelper helper;
	@Mock
	private Album album;
	@Mock
	private Image image;
	@Mock
	private Metadata metadata;
	@Mock
	private ImageIcon imageIcon;
	@Mock
	private Date date;

	private String artistName = "Daft Punk";
	private String albumName = "Discovery";
	private String imageURL = "http://userserve-ak.last.fm/serve/300x300/66072700.png";
	private String year = "1990";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetCoverArtFromLastfm() throws Exception {
		setCoverArtExpectations();
		
		LastfmAlbum albumFromLastfm = coverArtService.getCoverArtFromLastfm(artistName, albumName);
		
		verify(album).getImageURL(ImageSize.EXTRALARGE);
		verify(helper).readImage(imageURL);
		verify(helper).getImageIcon(image);
		assertEquals(imageIcon, albumFromLastfm.getImageIcon());
	}

	private void setCoverArtExpectations() throws MalformedURLException, IOException {
		when(helper.getAlbum(artistName, albumName)).thenReturn(album);
		when(album.getImageURL(ImageSize.EXTRALARGE)).thenReturn(imageURL);
		when(helper.readImage(imageURL)).thenReturn(image);
		when(album.getReleaseDate()).thenReturn(date);
		when(helper.getYear(isA(Date.class))).thenReturn(year);
		when(helper.getImageIcon(image)).thenReturn(imageIcon);
	}
	
	
	@Test
	public void shouldWithLastfm() throws Exception {
		when(metadata.getAlbum()).thenReturn(albumName);
		when(metadata.getArtist()).thenReturn(artistName);
		setCoverArtExpectations();
		when(helper.getImageIcon(image)).thenReturn(imageIcon);
		
		ActionResult result = coverArtService.completeCoverArt(metadata);
		
		verify(metadata).setLastfmCoverArt(imageIcon);
		assertEquals(ActionResult.COVER_ART_SUCCESS, result);
	}
	
	@Test
	public void shouldGetDefaultCoverArtIfNoValidURL() throws Exception {
		when(helper.getAlbum(artistName, albumName)).thenReturn(album);
		
		assertNull(coverArtService.getCoverArtFromLastfm(artistName, albumName));
	}
	
	@Test
	public void shouldNotGetCoverArtIfNoAlbum() throws Exception {
		assertNull(coverArtService.getCoverArtFromLastfm(artistName, albumName));
	}
	
	@Test
	public void shouldReturnMetadataCompleteIfHasCoverArt() throws Exception {
		when(metadata.getCoverArt()).thenReturn(imageIcon);
		
		ActionResult result = coverArtService.completeCoverArt(metadata);
		
		assertEquals(ActionResult.METADATA_COMPLETE, result);
	}
	
	@Test
	public void shouldGetMetadataErrorIfNoValidURL() throws Exception {
		when(metadata.getAlbum()).thenReturn(albumName);
		when(metadata.getArtist()).thenReturn(artistName);
		when(helper.getAlbum(artistName, albumName)).thenReturn(album);
		when(album.getImageURL(ImageSize.EXTRALARGE)).thenReturn(StringUtils.EMPTY);
		
		ActionResult result = coverArtService.completeCoverArt(metadata);
		assertEquals(ActionResult.COVER_ART_ERROR, result);
	}
	
	@Test
	public void shouldGetMetadataCompleteIfNoAlbum() throws Exception {
		when(metadata.getAlbum()).thenReturn(StringUtils.EMPTY);
		
		ActionResult result = coverArtService.completeCoverArt(metadata);
		assertEquals(ActionResult.NOT_ENOUGH_ARGUMENTS, result);
	}
}
