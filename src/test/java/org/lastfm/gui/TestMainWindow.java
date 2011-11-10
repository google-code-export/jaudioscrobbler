package org.lastfm.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.action.ActionResult;
import org.lastfm.action.Actions;
import org.lastfm.action.ResponseCallback;
import org.lastfm.action.ViewEngine;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.lastfm.util.Environment;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMainWindow {
	@InjectMocks
	private MainWindow mainWindow = new MainWindow();

	private static final String TRACK_TITLE = "I Don't Own You";
	private static final int FIRST_ROW = 0;
	private static final String OPEN_BUTTON_NAME = "openButton";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private static final String APPLY_BUTTON_NAME = "applyButton";
	private static final String COMPLETE_BUTTON_NAME = "completeMetadataButton";
	private static final String ALBUM = "Mirage";
	private static final String TRACK_NUMBER = "5";
	private static final String TOTAL_TRACKS_NUMBER = "16";
	private FrameFixture window;

	@Mock
	private ViewEngineConfigurator viewEngineConfigurator;
	@Mock
	private ControlEngineConfigurator controlEngineConfigurator;
	@Mock
	private ViewEngine viewEngine;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private Metadata metadata;
	@Mock
	private Set<Metadata> metadataWithAlbum;

	@Captor
	private ArgumentCaptor<ResponseCallback<ActionResult>> responseCaptor;
	private List<Metadata> metadatas;
	private Set<Metadata> metadatasWaitingForMetadata;
	

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(viewEngineConfigurator.getViewEngine()).thenReturn(viewEngine);
		when(controlEngineConfigurator.getControlEngine()).thenReturn(controlEngine);
		window = new FrameFixture(mainWindow.getFrame());
		window.show();
		metadatas = new ArrayList<Metadata>();
		metadatasWaitingForMetadata = new HashSet<Metadata>();
		metadatas.add(metadata);
		metadatasWaitingForMetadata.add(metadata);
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
		callback.onResponse(ActionResult.Sent);
		assertEquals(ActionResult.Sent, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetALoggedOutActionResult() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.NotLogged);
		assertEquals(ActionResult.NotLogged, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetASessionlessActionResult() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.Sessionless);
		assertEquals(ActionResult.Sessionless, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetAErrorActionResult() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.Error);
		assertEquals(ActionResult.Error, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldApply() throws Exception {
		when(viewEngine.get(Model.METADATA_ARTIST)).thenReturn(metadatasWaitingForMetadata);
		mainWindow.getApplyButton().setEnabled(true);

		window.button(APPLY_BUTTON_NAME).click();

		verify(viewEngine).request(eq(Actions.WRITE), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.Updated);
		assertEquals(ActionResult.Updated, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
		verifyButtonsAssertions();
	}

	@Test
	public void shouldNotApplyDueToErrorActionResult() throws Exception {
		when(viewEngine.get(Model.METADATA_ARTIST)).thenReturn(metadatasWaitingForMetadata);
		mainWindow.getApplyButton().setEnabled(true);

		window.button(APPLY_BUTTON_NAME).click();

		verify(viewEngine).request(eq(Actions.WRITE), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.Error);
		assertEquals(ActionResult.Error, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldComplete() throws Exception {
		setMetadataExpectations();
		mainWindow.getCompleteMetadataButton().setEnabled(true);

		window.button(COMPLETE_BUTTON_NAME).click();

		verify(viewEngine).request(eq(Actions.COMPLETE_ALBUM), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.New);

		verifyCompleteAlbumAssertions();

		verify(viewEngine).request(eq(Actions.COMPLETE_LAST_FM), eq(metadata), responseCaptor.capture());
		callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.New);
		
		verify(controlEngine).remove(Model.METADATA_ARTIST);
		verify(controlEngine).set(Model.METADATA_ARTIST, metadataWithAlbum, null);
		assertTrue(mainWindow.getApplyButton().isEnabled());
		verifyButtonsAssertions();
	}

	private void verifyButtonsAssertions() {
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
		assertTrue(mainWindow.getCompleteMetadataButton().isEnabled());
		assertTrue(mainWindow.getOpenButton().isEnabled());
		assertTrue(mainWindow.getDescriptionTable().isEnabled());
	}

	private void verifyCompleteAlbumAssertions() {
		assertEquals(ALBUM, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.ALBUM_COLUMN));
		assertEquals(TRACK_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TRACK_NUMBER_COLUMN));
		assertEquals(TOTAL_TRACKS_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN));
		assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	private void setMetadataExpectations() {
		when(metadata.getAlbum()).thenReturn(ALBUM);
		when(metadata.getTrackNumber()).thenReturn(TRACK_NUMBER);
		when(metadata.getTotalTracks()).thenReturn(TOTAL_TRACKS_NUMBER);
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
	}

	@Test
	public void shouldKnowWhenRowChanged() throws Exception {
		// Bug FEST in Linux at KeyEvent.VK_ENTER is not working property
		if (!Environment.isLinux()) {
			when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);

			mainWindow.getDescriptionTable().setEnabled(true);
			window.robot.doubleClick(mainWindow.getDescriptionTable());
			window.robot.enterText(ALBUM);
			mainWindow.tableLoaded = true;
			window.robot.pressKey(KeyEvent.VK_ENTER);

			assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
		}
	}

	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

}
