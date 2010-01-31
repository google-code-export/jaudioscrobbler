package org.lastfm;

import static org.junit.Assert.*;
import java.io.File;
import java.util.List;

import org.junit.Test;

public class TestFileUtils {

	@Test
	public void shouldScanADirectory() throws Exception {
		FileUtils fileUtils = new FileUtils();
		File root = new MockFile("root");
		List<File> fileList = fileUtils.getFileList(root);
		assertEquals(2, fileList.size());
	}

	@SuppressWarnings("serial")
	class MockFile extends File {
		String path = "src\\test\\resources";
		public MockFile(String pathname) {
			super(pathname);
		}

		@Override
		public String[] list() {
			String[] fileList = {"fileNameOne", "fileNameTwo"};
			return fileList;
		}

		public String getAbsolutePath() {
			return path;
		}
	}
}
