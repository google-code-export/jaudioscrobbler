package org.lastfm.metadata;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

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
}
