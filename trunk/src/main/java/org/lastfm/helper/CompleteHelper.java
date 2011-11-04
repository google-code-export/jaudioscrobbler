package org.lastfm.helper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

public class CompleteHelper {
	private LastFMAlbumHelper helper = new LastFMAlbumHelper();
	private Log log = LogFactory.getLog(this.getClass());
	private Album info;

	public boolean canLastFMHelpToComplete(Metadata metadata) {
		String artist = metadata.getArtist();
		String album = metadata.getAlbum();

		if ((metadata.getCoverArt() == null || StringUtils.isEmpty(metadata.getYear()) || StringUtils.isEmpty(metadata.getGenre())) && (!StringUtils.isEmpty(album) && !StringUtils.isEmpty(artist))) {
			info = helper.getAlbum(artist, album);
			return info == null ? false : true;
		}
		return false;
	}

	public LastfmAlbum getCoverArt(Metadata metadata) throws MalformedURLException, IOException {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		String imageURL = info.getImageURL(ImageSize.EXTRALARGE);
		log.info("imageURL: " + imageURL + " from album: " + info.getName());
		if (StringUtils.isEmpty(imageURL)) {
			return null;
		}
		Image image = helper.readImage(imageURL);
		lastfmAlbum.setImageIcon(helper.getImageIcon(image));
		return lastfmAlbum;
	}

	public LastfmAlbum getYear(Metadata metadata) {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		log.info("Year date format: " + info.getReleaseDate());
		lastfmAlbum.setYear(helper.getYear(info.getReleaseDate()));
		log.info("Year metadata format: " + lastfmAlbum.getYear());
		return lastfmAlbum;
	}

	public LastfmAlbum getGenre(Metadata metadata) {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		lastfmAlbum.setGenre(helper.getGenre(info));
		return lastfmAlbum;
	}

}
