package org.lastfm.util;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.controller.service.ImageService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDefaultImageIcon {

	@InjectMocks
	private DefaultImageIcon defaultImageIcon = new DefaultImageIcon();
	
	@Mock
	private ImageService imageService;
	@Mock
	private Image image;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetImageIcon() throws Exception {
		when(imageService.readDefaultImage()).thenReturn(image);
		ImageIcon imageIcon = defaultImageIcon.getImageIcon();
		verify(imageService).readDefaultImage();
		assertNotNull(imageIcon);
	}

}
