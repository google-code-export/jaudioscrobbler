package org.lastfm.model;

import static org.lastfm.model.ModelType.readOnly;

import java.util.List;

import org.lastfm.metadata.Metadata;

public interface Model {
	
	String CURRENT_USER_ID = "application.currentUser";
	ModelType<User> CURRENT_USER = readOnly(CURRENT_USER_ID);
	
	String METADATA_LIST = "application.metadataList";
	ModelType<List<Metadata>> METADATA = readOnly(METADATA_LIST);
	
	String METADATA_ARTIST_LIST = "application.metadataArtistList";
	ModelType<List<Metadata>> METADATA_ARTIST = readOnly(METADATA_ARTIST_LIST);

}
