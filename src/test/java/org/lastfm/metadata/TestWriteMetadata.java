package org.lastfm.metadata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */


public class TestWriteMetadata {
	@InjectMocks
	MetadataWriter metadataWriter = new MetadataWriter();
	
	@Mock
	private AudioFile audioFile;
	@Mock
	private Tag tag;
	
	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);
		when(audioFile.getTag()).thenReturn(tag);
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
