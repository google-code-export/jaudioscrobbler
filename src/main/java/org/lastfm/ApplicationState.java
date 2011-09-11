package org.lastfm;



/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands define ALL constants on JaudioScrobbler
 */

public interface ApplicationState {
	static int ERROR = -1;
	static int OK = 0;
	static int FAILURE = 2;
	
	static final String KEY = "250d02d1a21e78488d79ad1a73e88c72";
	static final String SECRET = "97dc72bff4e218c9cd41501a35f4493d";

	static final String LOGIN_FAIL = "Login fail";
	static final String LOGGED_AS = "Logged as : ";
	static final String DONE = "Done";
	static final String NETWORK_ERROR = "Internet Connection Error";
	static final String OPEN_ERROR = "Error on importing Music";
	static final String NEW_METADATA = "New";
	static final String METADATA_UPDATED = "Updated";
	static final String WORKING = "Working";
	static final int ALBUM_COLUMN = 2;
	static final int TRACK_NUMBER_COLUMN = 3;
	static final int STATUS_COLUMN = 5;
	static final int LOGGED_OUT = 0;
	
	static final String CLIENT_SCROBBLER_ID = "tst"; 
	static final String CLIENT_SCROBBLER_VERSION = "1.0"; 
	static final String DELIMITER = "|";
}
