package org.lastfm.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.lastfm.action.ActionResult;
import org.lastfm.metadata.Metadata;
import org.lastfm.util.FileUtils;
import org.lastfm.util.ImageUtils;

public class ExporterHelper {
	private static final String NEW_LINE = "\n";
	private static final String DASH = " - ";
	private static final String DOT = ". ";
	private static final String PAR_ABRE = " (";
	private static final String PAR_CIERRA = ")";
	private FileUtils fileUtils = new FileUtils();
	private ImageUtils imageUtils = new ImageUtils();

	public ActionResult export(List<Metadata> metadatas) throws IOException {
		imageUtils.saveCoverArtToFile(metadatas.get(0).getCoverArt());
		File file = fileUtils.createTempFile();
		OutputStream writer = new FileOutputStream(file);
		int counter = 1;
		for (Metadata metadata : metadatas) {
			writer.write(Integer.toString(counter++).getBytes());
			writer.write(DOT.getBytes());
			writer.write(metadata.getArtist().getBytes());
			writer.write(DASH.getBytes());
			writer.write(metadata.getTitle().getBytes());
			writer.write(PAR_ABRE.getBytes());
			writer.write(Integer.toString(metadata.getLength()).getBytes());
			writer.write(PAR_CIERRA.getBytes());
			writer.write(NEW_LINE.getBytes());
		}
		writer.flush();
		writer.close();
		return ActionResult.Exported;
	}

}
