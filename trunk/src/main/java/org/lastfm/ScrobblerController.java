package org.lastfm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import net.roarsoftware.lastfm.Artist;
import net.roarsoftware.lastfm.Track;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.gui.MainWindow;

public class ScrobblerController {
	private final MainWindow mainWindow;
	private final HelperScrobbler helperScrobbler;
	private List<Metadata> metadataList;
	

	public ScrobblerController(HelperScrobbler helperScrobbler, MainWindow mainWindow) {
		this.helperScrobbler = helperScrobbler;
		this.mainWindow = mainWindow;
		this.mainWindow.addOpenListener(new OpenListener());
		this.mainWindow.addSendListener(new SendListener());
		this.mainWindow.addCompleteListener(new CompleteListener());
	}
	
	class OpenListener implements ActionListener{
		private JFileChooser fileChooser;

		private void showFiles(File root) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
			metadataList = new FileUtils().getFileList(root);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selection = fileChooser.showOpenDialog(mainWindow.getPanel());
			if(selection == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				mainWindow.getDirectoryField().setText(file.getAbsolutePath());
				try {
					showFiles(file);
				} catch (CannotReadException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (TagException e1) {
					e1.printStackTrace();
				} catch (ReadOnlyFileException e1) {
					e1.printStackTrace();
				} catch (InvalidAudioFrameException e1) {
					e1.printStackTrace();
				} catch (InvalidId3VersionException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	class SendListener implements ActionListener{
		private void updateStatus(final int i) {
			Runnable doSetProgressBarValue = new Runnable() {
	            public void run() {
	                mainWindow.getProgressBar().setValue(i * 100 / metadataList.size());
	            }
	        };
	        SwingUtilities.invokeLater(doSetProgressBarValue);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mainWindow.getProgressBar().setVisible(true);
			
			SwingWorker swingWorker = new SwingWorker<Boolean, Integer>(){

				@Override
				protected Boolean doInBackground() throws Exception {
					try {
						if(metadataList!=null){
							for(Metadata metadata: metadataList){
								updateStatus(metadataList.indexOf(metadata));
								helperScrobbler.send(metadata);
							}
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return true;
				}
				
				
			};
			swingWorker.execute();
			mainWindow.getLabel().setText("Done");
		}
	}
	
	class CompleteListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = "250d02d1a21e78488d79ad1a73e88c72 "; //this is the key used in the last.fm API examples online.
			String artist = mainWindow.getTable().getModel().getValueAt(3, 0).toString();
			Collection<Artist> artists = Artist.search(artist, key);
			System.out.println("Top Tracks for " + artist);
			for (Artist a : artists) {
				System.out.println(a.getName());
			}
		}
	}
		
	
}
		




