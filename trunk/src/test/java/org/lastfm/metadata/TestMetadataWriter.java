package org.lastfm.metadata;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.helper.ArtworkHelper;
import org.lastfm.helper.AudioFileHelper;
import org.lastfm.util.ImageUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */


public class TestMetadataWriter {
	@InjectMocks
	MetadataWriter metadataWriter = new MetadataWriter();
	
	@Mock
	private AudioFile audioFile;
	@Mock
	private Tag tag;
	@Mock
	private File file;
	@Mock
	private AudioFileHelper audioFileHelper;
	@Mock
	private ImageIcon imageIcon;
	@Mock
	private Image image;
	@Mock
	private ImageUtils imageUtils;
	@Mock
	private ArtworkHelper artworkHelper;
	@Mock
	private Artwork artwork;
	
	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);
		when(audioFile.getTag()).thenReturn(tag);
	}
	
	@Test
	public void shouldSetFile() throws Exception {
		when(audioFileHelper.read(file)).thenReturn(audioFile);
		metadataWriter.setFile(file);
		
		verify(audioFile).getTag();
	}
	
	@Test
	public void shouldWriteArtist() throws Exception {
		String artist = "Markus Schulz";
		metadataWriter.writeArtist(artist);
		
		verify(tag).setField(FieldKey.ARTIST, artist);
		verify(audioFile).commit();
	}
	
	@Test
	public void shouldWriteTrackName() throws Exception {
		String trackName = "Nowhere";
		metadataWriter.writeTitle(trackName);
		
		verify(tag).setField(FieldKey.TITLE, trackName);
		verify(audioFile).commit();
	}
	
	@Test
	public void shouldWriteAlbum() throws Exception {
		String album = "Sahara Nights";
		metadataWriter.writeAlbum(album);

		verify(tag).setField(FieldKey.ALBUM, album);
		verify(audioFile).commit();
	}
	
	@Test
	public void shouldWriteTrackNumber() throws Exception {
		String trackNumber = "1";
		
		metadataWriter.writeTrackNumber(trackNumber);
		verify(tag).setField(FieldKey.TRACK, trackNumber);
		verify(audioFile).commit();
	}
	
	@Test
	public void shouldWriteTotalTracksNumber() throws Exception {
		String totalTracksNumber = "10";
		
		metadataWriter.writeTotalTracksNumber(totalTracksNumber);
		verify(tag).setField(FieldKey.TRACK_TOTAL, totalTracksNumber);
		verify(audioFile).commit();
	}

	@Test
	public void shouldWriteCoverArt() throws Exception {
		when(imageIcon.getImage()).thenReturn(image);
		when(imageUtils.saveCoverArtToFile(image)).thenReturn(file);
		when(artworkHelper.createArtwork()).thenReturn(artwork);
		
		metadataWriter.writeCoverArt(imageIcon);
		
		verify(imageUtils).saveCoverArtToFile(image);
		verify(artwork).setFromFile(file);
		verify(tag).setField(isA(Artwork.class));
		verify(audioFile).commit();
	}
	
	@Test
	public void shouldWriteCdNumber() throws Exception {
		String cdNumber = "1";
		
		boolean result = metadataWriter.writeCdNumber(cdNumber);
		
		verify(tag).setField(FieldKey.DISC_NO, cdNumber);
		verify(audioFile).commit();
		assertTrue(result);
	}
	
	@Test
	public void shouldWriteTotalCds() throws Exception {
		String totalCds = "2";
		
		boolean result = metadataWriter.writeTotalCds(totalCds);
		
		verify(tag).setField(FieldKey.DISC_TOTAL, totalCds);
		verify(audioFile).commit();
		assertTrue(result);
	}
	
	@Test
	public void shouldNotWriteTrackNumberIfEmptyString() throws Exception {
		assertFalse(metadataWriter.writeTrackNumber(""));
	}
	
	@Test
	public void shouldNotWriteTotalTracksNumberIfEmptyString() throws Exception {
		assertFalse(metadataWriter.writeTotalTracksNumber(""));
	}
	
	@Test
	public void shouldNotWriteCdNumberIfEmptyString() throws Exception {
		assertFalse(metadataWriter.writeCdNumber(""));
	}
	
	@Test
	public void shouldNotWriteTotalCdsIfEmptyString() throws Exception {
		assertFalse(metadataWriter.writeTotalCds(""));
	}
}
