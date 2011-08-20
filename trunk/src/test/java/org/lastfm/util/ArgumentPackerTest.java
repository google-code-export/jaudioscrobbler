package org.lastfm.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lastfm.ApplicationState;


public class ArgumentPackerTest {
	ArgumentPacker argumentPacker = new ArgumentPacker();
	
	@Test
	public void shouldPack() throws Exception {
		String arg1 = "arg1";
		String arg2 = "arg2";
		String expected = arg1 + ApplicationState.DELIMITER + arg2;
		
		assertEquals(expected, argumentPacker.pack(arg1, arg2));
	}
}
