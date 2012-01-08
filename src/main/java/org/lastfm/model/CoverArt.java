package org.lastfm.model;

import javax.swing.ImageIcon;

public class CoverArt {
	private final ImageIcon imageIcon;
	private final CoverArtType type;

	public CoverArt(ImageIcon imageIcon, CoverArtType type){
		this.imageIcon = imageIcon;
		this.type = type;
	}
	
	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	
	public CoverArtType getType() {
		return type;
	}

}
