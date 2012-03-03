package org.lastfm.helper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.lastfm.util.FileUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMetadataExporter {
	private static final String NEW_LINE = "\n";
	private static final String DASH = " - ";
	private static final String DOT = ". ";
	private static final String PAR_OPEN = " (";
	private static final String PAR_CLOSE = ")";
	
	@InjectMocks
	private MetadataExporter metadataExporter = new MetadataExporter();
	
	@Mock
	private FileUtils fileUtils;
	@Mock
	private Formatter formatter;
	@Mock
	private Metadata metadata;
	@Mock
	private File file;
	@Mock
	private OutStreamWriter outputStreamWriter;
	@Mock
	private OutputStream writer;
	
	private String album = "Bliksem";
	private String artist = "Sander van Doorn";
	private String title = "Bliksem";
	int lenght = 397;
	private String lenghtFormated = "6:37";
	private List<Metadata> metadatas = new ArrayList<Metadata>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(metadata.getAlbum()).thenReturn(album);
		when(metadata.getArtist()).thenReturn(artist);
		when(metadata.getTitle()).thenReturn(title);
		when(metadata.getLength()).thenReturn(lenght);
		when(formatter.getDuration(metadata.getLength())).thenReturn(lenghtFormated);
		metadatas.add(metadata);
		when(fileUtils.createTempFile()).thenReturn(file);
		when(outputStreamWriter.getWriter(file)).thenReturn(writer);
	}
	
	@Test
	public void shouldExport() throws Exception {
		metadataExporter.export(metadatas);
		verify(writer).write(Integer.toString(1).getBytes());
		verify(writer).write(DOT.getBytes());
		verify(writer).write(metadata.getArtist().getBytes());
		verify(writer).write(DASH.getBytes());
		verify(writer).write(metadata.getTitle().getBytes());
		verify(writer).write(PAR_OPEN.getBytes());
		verify(writer).write(formatter.getDuration(metadata.getLength()).getBytes());
		verify(writer).write(PAR_CLOSE.getBytes());
		verify(writer).write(NEW_LINE.getBytes());
	}
}
