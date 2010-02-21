package org.lastfm;

import java.io.File;

public class MetadataBean {

	private String album;
	private String trackNumber;
	private File file;
	private int row;

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setTrackNumber(String trackNumber) {
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

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setBeanRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}
}
