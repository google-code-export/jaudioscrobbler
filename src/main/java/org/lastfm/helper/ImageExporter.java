package org.lastfm.helper;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lastfm.metadata.Metadata;
import org.lastfm.util.ImageUtils;

public class ImageExporter {
	private ImageUtils imageUtils = new ImageUtils();

	public void export(List<Metadata> metadatas) throws IOException {
		if (isSameAlbum(metadatas)){
			imageUtils.saveCoverArtToFile(metadatas.get(0).getCoverArt(), StringUtils.EMPTY);
		} else { 
			for (Metadata metadata : metadatas) {
				imageUtils.saveCoverArtToFile(metadata.getCoverArt(), metadata.getArtist() + "-" + metadata.getTitle());
			}
		}
	}
	
	private boolean isSameAlbum(List<Metadata> metadatas) {
		for(int i = 0 ; i < metadatas.size() - 1  ; i++){
			if(!metadatas.get(i).getAlbum().equals(metadatas.get(i+1).getAlbum())){
				return false;
			}
		}
		return true;
	}
}
