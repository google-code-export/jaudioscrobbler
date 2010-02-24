package org.lastfm.metadata;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4Tag;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class MetadataMp4 extends Metadata {
	private File file;
	private Mp4Tag tag;
	private AudioHeader header;

	public MetadataMp4(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		this.file = file;
		AudioFile audioFile = AudioFileIO.read(file);
		tag = (Mp4Tag)audioFile.getTag();	
		header = audioFile.getAudioHeader();
	}
	
	// For Mocking proposes
	public MetadataMp4(File file, AudioFile audioFile) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		this.file = file;
		tag = (Mp4Tag)audioFile.getTag();	
		header = audioFile.getAudioHeader();
	}

	@Override
	public String getAlbum() {
		return tag.getFirst(FieldKey.ALBUM);
	}

	@Override
	public String getArtist() {
		return tag.getFirst(FieldKey.ARTIST);
	}

	@Override
	public int getLength() {
		return header.getTrackLength();
	}

	@Override
	public String getTitle() {
		return tag.getFirst(FieldKey.TITLE);
	}

	@Override
	public int getTrackNumber() {
		int trackNumber = -1;
		try {
			trackNumber = Integer.parseInt(tag.getFirst(FieldKey.TRACK));
		} catch (NullPointerException nue) {
		} catch (NumberFormatException nfe) {
			trackNumber = -1;
			this.getLog().error(file.getAbsolutePath() + " has a not valid Track Number");
		}
		return trackNumber;
	}

	@Override
	public File getFile() {
		return file;
	}

}
