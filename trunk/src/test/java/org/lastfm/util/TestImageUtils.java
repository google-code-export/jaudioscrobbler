package org.lastfm.util;

import static org.junit.Assert.*;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.helper.ImageHelper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class TestImageUtils {
	@InjectMocks
	private ImageUtils imageUtils = new ImageUtils();
	@Mock
	private ImageHelper imageHelper;
	@Mock
	private Image image;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(imageHelper.read()).thenReturn(image);
	}
	
	@Test
	public void shouldGetSameDefaultImage() throws Exception {
		ImageIcon defaultImage_one = imageUtils.getDefaultImage();
		ImageIcon defaultImage_two = imageUtils.getDefaultImage();
		assertSame(defaultImage_one, defaultImage_two);
	}
}
