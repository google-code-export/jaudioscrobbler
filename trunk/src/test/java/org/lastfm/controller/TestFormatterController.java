package org.lastfm.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ActionResult;
import org.lastfm.controller.service.FormatterService;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestFormatterController {

	@InjectMocks
	private FormatterController formatterController = new FormatterController();
	
	@Mock
	private FormatterService formatterService;
	@Mock
	private Metadata metadata;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldFormatWhenBadFormat() throws Exception {
		when(formatterService.isABadFormat(metadata)).thenReturn(true);
		when(formatterService.isNotCamelized(metadata)).thenReturn(false);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.New, result);
	}
	
	@Test
	public void shouldFormatWhenNotCamelized() throws Exception {
		when(formatterService.isABadFormat(metadata)).thenReturn(false);
		when(formatterService.isNotCamelized(metadata)).thenReturn(true);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.New, result);
	}
	
	@Test
	public void shouldReturnComplete() throws Exception {
		when(formatterService.isABadFormat(metadata)).thenReturn(false);
		when(formatterService.isNotCamelized(metadata)).thenReturn(false);
		ActionResult result = formatterController.format(metadata);
		assertEquals(ActionResult.Complete, result);
	}
	
}