package org.lastfm.metadata;

import java.io.File;

import javax.swing.ImageIcon;

public class Metadata {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private String trackNumber;
	private String totalTracks;
	private ImageIcon artwork;
	private int length;
	private int bitRate;
	private File file;
	private ImageIcon coverArt;
	private String cdNumber;
	private String totalCds;
	private String year;
	
	public File getFile() {
		return file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public ImageIcon getCoverArt() {
		return artwork;
	}
	public void setCoverArt(ImageIcon artwork) {
		this.artwork = artwork;
	}
	public void setLenght(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}
	
	public int getBitRate() {
		return bitRate;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public void setLastfmCoverArt(ImageIcon coverArt) {
		this.coverArt = coverArt;
	}
	public ImageIcon getLastfmCoverArt() {
		return coverArt;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getTotalTracks() {
		return totalTracks;
	}
	public void setTotalTracks(String totalTracks) {
		this.totalTracks = totalTracks;
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
