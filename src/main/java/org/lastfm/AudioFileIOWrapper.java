package org.lastfm;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class AudioFileIOWrapper extends AudioFile {

	public AudioFileIOWrapper(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		AudioFileIO.read(file);
	}

}
