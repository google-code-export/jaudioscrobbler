package org.lastfm.helper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.lastfm.metadata.Metadata;
import org.lastfm.util.FileUtils;

public class MetadataExporter {
	private static final String NEW_LINE = "\n";
	private static final String DASH = " - ";
	private static final String DOT = ". ";
	private static final String PAR_OPEN = " (";
	private static final String PAR_CLOSE = ")";
	private FileUtils fileUtils = new FileUtils();
	private Formatter formatter = new Formatter();
	private OutStreamWriter  outputStreamWriter = new OutStreamWriter();

	public void export(List<Metadata> metadatas) throws IOException {
		File file = fileUtils.createTempFile();
		OutputStream writer = outputStreamWriter.getWriter(file);
		int counter = 1;
		for (Metadata metadata : metadatas) {
			writer.write(Integer.toString(counter++).getBytes());
			writer.write(DOT.getBytes());
			writer.write(metadata.getArtist().getBytes());
			writer.write(DASH.getBytes());
			writer.write(metadata.getTitle().getBytes());
			writer.write(PAR_OPEN.getBytes());
			writer.write(formatter.getDuration(metadata.getLength()).getBytes());
			writer.write(PAR_CLOSE.getBytes());
			writer.write(NEW_LINE.getBytes());
		}
		writer.flush();
		writer.close();
	}

}
