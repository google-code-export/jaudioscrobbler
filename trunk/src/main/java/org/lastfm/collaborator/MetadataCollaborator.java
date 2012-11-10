package org.lastfm.collaborator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lastfm.model.Metadata;

public class MetadataCollaborator {

	private List<Metadata> metadatas;

	public void setMetadatas(List<Metadata> metadatas) {
		this.metadatas = metadatas;
	}

	public String getArtist() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (!metadatas.get(i).getArtist().equals(metadatas.get(i-1).getArtist())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getArtist() : StringUtils.EMPTY;
	}

	public String getAlbum() {
		boolean isTheSame = true;
		for (int i=1; i< metadatas.size(); i++) {
			if (!metadatas.get(i).getAlbum().equals(metadatas.get(i-1).getAlbum())){
				isTheSame = false;
			}
		}
		return isTheSame ? metadatas.get(0).getAlbum() : StringUtils.EMPTY;
	}

}
