package org.lastfm.helper;

import static org.junit.Assert.assertNotNull;

import org.jaudiotagger.tag.datatype.Artwork;
import org.junit.Test;

public class TestArtworkHelper {

	private ArtworkHelper artworkHelper = new ArtworkHelper();
	
	@Test
	public void shouldCreateAnArtWork() throws Exception {
		Artwork artwork = artworkHelper.createArtwork();
		assertNotNull(artwork);
	}

}
