package org.lastfm.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to resize an image
 */

public class ImageUtils {

	public ImageIcon resize(ImageIcon imageIcon, int width, int height) {
		BufferedImage image = (BufferedImage) imageIcon.getImage();
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return new ImageIcon(resizedImage);
	}
}
