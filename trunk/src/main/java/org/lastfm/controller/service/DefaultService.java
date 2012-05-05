package org.lastfm.controller.service;

import java.util.List;

import org.lastfm.metadata.Metadata;
import org.springframework.stereotype.Service;

@Service
public class DefaultService {

	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";
	
	public void complete(List<Metadata> metadatas) {
		for (Metadata metadata : metadatas) {
			metadata.setTotalTracks(String.valueOf(getTotalTracks(metadatas)));
			metadata.setCdNumber(CD_NUMBER);
			metadata.setTotalCds(TOTAL_CD_NUMBER);
		}
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
