package org.lastfm.helper;

import org.apache.commons.lang3.StringUtils;

public class FormatterHelper {

	public String getBasicFormat(String word) {
		String formatted = word.replace("-", StringUtils.EMPTY);
		return formatted.replace(" ", StringUtils.EMPTY);
	}

}
