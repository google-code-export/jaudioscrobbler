package org.lastfm.model;

import java.io.Serializable;

public class Album implements Serializable {
	private static final long serialVersionUID = 4563119987349894991L;

	private AlbumResponse albumResponse;
	
	public Album() {
	}

	public AlbumResponse getAlbumResponse() {
		return albumResponse;
	}

	public void setAlbumResponse(AlbumResponse albumResponse) {
		this.albumResponse = albumResponse;
	}
}
