package org.lastfm.controller.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lastfm.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultService {

	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";
	
	@Autowired
	private MetadataService metadataService;
	
	public Boolean isCompletable(List<Metadata> metadatas) {
		return (metadatas.size() < 2 || !metadataService.isSameAlbum(metadatas)) ? false : isSomethingMissing(metadatas);  
	}
	
	public void complete(List<Metadata> metadatas){
		for (Metadata metadata : metadatas) {
			metadata.setTotalTracks(String.valueOf(getTotalTracks(metadatas)));
			metadata.setCdNumber(CD_NUMBER);
			metadata.setTotalCds(TOTAL_CD_NUMBER);
		}
	}

	private boolean isSomethingMissing(List<Metadata> metadatas) {
		for (Metadata metadata : metadatas) {
			if(StringUtils.isEmpty(metadata.getTotalTracks()) || StringUtils.isEmpty(metadata.getCdNumber()) || StringUtils.isEmpty(metadata.getTotalCds())){
				return true;
			}
		}
		return false;
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
