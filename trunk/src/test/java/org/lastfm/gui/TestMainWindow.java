package org.lastfm.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.action.ActionResult;
import org.lastfm.action.Actions;
import org.lastfm.action.ResponseCallback;
import org.lastfm.action.ViewEngine;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMainWindow {
	private static final String TRACK_TITLE = "I Don't Own You";
	private static final int FIRST_ROW = 0;
	@InjectMocks
	private MainWindow mainWindow = new MainWindow();
	private FrameFixture window;
	private static final String OPEN_BUTTON_NAME = "openButton";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private static final String APPLY_BUTTON_NAME = "applyButton";
	private static final String COMPLETE_BUTTON_NAME = "completeMetadataButton";
	
	@Mock
	private ViewEngineConfigurator configurator;
	@Mock
	private ViewEngine viewEngine;
	@Mock
	private Metadata metadata;
	
	@Captor
	private ArgumentCaptor<ResponseCallback<ActionResult>> responseCaptor;
	private List<Metadata> metadatas;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getViewEngine()).thenReturn(viewEngine);
		window = new FrameFixture(mainWindow.getFrame());
		window.show();
		metadatas = new ArrayList<Metadata>();
		metadatas.add(metadata);
		when(metadata.getTitle()).thenReturn(TRACK_TITLE);
	}
	
	@Test
	public void shouldOpen() throws Exception {
		window.button(OPEN_BUTTON_NAME).click();
		
		verify(viewEngine).send(Actions.METADATA);
		assertFalse(mainWindow.getSendButton().isEnabled());
		assertFalse(mainWindow.getCompleteMetadataButton().isEnabled());
		assertFalse(mainWindow.getApplyButton().isEnabled());
	}
	
	private void setSendExpectations() {
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
		mainWindow.getSendButton().setEnabled(true);
	}
	
	private ResponseCallback<ActionResult> verifySendExpectations() {
		verify(viewEngine).request(eq(Actions.SEND), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		return callback;
	}
	
	@Test
	public void shouldSend() throws Exception {
		setSendExpectations();
		
		window.button(SEND_BUTTON_NAME).click();
		
		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.SUCCESS);
		assertEquals(ApplicationState.SENT, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetALoggedOutActionResult() throws Exception {
		setSendExpectations();
		
		window.button(SEND_BUTTON_NAME).click();
		
		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.LOGGED_OUT);
		assertEquals(ApplicationState.LOGGED_OUT, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}
	
	@Test
	public void shouldSendAndGetASessionlessActionResult() throws Exception {
		setSendExpectations();
		
		window.button(SEND_BUTTON_NAME).click();
		
		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.SESSIONLESS);
		assertEquals(ApplicationState.SESSIONLESS, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}
	
	@Test
	public void shouldSendAndGetAErrorActionResult() throws Exception {
		setSendExpectations();
		
		window.button(SEND_BUTTON_NAME).click();
		
		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.ERROR);
		assertEquals(ApplicationState.ERROR, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldApply() throws Exception {
		when(viewEngine.get(Model.METADATA_ARTIST)).thenReturn(metadatas);
		mainWindow.getApplyButton().setEnabled(true);
		
		window.button(APPLY_BUTTON_NAME).click();
		
		verify(viewEngine).request(eq(Actions.WRITE), eq(metadata), isA(ResponseCallback.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldComplete() throws Exception {
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
		mainWindow.getCompleteMetadataButton().setEnabled(true);
		
		window.button(COMPLETE_BUTTON_NAME).click();
		
		verify(viewEngine).request(eq(Actions.COMPLETE_ALBUM), eq(metadata), isA(ResponseCallback.class));
	}

	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

}
