package org.lastfm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ActionMethod;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataBean;
import org.lastfm.metadata.MetadataException;
import org.lastfm.metadata.MetadataWriter;
import org.lastfm.metadata.Mp3Reader;
import org.lastfm.metadata.Mp4Reader;
import org.lastfm.model.Credentials;
import org.lastfm.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who manage ALL scrobbler actions 
 */

@Controller
public class ScrobblerController {
	private HelperScrobbler helperScrobbler;
	private MainWindow mainWindow;
	private LoginWindow loginWindow;

	private JFileChooser fileChooser;
	private FileUtils fileUtils;
	private MetadataWriter metadataWriter = new MetadataWriter();
	private List<MetadataBean> metadataBeanList = new ArrayList<MetadataBean>();
	private List<File> fileList;
	private Logger log = Logger.getLogger(this.getClass());

	// TODO Change this awful code in order to respect encapsulation
	LoginController loginController = new LoginController();;
	List<Metadata> metadataList;
	MusicBrainzService service;

	@Autowired
	public void setAddHelperScrobbler(HelperScrobbler helperScrobbler) {
		this.helperScrobbler = helperScrobbler;
	}

	@Autowired
	public void setAddMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.mainWindow.getDescriptionTable().getModel().addTableModelListener(new DescriptionTableModelListener());
	}

	@Autowired
	public void setAddLoginWindow(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	private void updateStatus(final int i, int size) {
		int progress = ((i + 1) * 100) / size;
		mainWindow.getProgressBar().setValue(progress);
	};

	@SuppressWarnings("unused")
	@ActionMethod(Actions.LOGIN_ID)
	private void login(Credentials credentials) {
		int result = -1;
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		try {
			result = loginController.login(username, password);
			if (result == ApplicationState.OK) {
				ApplicationState.userName = username;
				ApplicationState.password = password;
				loginWindow.getFrame().dispose();
				mainWindow.getLoginLabel().setText(ApplicationState.LOGGED_AS + username);
				mainWindow.getSendButton().setEnabled(true);
			} else {
				mainWindow.getLoginLabel().setText(ApplicationState.LOGIN_FAIL);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	@ActionMethod(Actions.GET_METADATA)
	private void showMetadata() {
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
				mainWindow.getCompleteMetadataButton().setEnabled(true);
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
			} catch (MetadataException e) {
				handleException(e);
			}
		}
	}
	
	@SuppressWarnings("unused")
	@ActionMethod(Actions.SEND_METADATA)
	private void sendMetadata(){
		SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					if (metadataList != null) {
						for (Metadata metadata : metadataList) {
							updateStatus(metadataList.indexOf(metadata), metadataList.size());
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

			@Override
			public void done() {
				mainWindow.getLabel().setText(ApplicationState.DONE);
				mainWindow.getCompleteMetadataButton().setEnabled(true);
				mainWindow.getSendButton().setEnabled(true);
				mainWindow.getOpenButton().setEnabled(true);
			}
		};

		swingWorker.execute();
		mainWindow.getCompleteMetadataButton().setEnabled(false);
		mainWindow.getSendButton().setEnabled(false);
		mainWindow.getOpenButton().setEnabled(false);
		mainWindow.getProgressBar().setVisible(true);
		mainWindow.getLabel().setText(ApplicationState.WORKING);
	}
	
	@SuppressWarnings("unused")
	@ActionMethod(Actions.COMPLETE_METADATA)
	private void completeMetadata(){
		if (service == null) {
			service = new MusicBrainzService();
		}

		mainWindow.getProgressBar().setValue(0);
		mainWindow.getProgressBar().setVisible(true);

		SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				if (mainWindow.getCompleteMetadataButton().getText().equals(MainWindow.COMPLETE_BUTTON)) {
					for (Metadata metadata : metadataList) {
						int i = metadataList.indexOf(metadata);
						updateStatus(i, metadataList.size());
						String albumName = mainWindow.getDescriptionTable().getModel().getValueAt(i, 2).toString();
						if (StringUtils.isEmpty(albumName)) {
							String artistName = mainWindow.getDescriptionTable().getModel().getValueAt(i, 0)
									.toString();
							String trackName = mainWindow.getDescriptionTable().getModel().getValueAt(i, 1)
									.toString();
							try {
								String album = service.getAlbum(artistName, trackName);
								if (StringUtils.isNotEmpty(album)) {
									Integer trackNumber = service.getTrackNumber(album);
									mainWindow.getDescriptionTable().getModel()
											.setValueAt(album, i, ApplicationState.ALBUM_COLUMN);
									mainWindow.getDescriptionTable().getModel()
											.setValueAt(trackNumber, i, ApplicationState.TRACK_NUMBER_COLUMN);
									mainWindow
											.getDescriptionTable()
											.getModel()
											.setValueAt(ApplicationState.NEW_METADATA, i,
													ApplicationState.STATUS_COLUMN);
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
						metadataWriter.setFile(file);
						metadataWriter.writeArtist(bean.getArtist());
						metadataWriter.writeTrackName(bean.getTrackName());
						metadataWriter.writeAlbum(bean.getAlbum());
						Integer trackNumber = bean.getTrackNumber();
						metadataWriter.writeTrackNumber(trackNumber.toString());
						mainWindow
								.getDescriptionTable()
								.getModel()
								.setValueAt(ApplicationState.METADATA_UPDATED, bean.getRow(),
										ApplicationState.STATUS_COLUMN);
					}
				}
				return true;
			}

			@Override
			public void done() {
				mainWindow.getLabel().setText(ApplicationState.DONE);
				mainWindow.getCompleteMetadataButton().setEnabled(true);
				mainWindow.getOpenButton().setEnabled(true);
				mainWindow.getDescriptionTable().setEnabled(true);
				mainWindow.getCompleteMetadataButton().setText(MainWindow.APPLY);
			}
		};
		mainWindow.getCompleteMetadataButton().setEnabled(false);
		mainWindow.getOpenButton().setEnabled(false);
		swingWorker.execute();
	}
	
	@SuppressWarnings("unused")
	@ActionMethod(Actions.LOGIN_LASTFM)
	private void loginLastFM(){
		loginWindow.getFrame().setLocationRelativeTo(mainWindow.getFrame());
		loginWindow.getFrame().setVisible(true);
	}
	
	private void update(Metadata metadata) {
		JTable descriptionTable = mainWindow.getDescriptionTable();
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

	private List<Metadata> getMetadataList(List<File> fileList) throws InterruptedException, IOException,
			CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException, MetadataException {
		ScrobblerController.this.fileList = fileList;

		Metadata metadata = null;
		if (metadataList == null) {
			metadataList = new ArrayList<Metadata>();
		}

		for (File file : fileList) {
			if (file.getPath().endsWith("mp3")) {
				metadata = new Mp3Reader().getMetadata(file);
			} else if (file.getPath().endsWith("m4a")) {
				metadata = new Mp4Reader().getMetadata(file);
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

	private int showFiles(File root) throws InterruptedException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, InvalidId3VersionException, CannotReadException, MetadataException {
		if (fileUtils == null) {
			fileUtils = new FileUtils();
		}
		fileList = fileUtils.getFileList(root);
		metadataList = getMetadataList(fileList);
		return ApplicationState.OK;
	}

	class DescriptionTableModelListener implements TableModelListener {

		@SuppressWarnings("static-access")
		@Override
		public void tableChanged(TableModelEvent e) {
			if (mainWindow.getCompleteMetadataButton().getText().equals(mainWindow.APPLY)
					&& e.getColumn() != ApplicationState.STATUS_COLUMN) {
				int lastRow = e.getLastRow();
				DefaultTableModel model = (DefaultTableModel) e.getSource();
				String artist = (String) model.getValueAt(lastRow, 0);
				String trackName = (String) model.getValueAt(lastRow, 1);
				String album = (String) model.getValueAt(lastRow, 2);
				MetadataBean bean = new MetadataBean();
				bean.setArtist(artist);
				bean.setTrackName(trackName);
				bean.setAlbum(album);
				bean.setFile(fileList.get(lastRow));
				try {
					Integer trackNumber = (Integer) model.getValueAt(lastRow, 3);
					bean.setTrackNumber(trackNumber);
				} catch (ClassCastException cce) {
					log.debug(cce, cce);
				}
				metadataBeanList.add(bean);
			}
		}

	}

	private void handleException(Exception e) {
		e.printStackTrace();
		mainWindow.getLabel().setText(ApplicationState.OPEN_ERROR);
	}
}
