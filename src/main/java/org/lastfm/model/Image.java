package org.lastfm.model;

import java.io.Serializable;

public class Image implements Serializable{
	private static final long serialVersionUID = 5525372780501287216L;
	private String text;
	private String size;
	
	public Image() {
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
