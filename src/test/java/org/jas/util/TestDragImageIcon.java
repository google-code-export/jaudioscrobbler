package org.jas.util;

import static org.junit.Assert.*;

import java.awt.Image;

import javax.swing.ImageIcon;
import static org.mockito.Mockito.*;

import org.jas.service.ImageService;
import org.jas.util.DragImageIcon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDragImageIcon {

	@InjectMocks
	private DragImageIcon dragImageIcon = new DragImageIcon();
	
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
		when(imageService.readDragImage()).thenReturn(image);
		ImageIcon imageIcon = dragImageIcon.getImageIcon();
		verify(imageService).readDragImage();
		assertNotNull(imageIcon);
	}

}
