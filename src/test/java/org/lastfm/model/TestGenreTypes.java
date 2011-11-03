package org.lastfm.model;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


public class TestGenreTypes {
	
	@Test
	public void shouldGetBluesAsGenre() throws Exception {
		int houseCode = 35;
		assertEquals("House", GenreTypes.getGenreByCode(houseCode));
	}
	
	@Test
	public void shouldReturnEmptyStringIfNoGenre() throws Exception {
		int unknownCode = 150;
		assertEquals(StringUtils.EMPTY, GenreTypes.getGenreByCode(unknownCode));
	}
	
	@Test
	public void shouldGetGenreByCode() throws Exception {
		int genreAsCode = 18;
		String genre = "Techno";
		
		String result = GenreTypes.getGenreByCode(genreAsCode);
		assertEquals(genre, result);
	}
	
	@Test
	public void shouldGetCodeByGenre() throws Exception {
		int genreAsCode = 18;
		String genre = "Techno";
		
		GenreTypes genreType = GenreTypes.getGenreByName(genre);
		assertEquals(genreAsCode, genreType.getCode());
	}
	
	@Test
	public void shouldGetUnknownCodeByGenre() throws Exception {
		int genreAsCode = 148;
		String genre = "Minimal Techno";
		
		GenreTypes genreType = GenreTypes.getGenreByName(genre);
		assertEquals(genreAsCode, genreType.getCode());
	}
}
