package org.lastfm.util;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.controller.service.ImageService;

public class DefaultImageIcon implements ImageIconBase {

	private ImageService imageService = new ImageService();
	private Log log = LogFactory.getLog(getClass());

	private ImageIcon imageIcon;

	@Override
	public ImageIcon getImageIcon() {
		try {
			Image defaultImage = imageService.readDefaultImage();
			imageIcon = new ImageIcon(defaultImage);
		} catch (MalformedURLException mue) {
			log.error(mue, mue);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		return imageIcon;
	}

}
