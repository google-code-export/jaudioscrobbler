package org.lastfm.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFormatterHelper {

	private FormatterHelper helper = new FormatterHelper();
	
	@Test
	public void shouldFormatForComparison() throws Exception {
		String word = "de - pazz";
		String expectedWord = "depazz";
		assertEquals(expectedWord, helper.getBasicFormat(word));
	}

}
