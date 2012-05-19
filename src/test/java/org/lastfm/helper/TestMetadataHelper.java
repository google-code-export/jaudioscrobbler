package org.lastfm.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Set;

import org.junit.Test;
import org.lastfm.metadata.MetadataReader;
import org.lastfm.metadata.Mp4Reader;

public class TestMetadataHelper {

	private MetadataHelper metadataHelper = new MetadataHelper();

	@Test
	public void shouldCreateAHashset() throws Exception {
		Set<File> hashset = metadataHelper.createHashSet();
		assertNotNull(hashset);
	}
	
	@Test
	public void shouldCreateMp3Reader() throws Exception {
		MetadataReader mp3Reader = metadataHelper.createMp3Reader();
		assertNotNull(mp3Reader);
	}
	
	@Test
	public void shouldCreateMp4Reader() throws Exception {
		Mp4Reader mp4Reader = metadataHelper.createMp4Reader();
		assertNotNull(mp4Reader);
	}
	
}
