package org.lastfm.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public abstract class Metadata {
	
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("org.lastfm");
	
	public Metadata() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("src/main/resources/log4j.properties")));
		PropertyConfigurator.configure(properties);
		
		turnOffLogMessages();
	}

	public org.apache.log4j.Logger getLog(){
		return log;
	}

	protected void turnOffLogMessages() {
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(Level.OFF);
		}
	}
	public abstract File getFile();

	public abstract String getArtist();

	public abstract String getTitle();

	public abstract String getAlbum();

	public abstract int getLength();

	public abstract int getTrackNumber();


}