package org.lastfm.helper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.lastfm.ApplicationState;

import de.umass.lastfm.Album;

public class LastFMAlbumHelper {

	public Album getAlbum(String artist, String album) {
		return Album.getInfo(artist, album, ApplicationState.KEY);
	}

	public Image readImage(String imageURL) throws MalformedURLException, IOException {
		return ImageIO.read(new URL(imageURL));
	}

	public ImageIcon getImageIcon(Image image) {
		return new ImageIcon(image);
	}

}
