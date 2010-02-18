package org.lastfm;


/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class ApplicationState {
	public static String userName;
	public static String password;
	public static int ERROR = -1;
	public static int OK = 0;
	public static int FAILURE = 2;
	
	public static final String LOGIN_FAIL = "Login fail";
	public static final String LOGGED_AS = "Logged as : ";
	public static final String DONE = "Done";
	public static final Object HAND_SHAKE_FAIL = "Handshake failed";
	public static final String NETWORK_ERROR = "Internet Connection Error";
	public static final String OPEN_ERROR = "Error on importing Music";
	public static final String NEW_METADATA = "New Metadata";
	public static final String WORKING = "Working";
	public static final int ALBUM_COLUMN = 2;
	public static final int STATUS_COLUMN = 5;
}
