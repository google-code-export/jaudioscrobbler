package org.lastfm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.metadata.MetadataWriter;

public class TestChangeMetadata {

	private void initialize() throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		String path = "path";
		File root = new File(path);
		
		FileUtils utils = new FileUtils();
		List<File> fileList = utils.getFileList(root);
		
		MetadataWriter metadataWriter = new MetadataWriter();
		for (File file : fileList) {
			metadataWriter.setFile(file);
			metadataWriter.writeAlbum("Everything is OK");
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		new TestChangeMetadata().initialize();
	}

}
