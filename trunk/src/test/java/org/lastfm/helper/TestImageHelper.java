package org.lastfm.helper;

import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.lastfm.ApplicationState;

public class TestImageHelper {

	private static final String PREFIX = "PREFIX";
	private ImageHelper imageHelper = new ImageHelper();
		
	@Test
	public void shouldCreateTempFile() throws Exception {
		File tempFile = imageHelper.createTempFile(StringUtils.EMPTY);
		assertTrue(tempFile.getName().contains(ApplicationState.PREFIX));
		assertTrue(tempFile.getName().contains(ApplicationState.IMAGE_EXT));
	}
	
	@Test
	public void shouldCreateTempFileWithCustomPrefix() throws Exception {
		File tempFile = imageHelper.createTempFile(PREFIX);
		assertTrue(tempFile.getName().contains(PREFIX));
		assertTrue(tempFile.getName().contains(ApplicationState.IMAGE_EXT));
	}
	
	@Test
	public void shouldReadDragImage() throws Exception {
		Image image = imageHelper.readDragImage();
		assertEquals(150, image.getHeight(new ImageObserver() {
			
			@Override
			public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
				return false;
			}
		}));
	}
	
	@Test
	public void shouldReadCloseImage() throws Exception {
		Image image = imageHelper.readCloseImage();
		assertEquals(15, image.getHeight(new ImageObserver() {
			
			@Override
			public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
				return false;
			}
		}));
	}

}
