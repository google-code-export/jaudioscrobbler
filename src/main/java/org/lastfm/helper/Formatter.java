package org.lastfm.helper;

import org.lastfm.metadata.Metadata;

public class Formatter {

	public boolean isABadFormat(Metadata metadata) {
		if(metadata.getTitle().contains("&amp;")){
			String title = metadata.getTitle().replace("&amp;", "&");
			metadata.setTitle(title);
			return true;
		}
		return false;
	}

}
