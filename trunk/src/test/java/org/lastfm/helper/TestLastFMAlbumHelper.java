package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Album;


public class TestLastFMAlbumHelper {
	private LastFMAlbumHelper lastFMAlbumHelper = new LastFMAlbumHelper();
	private Date releaseDate = new Date();
	@Mock
	private Album album;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetYear() throws Exception {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		String currentYear = simpleDateformat.format(releaseDate);
		String year = lastFMAlbumHelper.getYear(releaseDate);
		assertEquals(currentYear, year);
	}
	
	@Test
	public void shouldGetEmptyYear() throws Exception {
		assertEquals(StringUtils.EMPTY, lastFMAlbumHelper.getYear(null));
	}
	
	@Test
	public void shouldMatchAGenre() throws Exception {
		Collection<String> tags = new ArrayList<String>();
		String tag = "House";
		tags.add(tag);
		
		when(album.getTags()).thenReturn(tags);
		
		String result = lastFMAlbumHelper.getGenre(album);
		assertEquals(tag, result);
	}
	
	@Test
	public void shouldNotMatchAGenre() throws Exception {
		Collection<String> tags = new ArrayList<String>();
		String tag = "usa";
		tags.add(tag);
		
		when(album.getTags()).thenReturn(tags);
		
		String result = lastFMAlbumHelper.getGenre(album);
		assertEquals(StringUtils.EMPTY, result);
	}
}
