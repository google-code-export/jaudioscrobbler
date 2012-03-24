package org.lastfm.model;

import java.io.File;
import java.util.List;

import org.lastfm.metadata.Metadata;

public class ExportPackage {
	private final List<Metadata> metadataList;
	private final File root;

	public ExportPackage(File root, List<Metadata> metadataList) {
		this.root = root;
		this.metadataList = metadataList;
	}
	
	public File getRoot() {
		return root;
	}

	public List<Metadata> getMetadataList() {
		return metadataList;
	}
	
}
