package org.lastfm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataBean;
import org.lastfm.metadata.MetadataMp3;
import org.lastfm.metadata.MetadataMp4;
import org.lastfm.metadata.MetadataWriter;

import com.slychief.javamusicbrainz.ServerUnavailableException;

public class ScrobblerController {
	private HelperScrobbler helperScrobbler;
	final MainWindow mainWindow;
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
			if (metadataList == null) {
				metadataList = new ArrayList<Metadata>();
			}

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
					update(metadata);
				}
			}
			return metadataList;
		}

		private void update(Metadata metadata) {
			JTable descriptionTable = mainWindow.getDescritionTable();
			int row = descriptionTable.getRowCount() - 1;
			DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
			model.addRow(new Object[] { "", "", "", "", "", "" });
			descriptionTable.setValueAt(metadata.getArtist(), row, 0);
			descriptionTable.setValueAt(metadata.getTitle(), row, 1);
			descriptionTable.setValueAt(metadata.getAlbum(), row, 2);
			descriptionTable.setValueAt(metadata.getTrackNumber(), row, 3);
			descriptionTable.setValueAt(metadata.getLength(), row, 4);
			descriptionTable.setValueAt("Ready", row, 5);
		}

		private int showFiles(File root) throws InterruptedException, IOException, TagException, ReadOnlyFileException,
				InvalidAudioFrameException, InvalidId3VersionException, CannotReadException {
			if (fileUtils == null) {
				fileUtils = new FileUtils();
			}
			fileList = fileUtils.getFileList(root);
			metadataList = getMetadataList(fileList);
			return ApplicationState.OK;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
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
					mainWindow.getSendButton().setEnabled(true);
					mainWindow.getCompleteButton().setEnabled(true);
				} catch (IOException e) {
					handleException(e);
				} catch (TagException e) {
					handleException(e);
				} catch (ReadOnlyFileException e) {
					handleException(e);
				} catch (InvalidAudioFrameException e) {
					handleException(e);
				} catch (InvalidId3VersionException e) {
					handleException(e);
				} catch (InterruptedException e) {
					handleException(e);
				} catch (CannotReadException e) {
					handleException(e);
				}
			}
		}

	}

	private void handleException(Exception e) {
		e.printStackTrace();
		mainWindow.getLabel().setText(ApplicationState.OPEN_ERROR);
	}

	class SendListener implements ActionListener {
		private void updateStatus(final int i) {
			int progress = ((i + 1) * 100) / metadataList.size();
			mainWindow.getProgressBar().setValue(progress);
			if (progress == 100) {
				mainWindow.getLabel().setText(ApplicationState.DONE);
				mainWindow.getCompleteButton().setEnabled(true);
				mainWindow.getSendButton().setEnabled(true);
				mainWindow.getOpenButton().setEnabled(true);
			}
		};

		@Override
		public void actionPerformed(ActionEvent e) {

			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					try {
						if (metadataList != null) {
							for (Metadata metadata : metadataList) {
								updateStatus(metadataList.indexOf(metadata));
								int result = helperScrobbler.send(metadata);
								if (result == ApplicationState.ERROR) {
									mainWindow.getLabel().setText(ApplicationState.NETWORK_ERROR);
								}
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

			mainWindow.getCompleteButton().setEnabled(false);
			mainWindow.getSendButton().setEnabled(false);
			mainWindow.getOpenButton().setEnabled(false);
			mainWindow.getProgressBar().setVisible(true);

			mainWindow.getLabel().setText(ApplicationState.WORKING);

		}
	}

	class CompleteListener implements ActionListener {

		protected List<MetadataBean> metadataBeanList = new ArrayList<MetadataBean>();

		private void updateStatus(final int i, int rowCount) {
			int progress = ((i + 1) * 100) / rowCount;
			mainWindow.getProgressBar().setValue(progress);
		};

		@Override
		public void actionPerformed(ActionEvent e) {
			if (service == null) {
				service = new MusicBrainzService();
			}

			mainWindow.getProgressBar().setValue(0);
			mainWindow.getProgressBar().setVisible(true);

			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					if (mainWindow.getCompleteButton().getText().equals(MainWindow.COMPLETE_BUTTON)) {
						for (Metadata metadata : metadataList) {
							int i = metadataList.indexOf(metadata);
							updateStatus(i, metadataList.size());
							String albumName = mainWindow.getDescritionTable().getModel().getValueAt(i, 2).toString();
							if (StringUtils.isEmpty(albumName)) {
								String artistName = mainWindow.getDescritionTable().getModel().getValueAt(i, 0)
										.toString();
								String trackName = mainWindow.getDescritionTable().getModel().getValueAt(i, 1)
										.toString();
								try {
									String album = service.getAlbum(artistName, trackName);
									if (StringUtils.isNotEmpty(album)) {
										String trackNumber = "" + service.getTrackNumber(album);
										mainWindow.getDescritionTable().getModel().setValueAt(album, i,
												ApplicationState.ALBUM_COLUMN);
										mainWindow.getDescritionTable().getModel().setValueAt(trackNumber, i,
												ApplicationState.TRACK_NUMBER_COLUMN);
										mainWindow.getDescritionTable().getModel().setValueAt(
												ApplicationState.NEW_METADATA, i, ApplicationState.STATUS_COLUMN);
										MetadataBean bean = new MetadataBean();
										bean.setAlbum(album);
										bean.setTrackNumber(trackNumber);
										bean.setFile(metadata.getFile());
										bean.setBeanRow(i);
										metadataBeanList.add(bean);
									}
								} catch (ServerUnavailableException sue) {
									log.error(sue, sue);
								}
							}
						}
					} else {
						mainWindow.getProgressBar().setValue(0);
						for (MetadataBean bean : metadataBeanList) {
							updateStatus(metadataBeanList.indexOf(bean), metadataBeanList.size());
							File file = bean.getFile();
							MetadataWriter metadataWriter = new MetadataWriter(file);
							metadataWriter.writeAlbum(bean.getAlbum());
							metadataWriter.writeTrackNumber(bean.getTrackNumber());
							mainWindow.getDescritionTable().getModel().setValueAt(ApplicationState.METADATA_UPDATED,
									bean.getRow(), ApplicationState.STATUS_COLUMN);
						}
					}
					return true;
				}

				public void done() {
					mainWindow.getLabel().setText(ApplicationState.DONE);
					mainWindow.getCompleteButton().setEnabled(true);
					mainWindow.getSendButton().setEnabled(true);
					mainWindow.getOpenButton().setEnabled(true);
					mainWindow.getCompleteButton().setText(ApplicationState.APPLY);
				}
			};
			mainWindow.getCompleteButton().setEnabled(false);
			mainWindow.getSendButton().setEnabled(false);
			mainWindow.getOpenButton().setEnabled(false);
			swingWorker.execute();
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
