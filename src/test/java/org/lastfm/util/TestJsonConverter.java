package org.lastfm.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lastfm.model.AlbumResponse;


public class TestJsonConverter {
	
	@Test
	public void shouldConvertAlbum() throws Exception {
		AlbumResponse album = createAlbum();

		String json = JsonConverter.toJson(album);
		System.err.println("json: " + json);
		AlbumResponse result = (AlbumResponse) JsonConverter.toBean(json, AlbumResponse.class);
		assertNotNull(result);
		compareAlbum(album, result);
	}
	
	private AlbumResponse createAlbum() {
		AlbumResponse album = new AlbumResponse();
//		album.setName("The Joshua Tree");
		album.setArtist("U2");
		album.setId("1412230");
		album.setMbid("19fb4543-45ee-4ded-a07b-32568f6214b0");
		album.setUrl("http://www.last.fm/music/U2/The+Joshua+Tree");
		album.setReleasedate("3 Dec 2007, 00:00");
		return album;
	}

	private void compareAlbum(AlbumResponse expected, AlbumResponse actual) {
		assertEquals(expected.getId(), actual.getId());
	}
}
