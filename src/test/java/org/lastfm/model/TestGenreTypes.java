package org.lastfm.model;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


public class TestGenreTypes {
	
	@Test
	public void shouldGetBluesAsGenre() throws Exception {
		assertEquals("House", GenreTypes.getGenreByCode(35));
	}
	
	@Test
	public void shouldReturnEmptyStringIfNoGenre() throws Exception {
		assertEquals(StringUtils.EMPTY, GenreTypes.getGenreByCode(150));
	}
	
}
