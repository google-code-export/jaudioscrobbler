package org.lastfm.metadata;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4Tag;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @undestands This class knows how to read metadata from a m4a file
 */

public class Mp4Reader extends MetadataReader {
private AudioFileHelper audioFileHelper = new AudioFileHelper();
	
	public Metadata getMetadata(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, MetadataException {
		AudioFile audioFile = audioFileHelper.read(file);
		tag = (Mp4Tag)audioFile.getTag();	
		header = audioFile.getAudioHeader();
		return generateMetadata(file);
	}
}
