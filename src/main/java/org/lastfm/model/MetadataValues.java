package org.lastfm.model;

public class MetadataValues {
	private String genre;
	private String album;
	private String tracks;
	private String cd;
	private String cds;

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getAlbum() {
		return album;
	}

	public void setTracks(String tracks) {
		this.tracks = tracks;
	}
	
	public String getTracks() {
		return tracks;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}
	
	public String getCd() {
		return cd;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}
	
	public String getCds() {
		return cds;
	}

}
