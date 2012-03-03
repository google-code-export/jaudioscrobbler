package org.lastfm.helper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;


public class TestFormatter {
	private Formatter formatter = new Formatter();
	private String badFormatA = "¿Cu&aacute;ndo?";
	private String badFormatAExpected = "¿Cuándo?";
	private String badFormatE = "¿Qu&eacute;?";
	private String badFormatEExpected = "¿Qué?";
	private String badFormatI = "m&iacute;";
	private String badFormatIExpected = "mí";
	private String badFormatO = "¿C&oacute;mo?";
	private String bFormatOExpected = "¿Cómo?";
	private String badFormatU = "t&uacute;";
	private String badFormatUExpected = "tú";
	private String artist = "angel tears";
	private String artistExpected = "Angel Tears";
	private String title = "legends of the fall";
	private String titleExpected = "Legends Of The Fall";
	private String album = "vision";
	private String albumExpected = "Vision";
	private Metadata metadata = new Metadata();

	
	@Before
	public void setup() throws Exception {
		metadata.setArtist(artist);
		metadata.setTitle(title);
		metadata.setAlbum(album);
	}
	
	@Test
	public void shouldDetectAaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatA);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatAExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectEaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatE);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatEExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectIaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatI);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatIExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectOaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatO);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(bFormatOExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectUaccentMarkTitle() throws Exception {
		metadata.setTitle(badFormatU);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatUExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectAaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatA);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatAExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectEaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatE);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatEExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectIaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatI);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatIExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectOaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatO);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(bFormatOExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectUaccentMarkArtist() throws Exception {
		metadata.setArtist(badFormatU);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatUExpected , metadata.getArtist());
	}
	
	@Test
	public void shouldDetectAaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatA);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatAExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectEaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatE);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatEExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectIaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatI);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatIExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectOaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatO);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(bFormatOExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectUaccentMarkAlbum() throws Exception {
		metadata.setAlbum(badFormatU);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatUExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldCapitalizeTitle() throws Exception {
		metadata.setTitle(title);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(titleExpected , metadata.getTitle());
	}
	
	@Test
	public void shouldCapitalizeArtist() throws Exception {
		metadata.setArtist(artist);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(artistExpected , metadata.getArtist());
	}
	
	@Test
	public void shouldCapitalizeAlbum() throws Exception {
		metadata.setAlbum(album);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(albumExpected , metadata.getAlbum());
	}
	
	@Test
	public void shouldCapitalizeAlbumWhenUppercase() throws Exception {
		metadata.setAlbum(album.toUpperCase());

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(albumExpected , metadata.getAlbum());
	}
	
	@Test
	public void shouldCapitalizeTitleWhenUppercase() throws Exception {
		metadata.setTitle(title.toUpperCase());

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(titleExpected , metadata.getTitle());
	}
	
	@Test
	public void shouldCapitalizeArtistWhenUppercase() throws Exception {
		metadata.setArtist(artist.toUpperCase());

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(artistExpected , metadata.getArtist());
	}
	
	@Test
	public void shouldCapitalizeArtistWhenDash() throws Exception {
		String artist = "de-pazz";
		String artistExpected = "De-pazz";
		
		metadata.setArtist(artist);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(artistExpected , metadata.getArtist());
	}
	
	@Test
	public void shouldCapitalizeTitleWhenDash() throws Exception {
		String title = "blue-eyed";
		String titleExpected = "Blue-eyed";
		
		metadata.setTitle(title);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(titleExpected , metadata.getTitle());
	}
	
	@Test
	public void shouldCapitalizeAlbumWhenDash() throws Exception {
		String album = "blue-eyed";
		String albumExpected = "Blue-eyed";
		
		metadata.setAlbum(album);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(albumExpected , metadata.getAlbum());
	}
	
	@Test
	public void shouldGetDuration() throws Exception {
		int lenght = 397;
		String expectedDuration = "6:37";
		assertEquals(expectedDuration, formatter.getDuration(lenght));
	}
}
