package org.lastfm.helper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.metadata.Metadata;
import org.springframework.stereotype.Service;

@Service
public class MetadataHelper {

	private Log log = LogFactory.getLog(getClass());

	public boolean isSameAlbum(List<Metadata> metadatas) {
		for(int i = 0 ; i < metadatas.size() - 1  ; i++){
			if(!metadatas.get(i).getAlbum().equals(metadatas.get(i+1).getAlbum())){
				return false;
			}
		}
		return true;
	}

	public Metadata extractFromFileName(Metadata metadata) {
		String titleComplete = metadata.getFile().getName();
		try{
			StringTokenizer stringTokenizer = new StringTokenizer(titleComplete, "-");
			String artist = stringTokenizer.nextToken();
			String title = stringTokenizer.nextToken();
			metadata.setArtist(artist);
			metadata.setTitle(title.substring(0, title.length() - 4));
		} catch (NoSuchElementException nue){
			String uniqueName = titleComplete.substring(0, titleComplete.length() - 4);
			metadata.setTitle(uniqueName);
			metadata.setArtist(uniqueName);
			log.info(titleComplete + " has no slash format");
		}
		metadata.setMetadataFromFile(true);
		return metadata;
	}

}
