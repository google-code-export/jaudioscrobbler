package org.lastfm.helper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.lastfm.ApplicationState;

public class ImageHelper {

	public Image read() throws MalformedURLException, IOException {
		return ImageIO.read(new File(ApplicationState.DEFAULT_IMAGE));
	}

	public File createTempFile() throws IOException {
		return File.createTempFile(ApplicationState.PREFIX, ApplicationState.IMAGE_EXT);
	}

	public void write(Image bufferedImage, String ext, File file) throws IOException {
		ImageIO.write((BufferedImage) bufferedImage, ext, file);
	}

}
