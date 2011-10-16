package org.lastfm.gui;

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
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getViewEngine()).thenReturn(viewEngine);
		window = new FrameFixture(mainWindow.getFrame());
		window.show();
	}
	
	@Test
	public void shouldOpen() throws Exception {
		window.button(OPEN_BUTTON_NAME).click();
		
		verify(viewEngine).send(Actions.METADATA);
		assertFalse(mainWindow.getSendButton().isEnabled());
		assertFalse(mainWindow.getCompleteMetadataButton().isEnabled());
		assertFalse(mainWindow.getApplyButton().isEnabled());
	}
	
	@Test
	public void shouldSend() throws Exception {
		List<Metadata> metadatas = setMetadataListExpectations();
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
		mainWindow.getSendButton().setEnabled(true);
		
		window.button(SEND_BUTTON_NAME).click();
		
		verify(viewEngine).request(eq(Actions.SEND), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.SUCCESS);
		mainWindow.getDescriptionTable().getModel().setValueAt(ApplicationState.SENT, FIRST_ROW, ApplicationState.STATUS_COLUMN);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldApply() throws Exception {
		List<Metadata> metadatas = setMetadataListExpectations();
		when(viewEngine.get(Model.METADATA_ARTIST)).thenReturn(metadatas);
		mainWindow.getApplyButton().setEnabled(true);
		
		window.button(APPLY_BUTTON_NAME).click();
		
		verify(viewEngine).request(eq(Actions.WRITE), eq(metadata), isA(ResponseCallback.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldComplete() throws Exception {
		List<Metadata> metadatas = setMetadataListExpectations();
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
		mainWindow.getCompleteMetadataButton().setEnabled(true);
		
		window.button(COMPLETE_BUTTON_NAME).click();
		
		verify(viewEngine).request(eq(Actions.COMPLETE_ALBUM), eq(metadata), isA(ResponseCallback.class));
	}

	private List<Metadata> setMetadataListExpectations() {
		List<Metadata> metadatas = new ArrayList<Metadata>();
		metadatas.add(metadata);
		return metadatas;
	}
	
	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

}
