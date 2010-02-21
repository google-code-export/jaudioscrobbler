package org.lastfm.metadata;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
		file = Mockito.mock(File.class);
		audioFile = Mockito.mock(AudioFile.class);
		tag = Mockito.mock(Tag.class);
		
		Mockito.when(audioFile.getTag()).thenReturn(tag);
		metadataWriter = new MetadataWriter(file, audioFile);
	}
	
	@Test
	public void shouldWriteAlbum() throws Exception {
		String album = "Sahara Nights";
		metadataWriter.writeAlbum(album);

		Mockito.verify(tag).setField(FieldKey.ALBUM, album);
		Mockito.verify(audioFile).commit();
		assertEquals(album, metadataWriter.getAlbum());
	}
	
	@Test
	public void shouldWriteTrackNumber() throws Exception {
		String trackNumber = "1";
		
		metadataWriter.writeTrackNumber(trackNumber);
		Mockito.verify(audioFile).commit();
		assertEquals(trackNumber, metadataWriter.getTrackNumber());
	}
}
