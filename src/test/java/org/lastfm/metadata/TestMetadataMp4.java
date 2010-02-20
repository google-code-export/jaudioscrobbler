package org.lastfm.metadata;

import static org.junit.Assert.*;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataMp4;
import org.mockito.Mockito;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestMetadataMp4 {
	File file = Mockito.mock(File.class);
	AudioFile audioFile = Mockito.mock(AudioFile.class);
	Mp4Tag tag = Mockito.mock(Mp4Tag.class);
	AudioHeader header = Mockito.mock(AudioHeader.class);

	@Test
	public void shouldGetAlbum() throws Exception {
		String album = "Simple Pleasures";

		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Mockito.when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(album, metadata.getAlbum());
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		String artist = "Ferry Corsten";

		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Mockito.when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(artist, metadata.getArtist());
	}
	
	@Test
	public void shouldGetLength() throws Exception {
		int length = 325;

		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Mockito.when(header.getTrackLength()).thenReturn(length);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(length, metadata.getLength());
	}
	
	@Test
	public void shouldGetTitle() throws Exception {
		String title = "A Magical Moment";

		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Mockito.when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(title, metadata.getTitle());
	}
	
	@Test
	public void shouldGetTrackNumber() throws Exception {
		String trackNumber = "11";

		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Mockito.when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(11, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldGetNotTrackNumber() throws Exception {
		String trackNumber = "";

		Mockito.when(audioFile.getTag()).thenReturn(tag);
		Mockito.when(audioFile.getAudioHeader()).thenReturn(header);
		Mockito.when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(-1, metadata.getTrackNumber());
	}
	
}
