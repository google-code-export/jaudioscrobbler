package org.lastfm.util;

import static org.junit.Assert.*;
import java.io.File;
import java.util.List;

import org.junit.Test;
import org.lastfm.util.FileUtils;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

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
