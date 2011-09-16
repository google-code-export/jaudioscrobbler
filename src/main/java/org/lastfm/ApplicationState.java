package org.lastfm;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands define ALL constants on JaudioScrobbler
 */

public interface ApplicationState {
	static final String KEY = "250d02d1a21e78488d79ad1a73e88c72";
	static final String SECRET = "97dc72bff4e218c9cd41501a35f4493d";

	static final String LOGIN_FAIL = "Login fail";
	static final String LOGGED_AS = "Logged as : ";
	static final String DONE = "Done";
	static final String NETWORK_ERROR = "Internet Connection Error";
	static final String OPEN_ERROR = "Error on importing Music";
	static final String NEW_METADATA = "New";
	static final String WORKING = "Working";
	static final String UPDATED = "Updated";
	static final int ALBUM_COLUMN = 2;
	static final int TRACK_NUMBER_COLUMN = 3;
	static final int STATUS_COLUMN = 5;
}
