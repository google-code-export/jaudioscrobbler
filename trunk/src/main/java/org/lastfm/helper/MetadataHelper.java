package org.lastfm.helper;

import java.util.List;

import org.lastfm.metadata.Metadata;
import org.springframework.stereotype.Service;

@Service
public class MetadataHelper {

	public boolean isSameAlbum(List<Metadata> metadatas) {
		for(int i = 0 ; i < metadatas.size() - 1  ; i++){
			if(!metadatas.get(i).getAlbum().equals(metadatas.get(i+1).getAlbum())){
				return false;
			}
		}
		return true;
	}

}
