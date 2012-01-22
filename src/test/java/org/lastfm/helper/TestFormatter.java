package org.lastfm.helper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;


public class TestFormatter {
	private String title = "legends of the fall";
	private String expected = "Legends Of The Fall";
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
	private String artist = "Angel Tears";
	private String album = "Vision";
	private Metadata metadata = new Metadata();

	
	@Before
	public void setup() throws Exception {
		metadata.setArtist(artist);
		metadata.setTitle(title);
		metadata.setAlbum(album);
	}
	
	@Test
	public void shouldCapitalizeTitle() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setTitle(title);

		assertTrue(formatter.isNotCamelized(metadata));
		assertEquals(expected , metadata.getTitle());
	}
	
	@Test
	public void shouldDetectAaccentMarkTitle() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setTitle(badFormatA);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatAExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectEaccentMarkTitle() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setTitle(badFormatE);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatEExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectIaccentMarkTitle() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setTitle(badFormatI);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatIExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectOaccentMarkTitle() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setTitle(badFormatO);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(bFormatOExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectUaccentMarkTitle() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setTitle(badFormatU);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatUExpected , metadata.getTitle());

	}
	
	@Test
	public void shouldDetectAaccentMarkArtist() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setArtist(badFormatA);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatAExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectEaccentMarkArtist() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setArtist(badFormatE);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatEExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectIaccentMarkArtist() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setArtist(badFormatI);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatIExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectOaccentMarkArtist() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setArtist(badFormatO);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(bFormatOExpected , metadata.getArtist());

	}
	
	@Test
	public void shouldDetectUaccentMarkArtist() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setArtist(badFormatU);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatUExpected , metadata.getArtist());
	}
	
	@Test
	public void shouldDetectAaccentMarkAlbum() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setAlbum(badFormatA);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatAExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectEaccentMarkAlbum() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setAlbum(badFormatE);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatEExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectIaccentMarkAlbum() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setAlbum(badFormatI);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatIExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectOaccentMarkAlbum() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setAlbum(badFormatO);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(bFormatOExpected , metadata.getAlbum());

	}
	
	@Test
	public void shouldDetectUaccentMarkAlbum() throws Exception {
		Formatter formatter = new Formatter();
		metadata.setAlbum(badFormatU);

		assertTrue(formatter.isABadFormat(metadata));
		assertEquals(badFormatUExpected , metadata.getAlbum());

	}
}
