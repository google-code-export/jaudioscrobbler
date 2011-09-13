package org.lastfm.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.action.Actions;
import org.lastfm.action.control.RequestMethod;
import org.lastfm.helper.MusicBrainzDelegator;
import org.lastfm.metadata.Metadata;
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
	
	@RequestMethod(Actions.COMPLETE_METADATA)
	public ActionResult completeMetadata(Metadata metadata){
		try {
			String album = service.getAlbum(metadata.getArtist(), metadata.getTitle());
			if(StringUtils.isNotEmpty(album)){
				log.info("Album found: " + album + " for track: " + metadata.getTitle());
				metadata.setAlbum(album);
			} else {
				log.info("No album found for track: " + metadata.getTitle());
				return ActionResult.METADATA_NOT_FOUND;
			}
		} catch (ServerUnavailableException sue) {
			log.error(sue, sue);
			return ActionResult.METADATA_ERROR;
		}
		return ActionResult.METADATA_SUCCESS;
	}
	
}
