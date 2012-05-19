package org.lastfm.controller.service;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.metadata.Metadata;
import org.springframework.stereotype.Service;

@Service
public class ExtractService {
	
	private Log log = LogFactory.getLog(this.getClass());

	public Metadata extractFromFileName(Metadata metadata) {
		String titleComplete = metadata.getFile().getName();
		try{
			StringTokenizer stringTokenizer = new StringTokenizer(titleComplete, "-");
			String artist = stringTokenizer.nextToken();
			String title = removeExtension(stringTokenizer.nextToken());
			metadata.setArtist(artist);
			metadata.setTitle(title);
		} catch (NoSuchElementException nue){
			String uniqueName = removeExtension(titleComplete);
			metadata.setArtist(uniqueName);
			metadata.setTitle(uniqueName);
			log.info(titleComplete + " has no slash format");
		}
		metadata.setMetadataFromFile(true);
		return metadata;
	}
	
	private String removeExtension(String name) {
		int extensionIndex = name.lastIndexOf(".");
		return extensionIndex == -1 ? name : name.substring(0, extensionIndex);
	}
}
