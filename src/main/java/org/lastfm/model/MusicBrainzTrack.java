package org.lastfm.model;

public class MusicBrainzTrack {
	private String album;
	private int trackNumber;
	private int totalTrackNumber;

	public MusicBrainzTrack() {
		album = "";
		trackNumber = -1;
	}
	
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}
	public int getTotalTrackNumber() {
		return totalTrackNumber;
	}
	public void setTotalTrackNumber(int totalTrackNumber) {
		this.totalTrackNumber = totalTrackNumber;
	}
}
