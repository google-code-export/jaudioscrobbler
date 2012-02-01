package org.lastfm.helper;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
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

	public boolean isNotCamelized(Metadata metadata) {
		boolean camelized = false;
		String artist = metadata.getArtist();
		String title = metadata.getTitle();
		String album = metadata.getAlbum();
		if(StringUtils.isAllLowerCase(title.replace(" ", StringUtils.EMPTY)) || StringUtils.isAllUpperCase(title.replace(" ", StringUtils.EMPTY))){
			metadata.setTitle(toCamelCase(title));
			camelized =  true;
		}
		if(StringUtils.isAllLowerCase(artist.replace(" ", StringUtils.EMPTY)) || StringUtils.isAllUpperCase(artist.replace(" ", StringUtils.EMPTY))){
			metadata.setArtist(toCamelCase(artist));
			camelized =  true;
		}
		if(StringUtils.isAllLowerCase(album.replace(" ", StringUtils.EMPTY)) || StringUtils.isAllUpperCase(album.replace(" ", StringUtils.EMPTY))){
			metadata.setAlbum(toCamelCase(album));
			camelized =  true;
		}
		return camelized == true ? true : false;
	}
	
	private String toCamelCase(String value) {
		StringTokenizer stringTokenizer = new StringTokenizer(value.toLowerCase());
		StringBuilder stringBuilder = new StringBuilder();
		while(stringTokenizer.hasMoreTokens()){
			stringBuilder.append(StringUtils.capitalize(stringTokenizer.nextToken()));
			stringBuilder.append(" ");
		}
		String[] strings = StringUtils.split(stringBuilder.toString(), " ");
		return StringUtils.join(strings, " ");
	}

}
