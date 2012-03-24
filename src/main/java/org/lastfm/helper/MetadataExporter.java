package org.lastfm.helper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.ExportPackage;
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
	private Log log = LogFactory.getLog(this.getClass());

	public void export(ExportPackage exportPackage) throws IOException {
		File file = fileUtils.createFile(exportPackage.getRoot(), StringUtils.EMPTY, ApplicationState.FILE_EXT);
		log.info("Exporting metadata to: " + file.getAbsolutePath());
		OutputStream writer = outputStreamWriter.getWriter(file);
		int counter = 1;
		for (Metadata metadata : exportPackage.getMetadataList()) {
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
