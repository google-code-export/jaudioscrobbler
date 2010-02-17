package org.lastfm;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestMetadataMp3 {
	File file = Mockito.mock(File.class);
	MP3File audioFile = Mockito.mock(MP3File.class);

	@Test
	public void shouldUpdateID3toV2() throws Exception {
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(false);
		new MetadataMp3(file, audioFile);
		
		((MP3File) Mockito.verify(audioFile)).setID3v2TagOnly((AbstractID3v2Tag) Mockito.anyObject());
		((MP3File) Mockito.verify(audioFile)).commit();
	}
	
	@Test
	public void shouldGetMetadata() throws Exception {
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		new MetadataMp3(file, audioFile);
		
		((MP3File) Mockito.verify(audioFile)).getTag();
		((MP3File) Mockito.verify(audioFile)).getAudioHeader();
		
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		String artist = "Armin Van Buuren";
		Tag tag = Mockito.mock(Tag.class);
		
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		Mockito.when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(artist, metadata.getArtist());
	}
	
	@Test
	public void shouldGetTitle() throws Exception {
		String title = "Control Freak (Sander Van Doorn Remix)";
		Tag tag = Mockito.mock(Tag.class);
		
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		Mockito.when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(title, metadata.getTitle());
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		String album = "Nobody Seems To Care / Murder Weapon";
		Tag tag = Mockito.mock(Tag.class);
		
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		Mockito.when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(album, metadata.getAlbum());
	}
	
	@Test
	public void shouldGetLength() throws Exception {
		int length = 325;
		AudioHeader header = Mockito.mock(AudioHeader.class);
		
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		Mockito.when(header.getTrackLength()).thenReturn(length);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(length, metadata.getLength());
	}
	
	@Test
	public void shouldGetTrackNumber() throws Exception {
		String trackNumber = "11";
		Tag tag = Mockito.mock(Tag.class);
		
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		Mockito.when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(11, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldNotGetTrackNumber() throws Exception {
		String trackNumber = "";
		Tag tag = Mockito.mock(Tag.class);
		
		Mockito.when(audioFile.hasID3v2Tag()).thenReturn(true);
		Mockito.when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Metadata metadata = new MetadataMp3(file, audioFile);
		
		assertEquals(-1, metadata.getTrackNumber());
	}
}
