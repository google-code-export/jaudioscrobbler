package org.lastfm.controller.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.helper.CompleteHelper;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.LastfmAlbum;
import org.springframework.stereotype.Service;

@Service
public class LastfmService {
	private CompleteHelper completeHelper = new CompleteHelper();
	private Log log = LogFactory.getLog(this.getClass());

	public synchronized ActionResult completeLastFM(Metadata metadata) {
		try {
			if (completeHelper.canLastFMHelpToComplete(metadata)) {
				LastfmAlbum lastfmAlbum = completeHelper.getLastFM(metadata);
				return completeHelper.isSomethingNew(lastfmAlbum, metadata);
			} else {
				return ActionResult.Complete;
			}
		} catch (MalformedURLException mfe) {
			log.error(mfe, mfe);
			return ActionResult.Error;
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return ActionResult.Error;
		} catch (NullPointerException npe) {
			log.error(npe, npe);
			return ActionResult.Error;
		}
	}
}
