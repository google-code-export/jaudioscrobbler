package org.lastfm.helper;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class TestLastFMAlbumHelper {
	private LastFMAlbumHelper lastFMAlbumHelper = new LastFMAlbumHelper();
	private Date releaseDate = new Date();
	
	@Test
	public void shouldGetYear() throws Exception {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		String currentYear = simpleDateformat.format(releaseDate);
		String year = lastFMAlbumHelper.getYear(releaseDate);
		assertEquals(currentYear, year);
	}
}
