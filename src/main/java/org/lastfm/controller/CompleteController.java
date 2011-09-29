package org.lastfm.controller;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.action.Actions;
import org.lastfm.action.control.RequestMethod;
import org.lastfm.controller.service.CoverArtService;
import org.lastfm.helper.MusicBrainzDelegator;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataException;
import org.lastfm.metadata.MetadataWriter;
import org.lastfm.model.MusicBrainzTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who control the MusicBrainz service
 */

@Controller
public class CompleteController {
	private Log log = LogFactory.getLog(this.getClass());
	private MusicBrainzDelegator service = new MusicBrainzDelegator();
	private MetadataWriter metadataWriter = new MetadataWriter();
	
	@Autowired
	private CoverArtService coverArtService;

	@RequestMethod(Actions.COMPLETE_METADATA)
	public ActionResult completeMetadata(Metadata metadata) {
		try {
			log.info(metadata.getArtist() + " / " + metadata.getTitle() + " album: " + metadata.getAlbum());
			if (StringUtils.isEmpty(metadata.getAlbum())) {
				MusicBrainzTrack musicBrainzTrack = service.getAlbum(metadata.getArtist(), metadata.getTitle());
				if (StringUtils.isNotEmpty(musicBrainzTrack.getAlbum())) {
					log.info("Album found: " + musicBrainzTrack.getAlbum() + " for track: " + metadata.getTitle());
					metadata.setAlbum(musicBrainzTrack.getAlbum());
					metadata.setTrackNumber(musicBrainzTrack.getTrackNumber());
					metadata.setTotalTracksNumber(musicBrainzTrack.getTotalTrackNumber());
					coverArtService.completeCoverArt(metadata);
					return ActionResult.METADATA_SUCCESS;
				} else {
					ActionResult completeCoverArt = coverArtService.completeCoverArt(metadata);
					if(completeCoverArt.equals(ActionResult.COVER_ART_SUCCESS)){
						return ActionResult.METADATA_SUCCESS;
					}
					log.info("No album found for track: " + metadata.getTitle());
					return ActionResult.METADATA_NOT_FOUND;
				}
			} else {
				ActionResult completeCoverArt = coverArtService.completeCoverArt(metadata);
				if(completeCoverArt.equals(ActionResult.COVER_ART_SUCCESS)){
					return ActionResult.METADATA_SUCCESS;
				} else {
					return ActionResult.METADATA_COMPLETE;
				}
			}
		} catch (ServerUnavailableException sue) {
			log.error(sue, sue);
			ActionResult completeCoverArt = coverArtService.completeCoverArt(metadata);
			if(completeCoverArt.equals(ActionResult.COVER_ART_SUCCESS)){
				return ActionResult.METADATA_SUCCESS;
			}
			return ActionResult.METADATA_ERROR;
		} 
	}

	@RequestMethod(Actions.COMPLETE_ALBUM_METADATA)
	public synchronized ActionResult completeAlbum(Metadata metadata) {
		try {
			File file = metadata.getFile();
			metadataWriter.setFile(file);
			metadataWriter.writeAlbum(metadata.getAlbum());
			Integer trackNumber = metadata.getTrackNumber();
			metadataWriter.writeTrackNumber(trackNumber.toString());
			Integer totalTracksNumber = metadata.getTotalTracksNumber();
			metadataWriter.writeTotalTracksNumber(totalTracksNumber.toString());
			if(metadata.getCoverArt() == null && metadata.getLastfmCoverArt() != null){
				metadataWriter.writeCoverArt(metadata.getLastfmCoverArt());
			}
			return ActionResult.UPDATED;
		} catch (MetadataException mde) {
			log.error(mde, mde);
			return ActionResult.WRITE_METADATA_ERROR;
		}
	}

}
