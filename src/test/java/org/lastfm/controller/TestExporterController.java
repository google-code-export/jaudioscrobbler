package org.lastfm.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.jas.action.ActionResult;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.helper.ExporterHelper;
import org.lastfm.model.ExportPackage;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestExporterController {

	@InjectMocks
	private ExporterController exporterController = new ExporterController();
	
	@Mock
	private ExportPackage exportPackage;
	@Mock
	private ExporterHelper exporterHelper;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSendMetadata() throws Exception {
		when(exporterHelper.export(exportPackage)).thenReturn(ActionResult.Exported);
		
		ActionResult result = exporterController.sendMetadata(exportPackage);
		
		verify(exporterHelper).export(exportPackage);
		assertEquals(ActionResult.Exported, result);
	}
	
	@Test
	public void shouldReportErrorInSendingMetadata() throws Exception {
		when(exporterHelper.export(exportPackage)).thenThrow(new IOException());
		
		ActionResult result = exporterController.sendMetadata(exportPackage);
		
		assertEquals(ActionResult.Error, result);
	}

}
