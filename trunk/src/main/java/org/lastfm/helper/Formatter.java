package org.lastfm.helper;

import org.lastfm.metadata.Metadata;

public class Formatter {

	public boolean isABadFormat(Metadata metadata) {
		if (metadata.getTitle().contains("&amp;") || metadata.getArtist().contains("&amp;") || metadata.getTitle().contains("`") || metadata.getArtist().contains("`")
				|| metadata.getTitle().contains("&eacute;") || metadata.getArtist().contains("&eacute;") || metadata.getTitle().contains("&aacute;") || metadata.getArtist().contains("&aacute;")
				|| metadata.getTitle().contains("&iacute;") || metadata.getArtist().contains("&iacute;") || metadata.getTitle().contains("&oacute;") || metadata.getArtist().contains("&oacute;")
				|| metadata.getTitle().contains("&uacute;") || metadata.getArtist().contains("&uacute;")) {
			String artist = metadata.getArtist().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú");
			String title = metadata.getTitle().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú");
			metadata.setTitle(title);
			metadata.setArtist(artist);
			return true;
		}
		return false;
	}

}
