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

	public ActionResult completeCoverArt(Metadata metadata) {
		try {
			if (completeHelper.canLastFMHelpToComplete(metadata)) {
				LastfmAlbum lastfmAlbum = completeHelper.getCoverArt(metadata);
				if (lastfmAlbum.getImageIcon() == null) {
					return ActionResult.COVER_ART_ERROR;
				}
				metadata.setLastfmCoverArt(lastfmAlbum.getImageIcon());
				return ActionResult.COVER_ART_SUCCESS;
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

	public ActionResult completeYearLastfmMetadata(Metadata metadata) {
		try {
			if (completeHelper.canLastFMHelpToComplete(metadata)) {
				LastfmAlbum lastfmAlbum = completeHelper.getYear(metadata);
				if (StringUtils.isEmpty(lastfmAlbum.getYear())) {
					return ActionResult.YEAR_ERROR;
				}
				metadata.setYear(lastfmAlbum.getYear());
				return ActionResult.YEAR_SUCCESS;
			} else {
				return ActionResult.METADATA_COMPLETE;
			}
		} catch (NullPointerException npe) {
			log.error(npe, npe);
			return ActionResult.COVER_ART_ERROR;
		}
	}

	public ActionResult completeGenreLastfmMetadata(Metadata metadata) {
		try {
			if (completeHelper.canLastFMHelpToComplete(metadata)) {
				LastfmAlbum lastfmAlbum = completeHelper.getGenre(metadata);
				if (StringUtils.isEmpty(lastfmAlbum.getGenre())) {
					return ActionResult.GENRE_ERROR;
				}
				metadata.setGenre(lastfmAlbum.getGenre());
				return ActionResult.GENRE_SUCCESS;
			} else {
				return ActionResult.METADATA_COMPLETE;
			}
		} catch (NullPointerException npe) {
			log.error(npe, npe);
			return ActionResult.COVER_ART_ERROR;
		}
	}
}
