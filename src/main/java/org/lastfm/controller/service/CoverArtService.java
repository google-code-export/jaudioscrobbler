package org.lastfm.controller.service;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.helper.LastFMAlbumHelper;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.springframework.stereotype.Service;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

@Service
public class CoverArtService {
	private LastFMAlbumHelper helper = new LastFMAlbumHelper();
	private Log log = LogFactory.getLog(this.getClass());
	
	public LastfmAlbum getCoverArtFromLastfm(String artist, String album) throws MalformedURLException, IOException{
		Album info = helper.getAlbum(artist, album);
		if(info != null){
			LastfmAlbum lastfmAlbum = new LastfmAlbum();
			String imageURL = info.getImageURL(ImageSize.EXTRALARGE);
			log.info("imageURL: " + imageURL + " from album: " + info.getName());
			if(StringUtils.isEmpty(imageURL)){
				return null;
			}
			Image image = helper.readImage(imageURL);
			lastfmAlbum.setImageIcon(helper.getImageIcon(image));
			return lastfmAlbum;
		} else {
			return null;
		}
	}

	public ActionResult completeCoverArt(Metadata metadata) {
		try{
			if(metadata.getCoverArt() == null){
				if(!StringUtils.isEmpty(metadata.getAlbum()) && !StringUtils.isEmpty(metadata.getArtist())){
					log.info("Getting Cover Art for Album: " + metadata.getAlbum() + " by " + metadata.getArtist());
					LastfmAlbum lastfmAlbum = getCoverArtFromLastfm(metadata.getArtist(), metadata.getAlbum());
					if(lastfmAlbum.getImageIcon() == null){
						return ActionResult.COVER_ART_ERROR;
					}
					metadata.setLastfmCoverArt(lastfmAlbum.getImageIcon());
					return ActionResult.COVER_ART_SUCCESS;
				} else {
					return ActionResult.NOT_ENOUGH_ARGUMENTS;
				}
			} else {
				return ActionResult.METADATA_COMPLETE;
			}
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
			return ActionResult.COVER_ART_ERROR;
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return ActionResult.COVER_ART_ERROR;
		} catch (NullPointerException npe) {
			log.error(npe, npe);
			return ActionResult.COVER_ART_ERROR;
		}
	}
	
	public LastfmAlbum getYearFromLastfm(String artist, String album) throws MalformedURLException, IOException{
		Album info = helper.getAlbum(artist, album);
		if(info != null){
			LastfmAlbum lastfmAlbum = new LastfmAlbum();
			log.info("Year date format: " + info.getReleaseDate());
			lastfmAlbum.setYear(helper.getYear(info.getReleaseDate()));
			log.info("Year metadata format: " + lastfmAlbum.getYear());
			return lastfmAlbum;
		} else {
			return null;
		}
	}

	public ActionResult completeLastfmMetadata(Metadata metadata) {
		try{
			if(StringUtils.isEmpty(metadata.getYear())){
				if(!StringUtils.isEmpty(metadata.getAlbum()) && !StringUtils.isEmpty(metadata.getArtist())){
					log.info("Getting Year for Album: " + metadata.getAlbum() + " by " + metadata.getArtist());
					LastfmAlbum lastfmAlbum = getYearFromLastfm(metadata.getArtist(), metadata.getAlbum());
					if(StringUtils.isEmpty(lastfmAlbum.getYear())){
						return ActionResult.COVER_ART_ERROR;
					}
					metadata.setYear(lastfmAlbum.getYear());
					return ActionResult.YEAR_SUCCESS;
				} else {
					return ActionResult.NOT_ENOUGH_ARGUMENTS;
				}
			} else {
				return ActionResult.METADATA_COMPLETE;
			}
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
			return ActionResult.COVER_ART_ERROR;
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return ActionResult.COVER_ART_ERROR;
		} catch (NullPointerException npe) {
			log.error(npe, npe);
			return ActionResult.COVER_ART_ERROR;
		}
	}
}
 