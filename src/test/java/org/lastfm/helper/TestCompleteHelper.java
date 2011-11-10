package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;


public class TestCompleteHelper {
	@InjectMocks
	private CompleteHelper completeHelper = new CompleteHelper();
	@Mock
	private Metadata metadata;
	@Mock
	private LastfmAlbum lastfmAlbum;
	@Mock
	private ImageIcon imageIcon;
	@Mock
	private LastFMAlbumHelper helper;
	@Mock
	private Album info;
	@Mock
	private Image image;

	private String year = "2011";
	private String genre = "Minimal Techno";
	private String artist = "Linas";
	private String album = "Time Lapse";
	private String imageURL = "http://userserve-ak.last.fm/serve/300x300/35560281.png";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(helper.getAlbum(artist, album)).thenReturn(info);
	}
	
	private void setArtistAndAlbumExpectations() {
		when(metadata.getArtist()).thenReturn(artist);
		when(metadata.getAlbum()).thenReturn(album);
	}

		@Test
	public void shouldCompleteIfNoMetadata() throws Exception {
		setArtistAndAlbumExpectations();
		
		assertTrue(completeHelper.canLastFMHelpToComplete(metadata));
		verify(helper).getAlbum(artist, album);
	}
	

	@Test
	public void shouldCompleteIfNoCoverArt() throws Exception {
		setArtistAndAlbumExpectations();
		when(metadata.getYear()).thenReturn(year);
		when(metadata.getGenre()).thenReturn(genre);
		
		assertTrue(completeHelper.canLastFMHelpToComplete(metadata));
		verify(helper).getAlbum(artist, album);
	}
	
	@Test
	public void shouldCompleteIfNoGenre() throws Exception {
		setArtistAndAlbumExpectations();
		when(metadata.getCoverArt()).thenReturn(imageIcon);
		when(metadata.getYear()).thenReturn(year);
		
		assertTrue(completeHelper.canLastFMHelpToComplete(metadata));
		verify(helper).getAlbum(artist, album);
	}
	
	@Test
	public void shouldCompleteIfNoYear() throws Exception {
		setArtistAndAlbumExpectations();
		when(metadata.getCoverArt()).thenReturn(imageIcon);
		when(metadata.getGenre()).thenReturn(genre);
		
		assertTrue(completeHelper.canLastFMHelpToComplete(metadata));
		verify(helper).getAlbum(artist, album);
	}
	
	private void setYearGenreCoverExpectations() {
		when(metadata.getCoverArt()).thenReturn(imageIcon);
		when(metadata.getYear()).thenReturn(year);
		when(metadata.getGenre()).thenReturn(genre);
	}
	
	@Test
	public void shouldNotCompleteIfMetadataIsComplete() throws Exception {
		setArtistAndAlbumExpectations();
		setYearGenreCoverExpectations();
		
		assertFalse(completeHelper.canLastFMHelpToComplete(metadata));
	}
	
	@Test
	public void shouldNotCompleteIfNoArtist() throws Exception {
		setYearGenreCoverExpectations();
		when(metadata.getAlbum()).thenReturn(album);
		
		assertFalse(completeHelper.canLastFMHelpToComplete(metadata));
	}
	
	@Test
	public void shouldNotCompleteIfNoAlbum() throws Exception {
		when(metadata.getArtist()).thenReturn(artist);
		setYearGenreCoverExpectations();
		
		assertFalse(completeHelper.canLastFMHelpToComplete(metadata));
	}
	
	@Test
	public void shouldGetLastfm() throws Exception {
		setYearAndGenreExpectations();
		setImageExpectations();
		
		LastfmAlbum lastFMalbum = completeHelper.getLastFM(metadata);
		
		assertEquals(year, lastFMalbum.getYear());
		assertEquals(genre, lastFMalbum.getGenre());
		assertEquals(imageIcon, lastFMalbum.getImageIcon());
	}
	
	
	@Test
	public void shouldGetLastfmEvenThoughThereisNoCoverArt() throws Exception {
		setYearAndGenreExpectations();
		
		LastfmAlbum lastFMalbum = completeHelper.getLastFM(metadata);
		
		assertEquals(year, lastFMalbum.getYear());
		assertEquals(genre, lastFMalbum.getGenre());
		assertNull(lastFMalbum.getImageIcon());
	}
	
	@Test
	public void shouldNotReadImageIfNoValidURL() throws Exception {
		setYearAndGenreExpectations();
		
		completeHelper.getLastFM(metadata);
		
		verify(helper, never()).getImageIcon((Image) anyObject());
	}

	private void setYearAndGenreExpectations() {
		Date date = new Date();
		when(info.getReleaseDate()).thenReturn(date);
		when(helper.getYear(date)).thenReturn(year);
		when(helper.getGenre(info)).thenReturn(genre);
	}
	
	@Test
	public void shouldDetectWhenNothingChanged() throws Exception {
		boolean somethingDifferent = completeHelper.isSomethingNew(lastfmAlbum, metadata);
		assertFalse(somethingDifferent);
	}
	
	@Test
	public void shouldDetectLastfmHasNewValues() throws Exception {
		when(lastfmAlbum.getYear()).thenReturn(year);
		boolean somethingDifferent = completeHelper.isSomethingNew(lastfmAlbum, metadata);
		assertTrue(somethingDifferent);
	}
	
	@Test
	public void shouldNotSetCoverArtIfAnyInFile() throws Exception {
		when(metadata.getCoverArt()).thenReturn(imageIcon);
		setImageExpectations();
		
		LastfmAlbum lastFMalbum = completeHelper.getLastFM(metadata);
		
		assertNull(lastFMalbum.getImageIcon());
	}

	private void setImageExpectations() throws MalformedURLException, IOException {
		when(info.getImageURL(ImageSize.EXTRALARGE)).thenReturn(imageURL);
		when(helper.readImage(imageURL)).thenReturn(image);
		when(helper.getImageIcon(image)).thenReturn(imageIcon);
	}
	
	@Test
	public void shouldNotAskForGenreIfAlreadyHasOne() throws Exception {
		when(metadata.getGenre()).thenReturn(genre);
		
		LastfmAlbum lastFMalbum = completeHelper.getLastFM(metadata);
		
		verify(helper, never()).getGenre(info);
		assertTrue(StringUtils.isEmpty(lastFMalbum.getGenre()));
	}
	
	@Test
	public void shouldNotAskForYearIfAlreadyHasOne() throws Exception {
		when(metadata.getYear()).thenReturn(year);
		
		LastfmAlbum lastFMalbum = completeHelper.getLastFM(metadata);
		
		verify(info, never()).getReleaseDate();
		assertTrue(StringUtils.isEmpty(lastFMalbum.getYear()));
	}
}
