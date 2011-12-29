package org.lastfm.dnd;

import java.awt.Image;
import java.awt.Point;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.gui.ImagePanel;
import org.lastfm.observ.ObservValue;
import org.lastfm.observ.Observable;
import org.lastfm.observ.ObserverCollection;
import org.lastfm.util.FileSystemValidatorLight;
import org.lastfm.util.Picture;

public class ImageDropListener implements DropListener {
	private static final Class<?>[] classes = new Class<?>[] { Picture.class };

	@Override
	public Class<?>[] handledTypes() {
		return classes;
	}

	private final ImagePanel imagePanel;
	private final Observable<ObservValue<ImagePanel>> nombre = new Observable<ObservValue<ImagePanel>>();

	private Log log = LogFactory.getLog(getClass());

	public ImageDropListener(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
	}

	@Override
	public void doDrop(DraggedObject draggedObject, Point location) {
		Picture picture = draggedObject.get(Picture.class);
		if (picture != null) {
			doDrop(picture, location);
		}
	}

	public void doDrop(Picture pic, Point location) {
		if (!pic.isValidImageSize()) {
			log.info("editContact.portrait.dnd.error.size");
			return;
		}

		if (!pic.isProportionedImage()) {
			log.info("editContact.portrait.dnd.error.proportion");
			return;
		}

		Image image = pic.getImage();
		if (image != null) {
			imagePanel.setImage(image, .17, .17);
			this.nombre.fire(new ObservValue<ImagePanel>(imagePanel));
		}
	}

	public void doDrop(FileSystemValidatorLight validator, Point location) {
		log.info("editContact.portrait.dnd.error.file");
	}

	@Override
	public boolean validateDrop(DraggedObject draggedObject, Point location) {
		return draggedObject.is(Picture.class, FileSystemValidatorLight.class);
	}

	public ObserverCollection<ObservValue<ImagePanel>> onDropped() {
		return nombre;
	}
}
