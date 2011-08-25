package org.lastfm;

import java.util.List;

import org.lastfm.metadata.Metadata;


/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands define ALL constants on JaudioScrobbler
 */

public class ApplicationState {
	public static int ERROR = -1;
	public static int OK = 0;
	public static int FAILURE = 2;

	public static List<Metadata> metadataList;
	public static String username;
	public static String password;
	
	public static final String LOGIN_FAIL = "Login fail";
	public static final String LOGGED_AS = "Logged as : ";
	public static final String DONE = "Done";
	public static final String HAND_SHAKE_FAIL = "Handshake failed";
	public static final String NETWORK_ERROR = "Internet Connection Error";
	public static final String OPEN_ERROR = "Error on importing Music";
	public static final String NEW_METADATA = "New";
	public static final String METADATA_UPDATED = "Updated";
	public static final String WORKING = "Working";
	public static final int ALBUM_COLUMN = 2;
	public static final int TRACK_NUMBER_COLUMN = 3;
	public static final int STATUS_COLUMN = 5;
	public static final int LOGGED_OUT = 0;
	
	public static final String CLIENT_SCROBBLER_ID = "tst"; 
	public static final String CLIENT_SCROBBLER_VERSION = "1.0"; 
	public static final String DELIMITER = "|";
}
