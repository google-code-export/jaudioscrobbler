package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ActionResult;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.lastfm.model.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;
import de.umass.lastfm.scrobble.ScrobbleResult;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */

public class TestScrobblerHelper {
	private static final String EMPTY_STRING = "";

	@InjectMocks
	private ScrobblerHelper helperScrobbler = new ScrobblerHelper();
	
	@Mock
	private Metadata metadata;
	@Mock
	private Map<Metadata, Long> metadataMap;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private User currentUser;
	@Mock
	private LastFMTrackHelper lastFMTrackHelper;
	@Mock
	private Session session;
	
	private ActionResult result;
	private String username = "josdem";
	private String password = "password";

	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(currentUser.getUsername()).thenReturn(username);
		when(currentUser.getPassword()).thenReturn(password);
		when(controlEngine.get(Model.CURRENT_USER)).thenReturn(currentUser);
		helperScrobbler.setControlEngine(controlEngine);
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
		when(metadata.getArtist()).thenReturn(EMPTY_STRING);
		when(metadata.getTitle()).thenReturn("Anjunabeach");

		result = helperScrobbler.send(metadata);
		
		notSendToScrobblingMapAssertion();
	}

	private void notSendToScrobblingMapAssertion() {
		verify(metadataMap, never()).size();
		verify(metadataMap, never()).put(isA(Metadata.class), isA(Long.class));
		assertEquals(ActionResult.NOT_SCROBBLEABLE, result);
	}

	@Test
	public void shouldNotAddAScrobblingIfNoTitle() throws Exception {
		setExpectations();
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn(EMPTY_STRING);

		result = helperScrobbler.send(metadata);
		notSendToScrobblingMapAssertion();
	}

	@Test
	public void shouldFailWhenSubmitScrobbler() throws Exception {
		ScrobbleResult result = mock(ScrobbleResult.class);
		when(metadataMap.get(metadata)).thenReturn(100L);
		setExpectations();
		setMetadataTrackExpectations();
		when(result.isSuccessful()).thenReturn(false);
		when(lastFMTrackHelper.scrobble(metadata.getArtist(), metadata.getTitle(), metadataMap.get(metadata).intValue(), currentUser.getSession())).thenReturn(result);
		
		assertEquals(ActionResult.SESSIONLESS, helperScrobbler.send(metadata));
	}

	private void setMetadataTrackExpectations() {
		when(metadata.getLength()).thenReturn(300);
		when(metadata.getArtist()).thenReturn("Above & Beyond");
		when(metadata.getTitle()).thenReturn("Anjunabeach");
	}

	@Test
	public void shouldSendAnScrobbler() throws Exception {
		ScrobbleResult result = mock(ScrobbleResult.class);
		when(metadataMap.get(metadata)).thenReturn(100L);
		when(currentUser.getSession()).thenReturn(session);
		setExpectations();
		setMetadataTrackExpectations();
		when(result.isSuccessful()).thenReturn(true);
		when(lastFMTrackHelper.scrobble(metadata.getArtist(), metadata.getTitle(), metadataMap.get(metadata).intValue(), currentUser.getSession())).thenReturn(result);
		
		assertEquals(ActionResult.SUCCESS, helperScrobbler.send(metadata));
	}
	
	private void setExpectations() {
		when(metadata.getAlbum()).thenReturn(EMPTY_STRING);
		when(metadata.getLength()).thenReturn(1);
		when(metadata.getTrackNumber()).thenReturn(1);
	}

	
	@Test
	public void shouldReturnIfNoLogin() throws Exception {
		setMetadataTrackExpectations();
		when(currentUser.getUsername()).thenReturn(EMPTY_STRING);
		
		assertEquals(ActionResult.LOGGED_OUT, helperScrobbler.send(metadata));
	}
	

}
