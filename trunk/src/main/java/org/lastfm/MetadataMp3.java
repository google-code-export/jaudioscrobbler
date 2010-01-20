package org.lastfm;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


public class MetadataMp3 extends Metadata {
	private Tag tag;
	private AudioHeader header;
	private final File file;
	

	public MetadataMp3(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		this.file = file;
		AudioFile audioFile = AudioFileIO.read(file);
		if (audioFile instanceof MP3File) {
			MP3File audioFMP3 = (MP3File) audioFile;
			if (!audioFMP3.hasID3v2Tag()) {
				throw new InvalidId3VersionException(); 
			} else {
				tag = audioFile.getTag();
				header = audioFile.getAudioHeader();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.lastfm.Metadata#getArtist()
	 */
	public String getArtist() {
		return tag.getFirst(FieldKey.ARTIST);
	}

	/* (non-Javadoc)
	 * @see org.lastfm.Metadata#getTitle()
	 */
	public String getTitle() {
		return tag.getFirst(FieldKey.TITLE);
	}

	/* (non-Javadoc)
	 * @see org.lastfm.Metadata#getAlbum()
	 */
	public String getAlbum() {
		return tag.getFirst(FieldKey.ALBUM);
	}

	/* (non-Javadoc)
	 * @see org.lastfm.Metadata#getSize()
	 */
	public int getLength() {
		return header.getTrackLength();
	}

	/* (non-Javadoc)
	 * @see org.lastfm.Metadata#getTrackNumber()
	 */
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
}
