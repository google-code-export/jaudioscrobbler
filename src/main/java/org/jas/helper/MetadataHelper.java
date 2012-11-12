package org.jas.helper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.jas.metadata.MetadataReader;
import org.jas.metadata.Mp3Reader;
import org.jas.metadata.Mp4Reader;
import org.jas.model.MetadataAlbumValues;
import org.springframework.stereotype.Service;

@Service
public class MetadataHelper {

	public Set<File> createHashSet() {
		return new HashSet<File>();
	}

	public MetadataReader createMp3Reader() {
		return new Mp3Reader();
	}

	public Mp4Reader createMp4Reader() {
		return new Mp4Reader();
	}

	public MetadataAlbumValues createMetadataAlbumVaues() {
		return new MetadataAlbumValues();
	}

}
