
package org.lastfm.metadata;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
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

public class TestMp3Reader{
	@InjectMocks
	private Mp3Reader reader = new Mp3Reader();
	@Mock
	private MP3File audioFile;
	@Mock
	private File file;
	@Mock
	private Tag tag;
	@Mock
	private Artwork artwork;
	@Mock
	private AudioHeader header;
	@Mock
	private AudioFileHelper audioFileHelper;
	@Mock
	private BufferedImage bufferedImage;
	
	private String artist = "Armin Van Buuren";
	private String title = "Control Freak (Sander Van Doorn Remix)";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(audioFileHelper.read(file)).thenReturn(audioFile);
		when(audioFile.getTag()).thenReturn(tag);
		when(audioFile.getAudioHeader()).thenReturn(header);
		when(artwork.getImage()).thenReturn(bufferedImage);
		when(tag.getFirstArtwork()).thenReturn(artwork);
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(header.getBitRate()).thenReturn("64");
	}
	
	@Test
	public void shouldUpdateID3toV2() throws Exception {
		when(audioFile.hasID3v2Tag()).thenReturn(false);
		reader.getMetadata(file);
		
		((MP3File) verify(audioFile)).setID3v2TagOnly((AbstractID3v2Tag) anyObject());
		((MP3File) verify(audioFile)).commit();
	}
	
	@Test
	public void shouldGetMetadata() throws Exception {
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		reader.getMetadata(file);
		
		((MP3File) verify(audioFile)).getTag();
		((MP3File) verify(audioFile)).getAudioHeader();
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		when(tag.getFirst(FieldKey.ARTIST)).thenReturn(artist);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(artist, metadata.getArtist());
	}

	@Test
	public void shouldGetTitle() throws Exception {
		when(tag.getFirst(FieldKey.TITLE)).thenReturn(title);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(title, metadata.getTitle());
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		String album = "Nobody Seems To Care / Murder Weapon";
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
	public void shouldGetLength() throws Exception {
		int length = 325;
		AudioHeader header = mock(AudioHeader.class);
		when(header.getBitRate()).thenReturn("64");
		when(audioFile.hasID3v2Tag()).thenReturn(true);
		when(header.getTrackLength()).thenReturn(length);
		when(audioFile.getAudioHeader()).thenReturn(header);
		Metadata metadata = reader.getMetadata(file);
		
		assertEquals(length, metadata.getLength());
	}
	
	@Test
	public void shouldGetArtwork() throws Exception {
		reader.getMetadata(file);
		verify(artwork).getImage();
	}
	
	@Test
	public void shouldGetFile() throws Exception {
		Metadata metadata = reader.getMetadata(file);
		assertNotNull(metadata.getFile());
	}
	
}
