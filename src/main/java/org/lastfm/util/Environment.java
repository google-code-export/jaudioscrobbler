package org.lastfm.util;

public class Environment {
	
	public static boolean isLinux(){
		return System.getProperty("os.name").toLowerCase().contains("linux");
	}
	
}
