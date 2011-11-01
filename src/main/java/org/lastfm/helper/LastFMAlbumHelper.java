package org.lastfm.helper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.lastfm.Auth;

import de.umass.lastfm.Album;

public class LastFMAlbumHelper {

	public Album getAlbum(String artist, String album) {
		return Album.getInfo(artist, album, Auth.KEY);
	}

	public Image readImage(String imageURL) throws MalformedURLException, IOException {
		return ImageIO.read(new URL(imageURL));
	}

	public ImageIcon getImageIcon(Image image) {
		return new ImageIcon(image);
	}

	public String getYear(Date releaseDate) {
		String dateAsString = releaseDate.toString();
		return dateAsString.substring(dateAsString.length()-4, dateAsString.length());
	}

}
