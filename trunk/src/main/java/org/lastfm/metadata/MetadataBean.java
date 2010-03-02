package org.lastfm.metadata;

import java.io.File;

public class MetadataBean {

	private String album;
	private Integer trackNumber;
	private File file;
	private int row;
	private String artist;
	private String trackName;

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public String getAlbum() {
		return album;
	}

	public Integer getTrackNumber() {
		return trackNumber;
	}

	public void setBeanRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getArtist() {
		return artist;
	}

	public String getTrackName() {
		return trackName;
	}
}
