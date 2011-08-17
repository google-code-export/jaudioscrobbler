package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */

public class TestHelperScrobbler {
	@InjectMocks
	private HelperScrobbler helperScrobbler = new HelperScrobbler();
	
	@Mock
	private Metadata metadata;
	@Mock
	private ScrobblerFactory factory;
	@Mock
	private Map<Metadata, Long> metadataMap;
	@Mock
	private Scrobbler scrobbler;
	@Mock
	private ResponseStatus responseStatus;
	
	int result;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		ApplicationState.userName = "josdem";
	}

	@Test
	public void shouldNotAddAScrobblingifTrackSmallerThan240() throws Exception {
		setExpectations();
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		result = helperScrobbler.send(metadata);
		
		notSendToScrobblingMapAssertion();
	}

	@Test
	public void shouldNotAddAScrobblingIfNoArtist() throws Exception {
		setExpectations();
		when(metadata.getArtist()).thenReturn("");
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		result = helperScrobbler.send(metadata);
		
		notSendToScrobblingMapAssertion();
	}

	private void notSendToScrobblingMapAssertion() {
		verify(metadataMap, never()).size();
		verify(metadataMap, never()).put(isA(Metadata.class), isA(Long.class));
		assertEquals(ApplicationState.FAILURE, result);
	}

	@Test
	public void shouldNotAddAScrobblingIfNoTitle() throws Exception {
		setExpectations();
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("");

		result = helperScrobbler.send(metadata);
		notSendToScrobblingMapAssertion();
	}

	@Test
	public void shouldFailHandShakeWhenSendAnScrobbler() throws Exception {
		when(factory.getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, ApplicationState.userName)).thenReturn(scrobbler);
		when(responseStatus.getStatus()).thenReturn(ResponseStatus.FAILED);
		when(scrobbler.handshake(ApplicationState.password)).thenReturn(responseStatus);
		setExpectations();
		setMetadataTrackExpectations();

		result = helperScrobbler.send(metadata);
		
		verify(metadataMap).size();
		verify(metadataMap).put(isA(Metadata.class), isA(Long.class));
		assertEquals(ApplicationState.FAILURE, result);
	}

	@Test
	public void shouldFailWhenSubmitScrobbler() throws Exception {
		ResponseStatus sctrobblingStatus = mock(ResponseStatus.class);
		when(metadataMap.get(metadata)).thenReturn(100L);
		setExpectations();
		setMetadataTrackExpectations();
		setHandshakeExpectations();
		when(sctrobblingStatus.getStatus()).thenReturn(ResponseStatus.FAILED);
		when(scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
				metadata.getAlbum(), metadata.getLength(), metadata
				.getTrackNumber(), Source.USER, metadataMap.get(
						metadata).longValue())).thenReturn(sctrobblingStatus);
		
		assertEquals(ApplicationState.FAILURE, helperScrobbler.send(metadata));
	}

	private void setMetadataTrackExpectations() {
		when(metadata.getLength()).thenReturn(250);
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");
	}

	private void setHandshakeExpectations() throws IOException {
		when(factory.getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, ApplicationState.userName)).thenReturn(scrobbler);
		when(responseStatus.getStatus()).thenReturn(ResponseStatus.OK);
		when(scrobbler.handshake(ApplicationState.password)).thenReturn(responseStatus);
	}
	
	@Test
	public void shouldSendAnScrobbler() throws Exception {
		ResponseStatus sctrobblingStatus = mock(ResponseStatus.class);
		when(metadataMap.get(metadata)).thenReturn(100L);
		setExpectations();
		setMetadataTrackExpectations();
		setHandshakeExpectations();
		when(sctrobblingStatus.getStatus()).thenReturn(ResponseStatus.OK);
		when(scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
				metadata.getAlbum(), metadata.getLength(), metadata
				.getTrackNumber(), Source.USER, metadataMap.get(
						metadata).longValue())).thenReturn(sctrobblingStatus);
		
		assertEquals(ApplicationState.OK, helperScrobbler.send(metadata));
	}
	
	@Test
	public void shouldCatchConnectionErrorWhenSubmitScrobbler() throws Exception {
		ResponseStatus sctrobblingStatus = mock(ResponseStatus.class);
		when(metadataMap.get(metadata)).thenReturn(100L);
		setExpectations();
		setMetadataTrackExpectations();
		setHandshakeExpectations();
		when(sctrobblingStatus.getStatus()).thenReturn(ResponseStatus.FAILED);
		Exception connectException = new ConnectException();
		when(scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
				metadata.getAlbum(), metadata.getLength(), metadata
				.getTrackNumber(), Source.USER, metadataMap.get(
						metadata).longValue())).thenThrow(connectException);
		
		assertEquals(ApplicationState.ERROR, helperScrobbler.send(metadata));
	}
	
	private void setExpectations() {
		when(metadata.getAlbum()).thenReturn("");
		when(metadata.getLength()).thenReturn(1);
		when(metadata.getTrackNumber()).thenReturn(1);
	}

	@Test
	public void shouldCatchUnknownHostExceptionWhenSubmitScrobbler() throws Exception {
		ResponseStatus sctrobblingStatus = mock(ResponseStatus.class);
		when(metadataMap.get(metadata)).thenReturn(100L);
		setExpectations();
		setMetadataTrackExpectations();
		setHandshakeExpectations();
		when(sctrobblingStatus.getStatus()).thenReturn(ResponseStatus.FAILED);
		Exception unknownHostException = new UnknownHostException();
		when(scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
				metadata.getAlbum(), metadata.getLength(), metadata
				.getTrackNumber(), Source.USER, metadataMap.get(
						metadata).longValue())).thenThrow(unknownHostException);
		
		assertEquals(ApplicationState.ERROR, helperScrobbler.send(metadata));
	}
	
	@Test
	public void shouldReturnIfNoLogin() throws Exception {
		ApplicationState.userName = "";
		setMetadataTrackExpectations();
		
		assertEquals(ApplicationState.LOGGED_OUT, helperScrobbler.send(metadata));
		verify(factory, never()).getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, ApplicationState.userName);
	}
	

}
