package org.lastfm.controller.service;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.helper.LastFMAlbumHelper;
import org.lastfm.metadata.Metadata;
import org.springframework.stereotype.Service;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

@Service
public class CoverArtService {
	private LastFMAlbumHelper helper = new LastFMAlbumHelper();
	private Log log = LogFactory.getLog(this.getClass());
	
	public ImageIcon getCoverArt(String artist, String album) throws MalformedURLException, IOException{
		Album info = helper.getAlbum(artist, album);
		String imageURL = info.getImageURL(ImageSize.EXTRALARGE);
		Image image = helper.readImage(imageURL);
		return helper.getImageIcon(image);
	}

	public ActionResult completeCoverArt(Metadata metadata) {
		try{
			if(metadata.getCoverArt() == null){
				if(!StringUtils.isEmpty(metadata.getAlbum()) && !StringUtils.isEmpty(metadata.getArtist())){
					log.info("Getting Cover Art for Album: " + metadata.getAlbum() + " by " + metadata.getArtist());
					ImageIcon coverArt = getCoverArt(metadata.getArtist(), metadata.getAlbum());
					metadata.setLastfmCoverArt(coverArt);
				}
			}
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
			return ActionResult.COVER_ART_ERROR;
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return ActionResult.COVER_ART_ERROR;
		}
		return ActionResult.COVER_ART_SUCCESS;
	}
}
 