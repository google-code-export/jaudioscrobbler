package org.lastfm.dnd;

import java.awt.datatransfer.Transferable;

public interface DraggedObjectGenerator {
	DraggedObject getPreview(Transferable transferable);

	DraggedObject getContent(Transferable transferable);
}
