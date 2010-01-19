package org.lastfm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestHelperScrobbler {
	Metadata metadata = Mockito.mock(Metadata.class);

	@Test
	public void shouldNotAddAScrobblingifTrackSmallerThan240() throws Exception {
		HelperScrobbler helperScrobbler = new HelperScrobbler();
		
		setExpectations();
		Mockito.when(metadata.getArtist()).thenReturn("Above & Beyond");
		Mockito.when(metadata.getTitle()).thenReturn("Anjunabeach");

		helperScrobbler.send(metadata);
		Assert.assertEquals(0, helperScrobbler.metadataMap.size());
	}

	
	
	@Test
	public void shouldNotAddAScrobblingIfNoArtist() throws Exception {
		HelperScrobbler helperScrobbler = new HelperScrobbler();
		
		setExpectations();
		Mockito.when(metadata.getArtist()).thenReturn("");
		Mockito.when(metadata.getTitle()).thenReturn("Anjunabeach");

		helperScrobbler.send(metadata);
		Assert.assertEquals(0, helperScrobbler.metadataMap.size());
	}
	
	@Test
	public void shouldNotAddAScrobblingIfNoTitle() throws Exception {
		HelperScrobbler helperScrobbler = new HelperScrobbler();
		
		setExpectations();
		Mockito.when(metadata.getArtist()).thenReturn("Above & Beyond");
		Mockito.when(metadata.getTitle()).thenReturn("");
		
		helperScrobbler.send(metadata);
		Assert.assertEquals(0, helperScrobbler.metadataMap.size());
	}
	
	@Test
	public void shouldNotAddAScrobblingIfIsNotAaudioFile() throws Exception {
		List<File> fileList = new ArrayList<File>();
		HelperScrobbler helperScrobbler = new HelperScrobbler();
		
		File file = new File("resources/log4j.properties");
		fileList.add(file);

		List<Metadata> metadataList = helperScrobbler.getMetadataList(fileList);
		Assert.assertEquals(0, metadataList.size());
	}
	
	private void setExpectations() {
		Mockito.when(metadata.getAlbum()).thenReturn("");
		Mockito.when(metadata.getLength()).thenReturn(1);
		Mockito.when(metadata.getTrackNumber()).thenReturn(1);
	}
	
}
