package org.lastfm.metadata;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */


public class TestWriteMetadata {
	MetadataWriter metadataWriter;
	private File file;
	private AudioFile audioFile;
	private Tag tag;
	
	@Before
	public void initialize() {
		file = mock(File.class);
		audioFile = mock(AudioFile.class);
		tag = mock(Tag.class);
		
		when(audioFile.getTag()).thenReturn(tag);
		metadataWriter = new MetadataWriter(file, audioFile);
	}
	
	@Test
	public void shouldWriteAlbum() throws Exception {
		String album = "Sahara Nights";
		metadataWriter.writeAlbum(album);

		verify(tag).setField(FieldKey.ALBUM, album);
		verify(audioFile).commit();
		assertEquals(album, metadataWriter.getAlbum());
	}
	
	@Test
	public void shouldWriteTrackNumber() throws Exception {
		String trackNumber = "1";
		
		metadataWriter.writeTrackNumber(trackNumber);
		verify(audioFile).commit();
		assertEquals(trackNumber, metadataWriter.getTrackNumber());
	}
}
