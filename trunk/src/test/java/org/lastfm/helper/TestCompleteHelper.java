package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Album;


public class TestCompleteHelper {
	@InjectMocks
	private CompleteHelper completeHelper = new CompleteHelper();
	@Mock
	private Metadata metadata;
	@Mock
	private ImageIcon imageIcon;
	@Mock
	private LastFMAlbumHelper helper;
	@Mock
	private Album info;

	private String year = "2011";
	private String genre = "Minimal Techno";
	private String artist = "Linas";
	private String album = "Time Lapse";
	
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
	public void shouldGetYear() throws Exception {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String year = simpleDateFormat.format(date);
		when(info.getReleaseDate()).thenReturn(date);
		when(helper.getYear(date)).thenReturn(year);
		
		LastfmAlbum lastfmAlbum = completeHelper.getYear(metadata);
		
		assertEquals(year, lastfmAlbum.getYear());
	}

	@Test
	public void shouldNotSetYearIfNoInfoRelease() throws Exception {
		LastfmAlbum lastfmAlbum = completeHelper.getYear(metadata);

		verify(helper, never()).getYear(isA(Date.class));
		assertNull(lastfmAlbum.getYear());
	}
	
}
