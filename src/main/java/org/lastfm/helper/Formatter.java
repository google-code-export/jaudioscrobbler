package org.lastfm.helper;

import org.lastfm.metadata.Metadata;

public class Formatter {

	public boolean isABadFormat(Metadata metadata) {
		if (metadata.getTitle().contains("&amp;") || metadata.getArtist().contains("&amp;") || metadata.getAlbum().contains("&amp;") || metadata.getTitle().contains("`") || metadata.getArtist().contains("`") || metadata.getAlbum().contains("`")
				|| metadata.getTitle().contains("&eacute;") || metadata.getArtist().contains("&eacute;") || metadata.getAlbum().contains("&eacute;") || metadata.getTitle().contains("&aacute;") || metadata.getArtist().contains("&aacute;") || metadata.getAlbum().contains("&aacute;")
				|| metadata.getTitle().contains("&iacute;") || metadata.getArtist().contains("&iacute;") || metadata.getAlbum().contains("&iacute;") || metadata.getTitle().contains("&oacute;") || metadata.getArtist().contains("&oacute;") || metadata.getAlbum().contains("&oacute;")
				|| metadata.getTitle().contains("&uacute;") || metadata.getArtist().contains("&uacute;") || metadata.getAlbum().contains("&uacute;")) {
			String artist = metadata.getArtist().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú");
			String title = metadata.getTitle().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
					.replace("&uacute;", "ú");
			String album = metadata.getAlbum().replace("&amp;", "&").replace("`", "'").replace("&eacute;", "é").replace("&aacute;", "á").replace("&iacute;", "í").replace("&oacute;", "ó")
			.replace("&uacute;", "ú");
			metadata.setTitle(title);
			metadata.setArtist(artist);
			metadata.setAlbum(album);
			return true;
		}
		return false;
	}

}
