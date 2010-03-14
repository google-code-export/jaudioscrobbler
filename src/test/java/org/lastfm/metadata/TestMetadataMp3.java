package org.lastfm.metadata;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.junit.Test;
import org.lastfm.BaseTestCase;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataMp3;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class TestMetadataMp3 extends BaseTestCase{
	File file = mock(File.class);
	
	@Mock
	MP3File audioFile;

	@Test
	public void shouldUpdateID3toV2() throws Exception {
		when(audioFile.hasID3v2Tag()).thenReturn(false);
		new MetadataMp3(file, audioFile);
		
		((MP3File) verify(audioFile)).setID3v2TagOnly((AbstractID3v2Tag) anyObject());
		((MP3File) verify(audioFile)).commit();
	}
	
	@Test
	public void shouldGetMetadata() throws Exception {
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		new MetadataMp3(file, audioFile);
		
		((MP3File) verify(audioFile)).getTag();
		((MP3File) verify(audioFile)).getAudioHeader();
		
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		String artist = "Armin Van Buuren";
		Tag tag = mock(Tag.class);
		
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
		when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(artist, metadata.getArtist());
	}
	
	@Test
	public void shouldGetTitle() throws Exception {
		String title = "Control Freak (Sander Van Doorn Remix)";
		Tag tag = mock(Tag.class);
		
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
		when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(title, metadata.getTitle());
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		String album = "Nobody Seems To Care / Murder Weapon";
		Tag tag = mock(Tag.class);
		
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
		when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(album, metadata.getAlbum());
	}
	
	@Test
	public void shouldGetLength() throws Exception {
		int length = 325;
		AudioHeader header = mock(AudioHeader.class);
		
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(header.getTrackLength()).thenReturn(length);
		when(audioFile.getAudioHeader()).thenReturn(header);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(length, metadata.getLength());
	}
	
	@Test
	public void shouldGetTrackNumber() throws Exception {
		String trackNumber = "11";
		Tag tag = mock(Tag.class);
		
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(11, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldNotGetTrackNumber() throws Exception {
		String trackNumber = "";
		Tag tag = mock(Tag.class);
		
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(-1, metadata.getTrackNumber());
	}
}
