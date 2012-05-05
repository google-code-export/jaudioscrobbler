package org.lastfm.controller.service;

import java.util.List;

import org.lastfm.helper.MetadataHelper;
import org.lastfm.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultService {

	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";
	
	@Autowired
	private MetadataHelper metadataHelper;
	
	public Boolean isCompletable(List<Metadata> metadatas) {
		if(metadatas.size() < 2 || !metadataHelper.isSameAlbum(metadatas)){
			return false;
		}
		for (Metadata metadata : metadatas) {
			metadata.setTotalTracks(String.valueOf(getTotalTracks(metadatas)));
			metadata.setCdNumber(CD_NUMBER);
			metadata.setTotalCds(TOTAL_CD_NUMBER);
		}
		return true;
	}

	private int getTotalTracks(List<Metadata> metadatas) {
		int biggerTrackNumber = 0;
		for (Metadata metadata : metadatas) {
			Integer metadataTrackNumber = Integer.valueOf(metadata.getTrackNumber());
			if(metadataTrackNumber > biggerTrackNumber){
				biggerTrackNumber = metadataTrackNumber;
			}
		}
		return biggerTrackNumber;
	}

}
