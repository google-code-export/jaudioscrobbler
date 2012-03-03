package org.lastfm.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutStreamWriter {

	public OutputStream getWriter(File file) throws FileNotFoundException {
		return new FileOutputStream(file);
	}

}
