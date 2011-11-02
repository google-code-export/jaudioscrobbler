package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.helper.ScrobblerHelper;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestScrobblerController {
	@InjectMocks
	private ScrobblerController controller = new ScrobblerController();

	@Mock
	private ScrobblerHelper helperScrobbler;
	@Mock
	private Metadata metadata;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
	}

	@Test
	public void shouldSendMetadata() throws Exception {
		when(helperScrobbler.send(metadata)).thenReturn(ActionResult.SUCCESS);

		ActionResult result = controller.sendMetadata(metadata);

		verify(helperScrobbler).send(metadata);
		assertEquals(ActionResult.SUCCESS, result);
	}

	@Test
	public void shouldDetectWhenErrorInScrobbling() throws Exception {
		when(helperScrobbler.send(metadata)).thenReturn(ActionResult.ERROR);

		ActionResult result = controller.sendMetadata(metadata);

		verify(helperScrobbler).send(metadata);
		assertEquals(ActionResult.ERROR, result);
	}
	
	@Test
	public void shouldSetup() throws Exception {
		controller.setup();
		verify(helperScrobbler).setControlEngine(controlEngine);
	}
}