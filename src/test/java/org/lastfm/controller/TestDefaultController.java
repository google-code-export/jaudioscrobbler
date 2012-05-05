package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.controller.service.DefaultService;
import org.lastfm.helper.MetadataHelper;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDefaultController {

	@InjectMocks
	private DefaultController defaultController = new DefaultController();
	
	@Mock
	private DefaultService defaultService;
	@Mock
	private MetadataHelper metadataHelper;
	@Mock
	private List<Metadata> metadatas;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCompleteTracksTotal() throws Exception {
		when(metadataHelper.isSameAlbum(metadatas)).thenReturn(true);
		
		ActionResult result = defaultController.complete(metadatas);
		
		verify(metadataHelper).isSameAlbum(metadatas);
		verify(defaultService).complete(metadatas);
		assertEquals(ActionResult.New, result);
	}
	
	@Test
	public void shouldNotCompleteTracksTotal() throws Exception {
		when(metadataHelper.isSameAlbum(metadatas)).thenReturn(false);
		
		ActionResult result = defaultController.complete(metadatas);
		
		verify(metadataHelper).isSameAlbum(metadatas);
		verify(defaultService, never()).complete(metadatas);
		assertEquals(ActionResult.Complete, result);
	}
}
