package org.lastfm.util;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.helper.ImageHelper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestImageUtils {
	@InjectMocks
	private ImageUtils imageUtils = new ImageUtils();
	@Mock
	private ImageHelper imageHelper;
	@Mock
	private Image image;
	@Mock
	private File file;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(imageHelper.read()).thenReturn(image);
	}
	
	@Test
	public void shouldGetSameDefaultImage() throws Exception {
		ImageIcon defaultImage_one = imageUtils.getDefaultImage();
		ImageIcon defaultImage_two = imageUtils.getDefaultImage();
		assertSame(defaultImage_one, defaultImage_two);
	}
	
	@Test
	public void shouldSaveCoverArtToFile() throws Exception {
		when(imageHelper.createTempFile()).thenReturn(file);
		
		imageUtils.saveCoverArtToFile(image);
		
		verify(imageHelper).createTempFile();
		verify(imageHelper).write(image, ApplicationState.IMAGE_EXT, file);
	}
}
