package org.lastfm.controller;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import org.lastfm.helper.LastFMAlbumHelper;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

public class LastfmController {
	private LastFMAlbumHelper helper = new LastFMAlbumHelper();
	
	public ImageIcon getCoverArt(String artist, String album) throws MalformedURLException, IOException{
		Album info = helper.getAlbum(artist, album);
		String imageURL = info.getImageURL(ImageSize.EXTRALARGE);
		Image image = helper.readImage(imageURL);
		return helper.getImageIcon(image);
	}
}
 