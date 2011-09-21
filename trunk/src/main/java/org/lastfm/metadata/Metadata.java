package org.lastfm.metadata;

import java.io.File;

import javax.swing.ImageIcon;

public class Metadata {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private int trackNumber;
	private ImageIcon artwork;
	private int length;
	private int bitRate;
	private File file;
	private ImageIcon coverArt;
	private int totalTracksNumber;
	
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
	public int getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
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
	public int getTotalTracksNumber() {
		return totalTracksNumber;
	}
	public void setTotalTracksNumber(int totalTracksNumber) {
		this.totalTracksNumber = totalTracksNumber;
	}
}
