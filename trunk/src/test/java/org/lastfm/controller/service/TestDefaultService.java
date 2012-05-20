package org.lastfm.controller.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.model.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDefaultService {

	private static final String TOTAL_TRACKS = "2";
	private static final String TRACK_NUMBER_METADATA_ONE = "1";
	private static final String TRACK_NUMBER_METADATA_TWO = TOTAL_TRACKS;
	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";
	
	@InjectMocks
	private DefaultService defaultService = new DefaultService();

	@Mock
	private Metadata metadata_one;
	@Mock
	private Metadata metadata_two;
	@Mock
	private MetadataService metadataService;
	
	private List<Metadata> metadatas = new ArrayList<Metadata>();
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(metadata_one.getTrackNumber()).thenReturn(TRACK_NUMBER_METADATA_ONE);
		when(metadata_two.getTrackNumber()).thenReturn(TRACK_NUMBER_METADATA_TWO);
		metadatas.add(metadata_one);
		metadatas.add(metadata_two);
	}
	
	@Test
	public void shouldCompleteTotalTracks() throws Exception {
		when(metadataService.isSameAlbum(metadatas)).thenReturn(true);
		defaultService.isCompletable(metadatas);
		verify(metadata_one).setTotalTracks(TOTAL_TRACKS);
		verify(metadata_two).setTotalTracks(TOTAL_TRACKS);
	}
	
	@Test
	public void shouldCompleteCDNumbers() throws Exception {
		when(metadataService.isSameAlbum(metadatas)).thenReturn(true);
		defaultService.isCompletable(metadatas);
		
		verify(metadata_one).setCdNumber(CD_NUMBER);
		verify(metadata_one).setTotalCds(TOTAL_CD_NUMBER);
		verify(metadata_two).setCdNumber(CD_NUMBER);
		verify(metadata_two).setTotalCds(TOTAL_CD_NUMBER);
	}
	
	@Test
	public void shouldNotCompleteIfSingleTrack() throws Exception {
		List<Metadata> metadatas = new ArrayList<Metadata>();
		metadatas.add(metadata_one);
		
		defaultService.isCompletable(metadatas);
		
		verify(metadata_one, never()).setTotalTracks(TOTAL_TRACKS);
		verify(metadata_one, never()).setCdNumber(CD_NUMBER);
		verify(metadata_one, never()).setTotalCds(TOTAL_CD_NUMBER);
	}

}
