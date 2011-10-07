package org.lastfm.helper;

import org.lastfm.ApplicationState;
import org.lastfm.metadata.Metadata;

public class MetadataAdapter {

	public void update(Metadata metadata, int column, String value) {
		if(column == ApplicationState.ARTIST_COLUMN){
			metadata.setArtist(value);
		}
		if (column == ApplicationState.TITLE_COLUMN){
			metadata.setArtist(value);
		}
		if (column == ApplicationState.ALBUM_COLUMN){
			metadata.setAlbum(value);
		}
		if (column == ApplicationState.TRACK_NUMBER_COLUMN){
			metadata.setTrackNumber(Integer.valueOf(value));
		}
		if (column == ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN){
			metadata.setTotalTracksNumber(Integer.valueOf(value));
		}
	}

}
