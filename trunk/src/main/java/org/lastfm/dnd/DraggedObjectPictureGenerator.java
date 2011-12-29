package org.lastfm.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.util.Picture;

public class DraggedObjectPictureGenerator extends DraggedObjectFileSystemGenerator {

	private static final Log LOG = LogFactory.getLog(DraggedObjectPictureGenerator.class);

	@Override
	public DraggedObject get(Transferable transferable) {
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		if (flavors == null) {
			return null;
		}
		for (DataFlavor flavor : flavors) {
			List<File> files = null;
			files = tryGetFile(transferable, flavor);
			if (files != null && files.size() == 1 && !files.get(0).isDirectory()) {
				try {
					Object draggedObject = new Picture(files.get(0));
					return new SimpleDraggedObject(draggedObject);
				} catch (Exception e) {
					LOG.error(e, e);
				}
			}
		}
		return new SimpleDraggedObject(null);
	}

}
