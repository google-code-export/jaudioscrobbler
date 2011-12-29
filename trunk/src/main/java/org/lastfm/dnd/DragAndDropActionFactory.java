package org.lastfm.dnd;

import java.awt.Container;

public interface DragAndDropActionFactory {
	DragAndDropAction getAction(Container frame);
}
