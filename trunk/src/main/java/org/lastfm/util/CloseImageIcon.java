package org.lastfm.util;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jas.service.ImageService;

public class CloseImageIcon implements ImageIconBase {

	private ImageService imageService = new ImageService();;
	
	private ImageIcon imageIcon;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public ImageIcon getImageIcon() {
		try {
			Image dragImage = imageService.readCloseImage();
			imageIcon = new ImageIcon(dragImage); 
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		return imageIcon;
	}

}
