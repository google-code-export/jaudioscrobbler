package org.lastfm.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.helper.ImageHelper;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to resize an image
 */

public class ImageUtils {
	private Log log = LogFactory.getLog(this.getClass());
	private ImageIcon imageIcon;
	private ImageHelper imageHelper = new ImageHelper();

	public ImageIcon resize(ImageIcon imageIcon, int width, int height) {
		BufferedImage image = (BufferedImage) imageIcon.getImage();
		int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return new ImageIcon(resizedImage);
	}

	public ImageIcon getDefaultImage() {
		return imageIcon == null ? getDeafaultIcon() : imageIcon;
	}

	private ImageIcon getDeafaultIcon() {
		try {
			Image image = imageHelper.read();
			imageIcon = new ImageIcon(image);
			return imageIcon;
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		return imageIcon;
	}

	private void write(Image bufferedImage, String ext, File file) throws IOException {
		imageHelper.write(bufferedImage, ext, file);
	}

	public File saveCoverArtToFile(Image image) throws IOException {
		File file = imageHelper.createTempFile();
		log.info("image height: " + image.getHeight(new ImageObserver() {
			
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		}));
		write(image, ApplicationState.IMAGE_EXT, file);
		return file;
	}

}
