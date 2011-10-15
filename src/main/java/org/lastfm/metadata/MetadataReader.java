package org.lastfm.metadata;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @undestands A class who gather all common methods getting Metadata
 */

public abstract class MetadataReader {
	protected Tag tag;
	protected AudioHeader header;
	protected Log log = LogFactory.getLog(this.getClass());
	
	public MetadataReader() { 
		turnOffLogMessages();
	}
	
	private void turnOffLogMessages() {
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(Level.OFF);
		}
	}
	
	private String getArtist(){
		return tag.getFirst(FieldKey.ARTIST);
	}

	private String getTitle(){
		return tag.getFirst(FieldKey.TITLE);
	}

	private String getAlbum(){
		return tag.getFirst(FieldKey.ALBUM);
	}
	
	private String getGenre(){
		return tag.getFirst(FieldKey.GENRE);
	}

	private int getLength(){
		return header.getTrackLength();
	}
	
	private int getBitRate() {
		// Case variable bitRate
		String bitRate = header.getBitRate().replace("~", "");
		return Integer.parseInt(bitRate);
	}

	private int getTrackNumber(){
		int trackNumber = -1;
		try {
			trackNumber = Integer.parseInt(tag.getFirst(FieldKey.TRACK));
		} catch (NullPointerException nue) {
		} catch (NumberFormatException nfe) {
			trackNumber = -1;
			log.warn(getTitle() + " has a not valid Track Number");
		}
		return trackNumber;
	}

	private ImageIcon getCoverArt() throws IOException, MetadataException {
		Artwork artwork = tag.getFirstArtwork();
		log.info(getTitle() + " has cover art?: " + (artwork != null));
		return artwork==null ? null: new ImageIcon(artwork.getImage());
	}
	
	protected Metadata generateMetadata(File file) throws IOException, MetadataException {
		Metadata metadata = new Metadata();
		metadata.setCoverArt(getCoverArt());
		metadata.setTitle(getTitle());
		metadata.setArtist(getArtist());
		metadata.setAlbum(getAlbum());
		metadata.setGenre(getGenre());
		metadata.setLenght(getLength());
		metadata.setTrackNumber(getTrackNumber());
		metadata.setBitRate(getBitRate());
		metadata.setFile(file);
		return metadata;
	}
}