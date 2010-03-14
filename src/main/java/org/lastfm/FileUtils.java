package org.lastfm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class FileUtils {
	private List<File> fileList;

	public List<File> getFileList(File root) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		fileList = new ArrayList<File>();
		scan(root);
		return fileList;
	}
	
	private void scan(File root) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		String[] listFiles = root.list();
		for(int i=0; i<listFiles.length; i++){
			File file = new File(root.getAbsolutePath() + "\\" + listFiles[i]);
			if(file.isDirectory()){
				scan(file);
			} else{
				fileList.add(file);
			}
		}
	}
}

