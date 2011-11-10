package org.lastfm;

import java.io.File;


/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands define ALL constants on JaudioScrobbler
 */

public interface ApplicationState {
	static final String DEFAULT_IMAGE = "src/main/resources/images/daft_punk.png";
	static String DESTFILE_PATH = System.getProperty("java.io.tmpdir") + File.separator;

	static final String LOGIN_FAIL = "Login fail";
	static final String LOGGED_AS = "Logged as : ";
	static final String DONE = "Done";
	static final String NETWORK_ERROR = "Internet Connection Error";
	static final String OPEN_ERROR = "Error on importing Music";
	static final String WORKING = "Working";
	static final int ARTIST_COLUMN = 0;
	static final int TITLE_COLUMN = 1;
	static final int ALBUM_COLUMN = 2;
	static final int GENRE_COLUMN = 3;
	static final int YEAR_COLUMN = 4;
	static final int TRACK_NUMBER_COLUMN = 5;
	static final int TOTAL_TRACKS_NUMBER_COLUMN = 6;
	static final int CD_NUMBER_COLUMN = 7;
	static final int TOTAL_CDS_NUMBER_COLUMN = 8;
	static final int STATUS_COLUMN = 9;
	static final int WIDTH = 1024;
	static final int HEIGHT = 600;
	static final String COVER_ART_FROM_FILE = "Cover Art from File";
	static final String COVER_ART_FROM_LASTFM = "Cover Art from Lastfm";
	static final String COVER_ART_DEFAULT = "Covert Art not found";
	static final String APPLY = "Apply";
	static final Object READY = "Ready";
	static final String APPLICATION_NAME = "JAudioScrobbler";
	static final String IMAGE_EXT = "PNG";
	static final String PREFIX = "JAS_";
	static final String GETTING_ALBUM = "Getting Album from Musicbrainz";
	static final String GETTING_LAST_FM = "Getting Last.fm Metadata";
	static final String WRITTING_METADATA = "Writting Metadata";
	static final String USERNAME_LABEL = "username:";
	static final String PASSWORD_LABEL = "password:";
}
