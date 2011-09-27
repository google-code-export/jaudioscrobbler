package org.lastfm.helper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lastfm.ApplicationState;

public class ImageHelper {

	public Image read() throws MalformedURLException, IOException {
		return ImageIO.read(new URL(ApplicationState.DEFAULT_IMAGE));
	}

}
