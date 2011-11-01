package org.lastfm.model;

import org.apache.commons.lang3.StringUtils;

public class MusicBrainzTrack {
	private String album;
	private String trackNumber;
	private String totalTrackNumber;
	private String cdNumber;
	private String totalCds;
	private String year;

	public MusicBrainzTrack() {
		album = StringUtils.EMPTY;
		trackNumber = "0";
	}
	
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getTotalTrackNumber() {
		return totalTrackNumber;
	}
	public void setTotalTrackNumber(String totalTrackNumber) {
		this.totalTrackNumber = totalTrackNumber;
	}
	public void setCdNumber(String cdNumber) {
		this.cdNumber = cdNumber;
	}
	public String getCdNumber() {
		return cdNumber;
	}
	public void setTotalCds(String totalCds) {
		this.totalCds = totalCds;
	}
	public String getTotalCds() {
		return totalCds;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYear() {
		return year;
	}
}
