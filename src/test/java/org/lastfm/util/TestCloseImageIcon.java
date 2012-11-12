package org.lastfm.util;

import static org.junit.Assert.*;

import java.awt.Image;

import javax.swing.ImageIcon;
import static org.mockito.Mockito.*;

import org.jas.service.ImageService;
import org.jas.util.CloseImageIcon;
import org.jas.util.ImageIconBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestCloseImageIcon {

	@InjectMocks
	private CloseImageIcon closeImageIcon = new CloseImageIcon();
	
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
		when(imageService.readCloseImage()).thenReturn(image);
		
		ImageIcon imageIcon = closeImageIcon.getImageIcon();
		
		verify(imageService).readCloseImage();
		assertTrue(closeImageIcon instanceof ImageIconBase);
		assertNotNull(imageIcon);
	}
	
}
