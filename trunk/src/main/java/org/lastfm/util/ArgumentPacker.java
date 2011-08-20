package org.lastfm.util;

import org.lastfm.ApplicationState;

public class ArgumentPacker {

	public String pack(String arg1, String arg2) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(arg1);
		stringBuilder.append(ApplicationState.DELIMITER);
		stringBuilder.append(arg2);
		return stringBuilder.toString();
	}
	
}
