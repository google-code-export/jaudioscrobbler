package org.lastfm.helper;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataReader;
import org.lastfm.metadata.Mp3Reader;
import org.lastfm.metadata.Mp4Reader;
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

	public Set<File> createHashSet() {
		return new HashSet<File>();
	}

	public MetadataReader createMp3Reader() {
		return new Mp3Reader();
	}

	public Mp4Reader createMp4Reader() {
		return new Mp4Reader();
	}

}
