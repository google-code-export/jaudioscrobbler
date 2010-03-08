package org.lastfm.metadata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.Test;
import org.lastfm.BaseTestCase;
import org.mockito.Mock;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestMetadataMp4 extends BaseTestCase{
	
	@Mock
	File file;
	@Mock
	Mp4Tag tag;
	@Mock
	AudioFile audioFile;
	@Mock
	AudioHeader header;

	@Test
	public void shouldGetAlbum() throws Exception {
		String album = "Simple Pleasures";

		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(tag.getFirst(FieldKey.ALBUM)).thenReturn(album);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(album, metadata.getAlbum());
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		String artist = "Ferry Corsten";

		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(artist, metadata.getArtist());
	}
	
	@Test
	public void shouldGetLength() throws Exception {
		int length = 325;

		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(header.getTrackLength()).thenReturn(length);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(length, metadata.getLength());
	}
	
	@Test
	public void shouldGetTitle() throws Exception {
		String title = "A Magical Moment";

		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(title, metadata.getTitle());
	}
	
	@Test
	public void shouldGetTrackNumber() throws Exception {
		String trackNumber = "11";

		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(11, metadata.getTrackNumber());
	}
	
	@Test
	public void shouldGetNotTrackNumber() throws Exception {
		String trackNumber = "";

		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(tag.getFirst(FieldKey.TRACK)).thenReturn(trackNumber);
		
		Metadata metadata = new MetadataMp4(file, audioFile);
		assertEquals(-1, metadata.getTrackNumber());
	}
	
}
