package org.lastfm;

import java.io.File;


/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands define ALL constants on JaudioScrobbler
 */

public interface ApplicationState {
	static final String DEFAULT_IMAGE = "http://userserve-ak.last.fm/serve/300x300/66072700.png";
	static String DESTFILE_PATH = System.getProperty("java.io.tmpdir") + File.separator;

	static final String LOGIN_FAIL = "Login fail";
	static final String LOGGED_AS = "Logged as : ";
	static final String DONE = "Done";
	static final String NETWORK_ERROR = "Internet Connection Error";
	static final String OPEN_ERROR = "Error on importing Music";
	static final String NEW_METADATA = "New";
	static final String WORKING = "Working";
	static final String UPDATED = "Updated";
	static final String ERROR = "Error";
	static final int ALBUM_COLUMN = 2;
	static final int TRACK_NUMBER_COLUMN = 3;
	static final int TOTAL_TRACKS_NUMBER_COLUMN = 4;
	static final int STATUS_COLUMN = 5;
	static final String SENT = "Sent";
	static final String SESSIONLESS = "SessionLess";
	static final String LOGGED_OUT = "NotLogged";
	static final int WIDTH = 1024;
	static final int HEIGHT = 550;
	static final String COVER_ART_FROM_FILE = "Cover Art from File";
	static final String COVER_ART_FROM_LASTFM = "Cover Art from Lastfm";
	static final String COVER_ART_DEFAULT = "Covert Art not found";
	static final String APPLY = "Apply";
	static final String APPLICATION_NAME = "JAudioScrobbler";
	static final String IMAGE_EXT = "PNG";
	static final String PREFIX = "JAS_";
}
