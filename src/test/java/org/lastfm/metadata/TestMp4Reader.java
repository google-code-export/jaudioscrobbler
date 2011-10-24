package org.lastfm.metadata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.helper.AudioFileHelper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class TestMp4Reader{
	@InjectMocks
	private Mp4Reader reader = new Mp4Reader();
	@Mock
	private File file;
	@Mock
	private Mp4Tag tag;
	@Mock
	private AudioFile audioFile;
	@Mock
	private AudioHeader header;
	@Mock
	private Artwork artwork;
	@Mock
	private AudioFileHelper audioFileHelper;
	@Mock
	private BufferedImage bufferedImage;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(audioFileHelper.read(file)).thenReturn(audioFile);
		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(artwork.getImage()).thenReturn(bufferedImage);
		when(tag.getFirstArtwork()).thenReturn(artwork);
		when(header.getBitRate()).thenReturn("64");
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		String album = "Simple Pleasures";
		when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(album, metadata.getAlbum());
	}
	
	@Test
	public void shouldGetGenre() throws Exception {
		String genre = "Minimal Techno";
		when(tag.getFirst(FieldKey.GENRE)).thenReturn(genre);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(genre, metadata.getGenre());
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		String artist = "Ferry Corsten";
		when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(artist, metadata.getArtist());
	}
	
	@Test
	public void shouldGetLength() throws Exception {
		int length = 325;
		when(header.getTrackLength()).thenReturn(length);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(length, metadata.getLength());
	}
	
	@Test
	public void shouldGetTitle() throws Exception {
		String title = "A Magical Moment";
		when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(title, metadata.getTitle());
	}

	@Test
	public void shouldGetTrackNumber() throws Exception {
		String trackNumber = "11";
		when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(trackNumber, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldGetTotalTracks() throws Exception {
		String totalTracks = "20";
		when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(totalTracks);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(totalTracks, metadata.getTotalTracks());
	}
	
	@Test
	public void shouldReturnZEROInTrackNumberWhenTagIsNull() throws Exception {
		String trackNumber = "0";
		when(tag.getFirst(FieldKey.TRACK)).thenReturn(null);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(trackNumber, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldReturnZEROInTotalTracksWhenTagIsNull() throws Exception {
		String totalTracks = "0";
		when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenReturn(null);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(totalTracks, metadata.getTotalTracks());
	}
	
	@Test
	public void shouldReturnZEROInTrackNumberWhenNullPointer() throws Exception {
		String trackNumber = "0";
		when(tag.getFirst(FieldKey.TRACK)).thenThrow(new NullPointerException());
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(trackNumber, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldReturnZEROInTotalTracksWhenNullPointer() throws Exception {
		String totalTracks = "0";
		when(tag.getFirst(FieldKey.TRACK_TOTAL)).thenThrow(new NullPointerException());
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(totalTracks, metadata.getTotalTracks());
	}

	@Test
	public void shouldReturnZEROInGettingCdNumberWhenNullPointer() throws Exception {
		String cdNumber = "0";
		when(tag.getFirst(FieldKey.DISC_NO)).thenThrow(new NullPointerException());
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(cdNumber, metadata.getCdNumber());
	}
	
	@Test
	public void shouldReturnZEROInGettingTotalCdsWhenNullPointer() throws Exception {
		String totalCds = "0";
		when(tag.getFirst(FieldKey.DISC_TOTAL)).thenThrow(new NullPointerException());
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(totalCds, metadata.getTotalCds());
	}
	
	@Test
	public void shouldGetArtwork() throws Exception {
		reader.getMetadata(file);
		verify(artwork).getImage();
	}
}
