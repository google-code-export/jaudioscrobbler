package org.lastfm.helper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestExporterHelper {
	@InjectMocks
	private ExporterHelper exporterHelper = new ExporterHelper();
	
	@Mock
	private ImageExporter imageExporter;
	@Mock
	private MetadataExporter metadataExporter;

	private List<Metadata> metadatas = new ArrayList<Metadata>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldExportImage() throws Exception {
		exporterHelper.export(metadatas);
		Mockito.verify(imageExporter).export(metadatas);
		Mockito.verify(metadataExporter).export(metadatas);
	}

}
