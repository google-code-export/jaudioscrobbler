package org.lastfm.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TestOutStreamWriter {

	@InjectMocks
	private OutStreamWriter outStreamWriter = new OutStreamWriter();
	
	private File pepeGarden = new File("src/test/resources/audio/Jaytech - Pepe Garden (Original Mix).mp3");
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetWriter() throws Exception {
		OutputStream writer = outStreamWriter.getWriter(pepeGarden);
		assertNotNull(writer);
	}

}
