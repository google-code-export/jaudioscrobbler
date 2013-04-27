package org.jas.helper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Image;
import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.jas.ApplicationState;
import org.jas.service.ImageService;
import org.junit.Test;

public class TestImageHelper {

	private static final String FILE_PROTOCOL = "file://";
	private static final String IMAGE_PATH = "/src/test/resources/images/close.png";
	private static final String PREFIX = "PREFIX";
	private ImageService imageService = new ImageService();
		
	@Test
	public void shouldCreateTempFile() throws Exception {
		File tempFile = imageService.createTempFile(StringUtils.EMPTY);
		assertTrue(tempFile.getName().contains(ApplicationState.PREFIX));
		assertTrue(tempFile.getName().contains(ApplicationState.IMAGE_EXT));
	}
	
	@Test
	public void shouldCreateTempFileWithCustomPrefix() throws Exception {
		File tempFile = imageService.createTempFile(PREFIX);
		assertTrue(tempFile.getName().contains(PREFIX));
		assertTrue(tempFile.getName().contains(ApplicationState.IMAGE_EXT));
	}
	
	@Test
	public void shouldReadImageFromUrl() throws Exception {
		File file = new File("");
		
		StringBuilder sb = new StringBuilder();
		sb.append(FILE_PROTOCOL);
		sb.append(file.getAbsolutePath());
		sb.append(IMAGE_PATH);
		
		Image image = imageService.readImage(sb.toString());
		assertNotNull(image);
	}

}
