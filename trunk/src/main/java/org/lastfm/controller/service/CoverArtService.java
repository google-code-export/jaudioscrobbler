package org.lastfm.controller.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.helper.CompleteHelper;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.springframework.stereotype.Service;

@Service
public class CoverArtService {
	private CompleteHelper completeHelper = new CompleteHelper();
	private Log log = LogFactory.getLog(this.getClass());

	public ActionResult completeLastFM(Metadata metadata) {
		try {
			if (completeHelper.canLastFMHelpToComplete(metadata)) {
				LastfmAlbum lastfmAlbum = completeHelper.getLastFM(metadata);
				if (lastfmAlbum.getImageIcon() != null) {
					metadata.setLastfmCoverArt(lastfmAlbum.getImageIcon());
				}
				if(!StringUtils.isEmpty(lastfmAlbum.getYear())){
					metadata.setYear(lastfmAlbum.getYear());
				}
				if(!StringUtils.isEmpty(lastfmAlbum.getGenre())){
					metadata.setGenre(lastfmAlbum.getGenre());
				}
				return ActionResult.LAST_FM_SUCCESS;
			} else {
				return ActionResult.METADATA_COMPLETE;
			}
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
			return ActionResult.LAST_FM_ERROR;
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return ActionResult.LAST_FM_ERROR;
		} catch (NullPointerException npe) {
			log.error(npe, npe);
			return ActionResult.LAST_FM_ERROR;
		}
	}
}
