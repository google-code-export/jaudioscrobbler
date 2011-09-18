package org.lastfm.model;

import java.io.Serializable;
import java.util.List;

public class AlbumResponse implements Serializable {
	private static final long serialVersionUID = -9101230920825834214L;
	private String album;
	private String name;
	private String artist;
	private String id;
	private String mbid;
	private String url;
	private String releasedate;
	private String listeners;
	private String playcount;
	private String tracks;
	private String toptags;
	private List<Image> image;
	
	public AlbumResponse() {
	}
	
	public String getListeners() {
		return listeners;
	}

	public void setListeners(String listeners) {
		this.listeners = listeners;
	}

	public String getPlaycount() {
		return playcount;
	}

	public void setPlaycount(String playcount) {
		this.playcount = playcount;
	}

	public String getTracks() {
		return tracks;
	}

	public void setTracks(String tracks) {
		this.tracks = tracks;
	}

	public String getToptags() {
		return toptags;
	}

	public void setToptags(String toptags) {
		this.toptags = toptags;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMbid() {
		return mbid;
	}
	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReleasedate() {
		return releasedate;
	}
	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public List<Image> getImage() {
		return image;
	}
	public void setImage(List<Image> image) {
		this.image = image;
	}
}
