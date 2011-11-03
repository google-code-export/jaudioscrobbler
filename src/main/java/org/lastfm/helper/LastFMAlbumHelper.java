package org.lastfm.helper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.Auth;
import org.lastfm.model.GenreTypes;

import de.umass.lastfm.Album;

public class LastFMAlbumHelper {
	private Log log = LogFactory.getLog(this.getClass());

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

	public String getGenre(Album info) {
		Collection<String> tags = info.getTags();
		Iterator<String> iterator = tags.iterator();
		while(iterator.hasNext()){
			String lastFmTag = (String) iterator.next().toLowerCase();
			log.info("lastFmTag: " + lastFmTag);
			if(GenreTypes.getGenreByName(lastFmTag) != GenreTypes.UNKNOWN){
				log.info("lastFmTag matched in GenreTypes: " + GenreTypes.getGenreByName(lastFmTag).getName());
				return GenreTypes.getGenreByName(lastFmTag).getName();
			}
		}
		return StringUtils.EMPTY;
	}

}
