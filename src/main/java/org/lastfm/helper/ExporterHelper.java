package org.lastfm.helper;

import java.io.IOException;
import java.util.List;

import org.lastfm.action.ActionResult;
import org.lastfm.metadata.Metadata;

public class ExporterHelper {
	private ImageExporter imageExporter = new ImageExporter();
	private MetadataExporter metadataExporter = new MetadataExporter();

	public ActionResult export(List<Metadata> metadatas) throws IOException {
		imageExporter.export(metadatas);
		metadataExporter.export(metadatas);
		return ActionResult.Exported;
	}
}
