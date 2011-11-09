package org.lastfm.helper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

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

		if (isMetadataIncomplete(metadata) && hasAlbumAndArtist(artist, album)) {
			info = helper.getAlbum(artist, album);
			return info == null ? false : true;
		}
		return false;
	}

	private boolean hasAlbumAndArtist(String artist, String album) {
		return (!StringUtils.isEmpty(album) && !StringUtils.isEmpty(artist));
	}

	private boolean isMetadataIncomplete(Metadata metadata) {
		return (metadata.getCoverArt() == null || StringUtils.isEmpty(metadata.getYear()) || StringUtils.isEmpty(metadata.getGenre()));
	}

	public LastfmAlbum getLastFM(Metadata metadata) throws MalformedURLException, IOException {
		LastfmAlbum lastfmAlbum = new LastfmAlbum();
		setCoverArt(metadata, lastfmAlbum);
		setYear(lastfmAlbum);
		setGenre(lastfmAlbum);
		return lastfmAlbum;
	}

	private void setCoverArt(Metadata metadata, LastfmAlbum lastfmAlbum) throws MalformedURLException, IOException {
		if(metadata.getCoverArt() != null){
			return;
		}
		String imageURL = info.getImageURL(ImageSize.EXTRALARGE);
		log.info("imageURL: " + imageURL + " from album: " + info.getName());
		if (!StringUtils.isEmpty(imageURL)) {
			Image image = helper.readImage(imageURL);
			lastfmAlbum.setImageIcon(helper.getImageIcon(image));
		}
	}

	private void setGenre(LastfmAlbum lastfmAlbum) {
		String genre = helper.getGenre(info);
		log.info("Genre from lastFM: " + genre);
		lastfmAlbum.setGenre(genre);
	}

	private void setYear(LastfmAlbum lastfmAlbum) {
		Date release = info.getReleaseDate();
		log.info("Year date format: " + release);
		lastfmAlbum.setYear(helper.getYear(release));
		log.info("Year metadata format: " + lastfmAlbum.getYear());
	}

	public boolean completeMetadata(LastfmAlbum lastfmAlbum, Metadata metadata) {
		if(lastfmAlbum.getImageIcon() == null && StringUtils.isEmpty(lastfmAlbum.getYear()) && StringUtils.isEmpty(lastfmAlbum.getGenre())){
			return false;
		}
		metadata.setLastfmCoverArt(lastfmAlbum.getImageIcon());
		if(StringUtils.isEmpty(metadata.getYear())){
			metadata.setYear(lastfmAlbum.getYear());
		}
		if(StringUtils.isEmpty(metadata.getGenre())){
			metadata.setGenre(lastfmAlbum.getGenre());
		}
		return true;
	}

}
