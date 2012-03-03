package org.lastfm.helper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.lastfm.util.ImageUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestImageExporter {
	@InjectMocks
	private ImageExporter imageExporter = new ImageExporter();

	@Mock
	private ImageUtils imageUtils;
	@Mock
	private Metadata metadata;
	@Mock
	private Image coverArt;

	private String album = "Bliksem";
	private String artist = "Sander van Doorn";
	private String title = "Bliksem";
	
	private List<Metadata> metadatas = new ArrayList<Metadata>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(metadata.getAlbum()).thenReturn(album);
		when(metadata.getArtist()).thenReturn(artist);
		when(metadata.getTitle()).thenReturn(title);
		when(metadata.getCoverArt()).thenReturn(coverArt);
		metadatas.add(metadata);
	}
	
	@Test
	public void shouldExportASingleImage() throws Exception {
		imageExporter.export(metadatas);
		verify(imageUtils).saveCoverArtToFile(metadatas.get(0).getCoverArt(), StringUtils.EMPTY);
	}
	
	@Test
	public void shouldExportASingleImageWhenSameAlbum() throws Exception {
		metadatas.add(metadata);
		imageExporter.export(metadatas);
		verify(imageUtils).saveCoverArtToFile(metadatas.get(0).getCoverArt(), StringUtils.EMPTY);
	}
	
	@Test
	public void shouldExportTwoImagesWhenDifAlbum() throws Exception {
		Metadata metadata = setSecondMetadataExpectations();
		metadatas.add(metadata);
		imageExporter.export(metadatas);
		verify(imageUtils).saveCoverArtToFile(coverArt, "Sander van Doorn" + "-" + "Bliksem");
		verify(imageUtils).saveCoverArtToFile(coverArt, "ATA" + "-" + "Blue Skies (Andy Tau Remix)");
	}

	private Metadata setSecondMetadataExpectations() {
		Metadata metadata = mock(Metadata.class);
		when(metadata.getAlbum()).thenReturn("Blue Skies");
		when(metadata.getArtist()).thenReturn("ATA");
		when(metadata.getTitle()).thenReturn("Blue Skies (Andy Tau Remix)");
		when(metadata.getCoverArt()).thenReturn(coverArt);
		return metadata;
	}
}
