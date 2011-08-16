package org.lastfm.metadata;

/**
 * @author joseluis.delacruz@gmail.com
 * @understands A class who represents exceptions from Metadata project 
 */

public class MetadataException extends Exception {
	private static final long serialVersionUID = -1110345551784140359L;

	public MetadataException(String message) {
		super(message);
	}
}
