package org.lastfm.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to resize an image
 */

public class ImageUtils {
	private Log log = LogFactory.getLog(this.getClass());

	public ImageIcon resize(ImageIcon imageIcon, int width, int height) {
		BufferedImage image = (BufferedImage) imageIcon.getImage();
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return new ImageIcon(resizedImage);
	}

	public ImageIcon getDefaultImage() {
		try {
			Image image = ImageIO.read(new URL(ApplicationState.DEFAULT_IMAGE));
			ImageIcon imageIcon = new ImageIcon(image);
			return imageIcon;
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
			return new ImageIcon();
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return new ImageIcon();
		}
	}

	private void write(BufferedImage bufferedImage, String ext, File file) throws IOException {
		ImageIO.write(bufferedImage, ext, file);
	}

	public File saveCoverArtToFile(Image image) throws IOException {
		File file = File.createTempFile(ApplicationState.PREFIX, ApplicationState.IMAGE_EXT);
		write((BufferedImage)image, ApplicationState.IMAGE_EXT, file);
		return file;
	}

}
