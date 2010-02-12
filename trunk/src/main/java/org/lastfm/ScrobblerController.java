package org.lastfm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;

import com.slychief.javamusicbrainz.ServerUnavailableException;

public class ScrobblerController {
	private final MainWindow mainWindow;
	private HelperScrobbler helperScrobbler;
	List<Metadata> metadataList;
	LoginWindow loginWindow;
	LoginController loginController;
	JFileChooser fileChooser;
	FileUtils fileUtils;
	MusicBrainzService service;
	private Logger log = Logger.getLogger(this.getClass());

	public ScrobblerController(HelperScrobbler helperScrobbler, MainWindow mainWindow, LoginWindow loginWindow) {
		this.helperScrobbler = helperScrobbler;
		this.mainWindow = mainWindow;
		this.loginWindow = loginWindow;
		this.mainWindow.addOpenListener(new OpenListener());
		this.mainWindow.addSendListener(new SendListener());
		this.mainWindow.addCompleteListener(new CompleteListener());
		this.loginWindow.addLoginListener(new LoginListener());
	}

	class OpenListener implements ActionListener {
		private List<File> fileList;

		public List<Metadata> getMetadataList(List<File> fileList) throws InterruptedException, IOException,
				CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException,
				InvalidId3VersionException {
			this.fileList = fileList;

			Metadata metadata = null;
			metadataList = new ArrayList<Metadata>();

			for (File file : fileList) {
				if (file.getPath().endsWith("mp3")) {
					metadata = new MetadataMp3(file);
				} else if (file.getPath().endsWith("m4a")) {
					metadata = new MetadataMp4(file);
				}

				if (metadata == null) {
					log.error(file.getAbsoluteFile() + " is not a valid Audio File");
				} else if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())) {
					metadataList.add(metadata);
					ApplicationState.update(metadata);
				}
			}
			return metadataList;
		}

		private void showFiles(File root) throws InterruptedException, IOException, CannotReadException, TagException,
				ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
			if (fileUtils == null) {
				fileUtils = new FileUtils();
			}
			fileList = fileUtils.getFileList(root);
			metadataList = getMetadataList(fileList);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChooser == null) {
				fileChooser = new JFileChooser();
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selection = fileChooser.showOpenDialog(mainWindow.getPanel());
			if (selection == JFileChooser.APPROVE_OPTION) {
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

	class SendListener implements ActionListener {
		private void updateStatus(final int i) {
			mainWindow.getProgressBar().setValue(((i + 1) * 100) / metadataList.size());
		};

		@Override
		public void actionPerformed(ActionEvent e) {
			mainWindow.getProgressBar().setVisible(true);

			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					try {
						if (metadataList != null) {
							for (Metadata metadata : metadataList) {
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
			mainWindow.getLabel().setText(ApplicationState.DONE);
		}
	}

	class CompleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (service == null) {
				service = new MusicBrainzService();
			}
			for (int i = 0; i < mainWindow.getTable().getRowCount(); i++) {
				String albumName = mainWindow.getTable().getModel().getValueAt(i, 2).toString();
				if (StringUtils.isEmpty(albumName)) {
					String artistName = mainWindow.getTable().getModel().getValueAt(i, 0).toString();
					String trackName = mainWindow.getTable().getModel().getValueAt(i, 1).toString();
					try {
						System.out.println(service.getArtist(artistName, trackName));
					} catch (ServerUnavailableException sue) {
						sue.printStackTrace();
					}
				}
			}
		}
	}

	public class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int result = -1;
			String username = loginWindow.getUsername().getText();
			String password = loginWindow.getPassword().getText();

			if (loginController == null) {
				loginController = new LoginController();
			}
			try {
				result = loginController.login(username, password);
				if (result == ApplicationState.OK) {
					ApplicationState.userName = username;
					ApplicationState.password = password;
					loginWindow.getFrame().dispose();
					mainWindow.getLoginLabel().setText(ApplicationState.LOGGED_AS + username);
				} else {
					mainWindow.getLoginLabel().setText(ApplicationState.LOGIN_FAIL);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public List<Metadata> getMetadataList() {
		return metadataList;
	}
}
