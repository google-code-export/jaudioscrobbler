package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestHelperScrobbler {
	Metadata metadata = mock(Metadata.class);
	private HelperScrobbler helperScrobbler;

	@Before
	public void initialize() {
		helperScrobbler = new HelperScrobbler();
	}

	@Test
	public void shouldNotAddAScrobblingifTrackSmallerThan240() throws Exception {
		setExpectations();
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		helperScrobbler.send(metadata);
		assertEquals(0, helperScrobbler.metadataMap.size());
	}

	@Test
	public void shouldNotAddAScrobblingIfNoArtist() throws Exception {

		setExpectations();
		when(metadata.getArtist()).thenReturn("");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		helperScrobbler.send(metadata);
		assertEquals(0, helperScrobbler.metadataMap.size());
	}

	@Test
	public void shouldNotAddAScrobblingIfNoTitle() throws Exception {
		setExpectations();
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("");

		helperScrobbler.send(metadata);
		assertEquals(0, helperScrobbler.metadataMap.size());
	}

	@Test
	public void shouldNotAddAScrobblingIfIsNotAaudioFile() throws Exception {
		List<File> fileList = new ArrayList<File>();

		File file = new File("resources/log4j.properties");
		fileList.add(file);

		List<Metadata> metadataList = helperScrobbler.getMetadataList(fileList);
		assertEquals(0, metadataList.size());
	}

	@Test
	public void shouldFailHandShakeWhenSendAnScrobbler() throws Exception {
		Scrobbler scrobbler = mock(Scrobbler.class);
		ResponseStatus responseStatus = mock(ResponseStatus.class);

		ScrobblerFactory factory = mock(ScrobblerFactory.class);

		when(factory.getScrobbler("tst", "1.0", ApplicationState.userName)).thenReturn(scrobbler);
		when(responseStatus.getStatus()).thenReturn(ResponseStatus.FAILED);
		when(scrobbler.handshake(ApplicationState.password)).thenReturn(responseStatus);

		setExpectations();
		when(metadata.getLength()).thenReturn(250);
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		helperScrobbler.factory = factory;

		assertEquals(ApplicationState.FAILURE, helperScrobbler.send(metadata));
		assertEquals(1, helperScrobbler.metadataMap.size());

	}

	@Test
	public void shouldFailWhenSubmitScrobbler() throws Exception {
		Scrobbler scrobbler = mock(Scrobbler.class);
		ResponseStatus responseStatus = mock(ResponseStatus.class);
		ResponseStatus sctrobblingStatus = mock(ResponseStatus.class);
		Map<Metadata, Long> metadataMap = mock(HashMap.class);
		
		setExpectations();
		when(metadataMap.get(metadata)).thenReturn(100L);
		when(metadata.getLength()).thenReturn(250);
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		ScrobblerFactory factory = mock(ScrobblerFactory.class);

		helperScrobbler.factory = factory;
		helperScrobbler.metadataMap = metadataMap;
		
		when(factory.getScrobbler("tst", "1.0", ApplicationState.userName)).thenReturn(scrobbler);
		when(responseStatus.getStatus()).thenReturn(ResponseStatus.OK);
		when(sctrobblingStatus.getStatus()).thenReturn(ResponseStatus.FAILED);
		when(scrobbler.handshake(ApplicationState.password)).thenReturn(responseStatus);
		when(scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
				metadata.getAlbum(), metadata.getLength(), metadata
				.getTrackNumber(), Source.USER, metadataMap.get(
						metadata).longValue())).thenReturn(sctrobblingStatus);
		
		assertEquals(ApplicationState.FAILURE, helperScrobbler.send(metadata));
	}
	
	@Test
	public void shouldSendAnScrobbler() throws Exception {
		Scrobbler scrobbler = mock(Scrobbler.class);
		ResponseStatus responseStatus = mock(ResponseStatus.class);
		ResponseStatus sctrobblingStatus = mock(ResponseStatus.class);
		Map<Metadata, Long> metadataMap = mock(HashMap.class);
		
		setExpectations();
		when(metadataMap.get(metadata)).thenReturn(100L);
		when(metadata.getLength()).thenReturn(250);
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		ScrobblerFactory factory = mock(ScrobblerFactory.class);

		helperScrobbler.factory = factory;
		helperScrobbler.metadataMap = metadataMap;
		
		when(factory.getScrobbler("tst", "1.0", ApplicationState.userName)).thenReturn(scrobbler);
		when(responseStatus.getStatus()).thenReturn(ResponseStatus.OK);
		when(sctrobblingStatus.getStatus()).thenReturn(ResponseStatus.OK);
		when(scrobbler.handshake(ApplicationState.password)).thenReturn(responseStatus);
		when(scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
				metadata.getAlbum(), metadata.getLength(), metadata
				.getTrackNumber(), Source.USER, metadataMap.get(
						metadata).longValue())).thenReturn(sctrobblingStatus);
		
		assertEquals(ApplicationState.OK, helperScrobbler.send(metadata));
	}

	private void setExpectations() {
		when(metadata.getAlbum()).thenReturn("");
		when(metadata.getLength()).thenReturn(1);
		when(metadata.getTrackNumber()).thenReturn(1);
	}

}
