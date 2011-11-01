package org.lastfm.model;

import static org.junit.Assert.assertEquals;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestLastfmAlbum {
	@Mock
	private ImageIcon imageIcon;
	private String year = "2011";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateALastfmAlbum() throws Exception {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		lastfmAlbum.setYear(year);
		lastfmAlbum.setImageIcon(imageIcon);
		
		assertEquals(year, lastfmAlbum.getYear());
		assertEquals(imageIcon, lastfmAlbum.getImageIcon());
	}
}
