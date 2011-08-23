package org.lastfm.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.MusicBrainzService;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ActionMethod;
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
	private MusicBrainzService service = new MusicBrainzService();

	
	@ActionMethod(Actions.COMPLETE_METADATA)
	public Metadata completeMetadata(Metadata metadata){
		try {
			String album = service.getAlbum(metadata.getArtist(), metadata.getTitle());
			if(StringUtils.isNotEmpty(album)){
				metadata.setAlbum(album);
			}
		} catch (ServerUnavailableException sue) {
			log.error(sue, sue);
		}
		return metadata;
	}
	
}
