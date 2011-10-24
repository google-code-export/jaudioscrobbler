package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.action.ActionResult;
import org.lastfm.action.Actions;
import org.lastfm.action.ResponseCallback;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.event.EventMethod;
import org.lastfm.event.Events;
import org.lastfm.helper.MetadataAdapter;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.lastfm.model.User;
import org.lastfm.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A principal JAudioScrobbler principal window
 */

@SuppressWarnings("unused")
public class MainWindow {
	private static final int THREE_HUNDRED = 300;
	private static final int ONE_HUNDRED = 100;
	private static final String JMENU_ITEM_LABEL = "Sign in Last.fm";
	private static final String JMENU_LABEL = "Last.fm";
	private static final int DIRECTORY_SELECTED_LENGHT = 20;
	private static final String STATUS_LABEL = "Status";
	private static final String CTRL_O = "CTRL+O";
	private static final String ENTER = "ENTER";
	private static final String LOGIN_MENU_ITEM = "loginMenuItem";
	private static final String SEND_SCROBBLINGS = "Send";
	private static final String LOAD_FILES = "Open";
	private static final String LOG_OUT = "logged out";
	private static final String OPEN_BUTTON_NAME = "openButton";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private static final String APPLY_BUTTON_NAME = "applyButton";
	private static final String COMPLETE_BUTTON_NAME = "completeMetadataButton";
	private int counter = 0;

	private JFrame frame;
	private JPanel panel;
	private JButton openButton;
	private JButton sendButton;
	private JButton applyButton;
	private JButton completeMetadataButton;
	private JTextField directorySelected;
	private JPanel bottomPanel;
	private JTable descriptionTable;
	private JProgressBar progressBar;
	private JLabel label;
	private JLabel imageLabel;
	private JLabel loginLabel;
	private JPanel middlePanel;
	private JPanel topPanel;
	private JPanel imagePanel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private InputMap inputMap;
	private JScrollPane scrollPane;
	private ImageUtils imageUtils = new ImageUtils();
	private Log log = LogFactory.getLog(this.getClass());
	private Set<Metadata> metadataWithAlbum = new HashSet<Metadata>();
	private MetadataAdapter metadataAdapter = new MetadataAdapter();
	boolean tableLoaded;

	@Autowired
	private LoginWindow loginWindow;
	@Autowired
	private ViewEngineConfigurator viewEngineConfigurator;
	@Autowired
	private ControlEngineConfigurator controlEngineConfigurator;

	public MainWindow() {
		doLayout();
		getDescriptionTable().getModel().addTableModelListener(new DescriptionTableModelListener());
	}

	private void doLayout() {
		registerKeyStrokeAction();
		getFrame();
	}

	@EventMethod(Events.USER_LOGGED)
	private void onUserLogged(User currentUser) {
		getLoginLabel().setText(ApplicationState.LOGGED_AS + currentUser.getUsername());
		getSendButton().setEnabled(true);
	}

	@EventMethod(Events.USER_LOGIN_FAILED)
	private void onUserLoginFailed() {
		getLoginLabel().setText(ApplicationState.LOGIN_FAIL);
	}

	@EventMethod(Events.MUSIC_DIRECTORY_SELECTED)
	private void onMusicDirectorySelected(String path) {
		getDirectoryField().setText(path);
	}

	@EventMethod(Events.TRACKS_LOADED)
	private void onTracksLoaded() {
		getCompleteMetadataButton().setEnabled(true);
		tableLoaded = true;
	}

	@EventMethod(Events.READY_TO_COMPLETE_METADATA)
	private void onReadyToCompleteMetadata() {
		getProgressBar().setValue(0);
		getProgressBar().setVisible(true);
	}

	@EventMethod(Events.LOAD_METADATA)
	private void onLoadMetadata(Metadata metadata) {
		JTable descriptionTable = getDescriptionTable();
		int row = descriptionTable.getRowCount();
		DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
		model.addRow(new Object[] { "", "", "", "", "", "", "", "" });
		descriptionTable.setValueAt(metadata.getArtist(), row, ApplicationState.ARTIST_COLUMN);
		descriptionTable.setValueAt(metadata.getTitle(), row, ApplicationState.TITLE_COLUMN);
		descriptionTable.setValueAt(metadata.getAlbum(), row, ApplicationState.ALBUM_COLUMN);
		descriptionTable.setValueAt(metadata.getTrackNumber(), row, ApplicationState.TRACK_NUMBER_COLUMN);
		descriptionTable.setValueAt(metadata.getTotalTracks(), row, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN);
		descriptionTable.setValueAt(metadata.getCdNumber(), row, ApplicationState.CD_NUMBER_COLUMN);
		descriptionTable.setValueAt(metadata.getTotalCds(), row, ApplicationState.TOTAL_CDS_NUMBER_COLUMN);
		descriptionTable.setValueAt(ApplicationState.READY, row, ApplicationState.STATUS_COLUMN);
	}

	private void deleteALLRows(JTable descriptionTable) {
		DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	@EventMethod(Events.OPEN_ERROR)
	private void onOpenError() {
		getLabel().setText(ApplicationState.OPEN_ERROR);
	}

	private JMenuBar getMenubar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMenu());
		}
		return menuBar;
	}

	private JMenu getMenu() {
		if (menu == null) {
			menu = new JMenu(JMENU_LABEL);
			menu.setMnemonic(KeyEvent.VK_L);
			menu.add(getMenuItem());
		}
		return menu;
	}

	private JMenuItem getMenuItem() {
		if (menuItem == null) {
			menuItem = new JMenuItem(JMENU_ITEM_LABEL);
			menuItem.setName(LOGIN_MENU_ITEM);

			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					loginWindow.getFrame().setLocationRelativeTo(getFrame());
					loginWindow.getFrame().setVisible(true);
				}
			});
		}
		return menuItem;
	}

	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			bottomPanel = new JPanel();
			bottomPanel.add(getLabel());
			bottomPanel.add(getDirectoryField());
			bottomPanel.add(getProgressBar());
			bottomPanel.add(getOpenButton());
			bottomPanel.add(getSendButton());
			bottomPanel.add(getCompleteMetadataButton());
			bottomPanel.add(getApplyButton());
		}
		return bottomPanel;
	}

	private void registerKeyStrokeAction() {
		KeyStroke ctrlo = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		inputMap = getOpenButton().getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(ctrlo, CTRL_O);
		inputMap.put(enter, ENTER);
		getOpenButton().getActionMap().put(CTRL_O, new ClickAction(getOpenButton()));
		getOpenButton().getActionMap().put(ENTER, new ClickAction(getOpenButton()));
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(getTopPanel(), BorderLayout.NORTH);
			panel.add(getMiddlePanel(), BorderLayout.CENTER);
			panel.add(getBottomPanel(), BorderLayout.SOUTH);
		}
		return panel;
	}

	private JPanel getMiddlePanel() {
		if (middlePanel == null) {
			middlePanel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			middlePanel.add(getImageLabel(), c);
			c.fill = GridBagConstraints.VERTICAL;
			c.gridx = 0;
			c.gridy = 1;
			middlePanel.add(getImagePanel(), c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			middlePanel.add(getScrollPane(), c);
		}
		return middlePanel;
	}

	private JPanel getImagePanel() {
		if (imagePanel == null) {
			imagePanel = new JPanel();
			imagePanel.add(new JLabel(imageUtils.getDefaultImage()));
		}
		return imagePanel;
	}

	private JLabel getImageLabel() {
		if (imageLabel == null) {
			imageLabel = new JLabel();
		}
		return imageLabel;
	}

	private JPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new JPanel();
			topPanel.add(getLoginLabel());
		}
		return topPanel;
	}

	private JTextField getDirectoryField() {
		if (directorySelected == null) {
			directorySelected = new JTextField(DIRECTORY_SELECTED_LENGHT);
			directorySelected.setEnabled(false);
		}
		return directorySelected;
	}

	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setVisible(true);
		}
		return progressBar;
	}

	public JLabel getLabel() {
		if (label == null) {
			label = new JLabel(STATUS_LABEL);
		}
		return label;
	}

	private JLabel getLoginLabel() {
		if (loginLabel == null) {
			loginLabel = new JLabel(LOG_OUT);
		}
		return loginLabel;
	}

	public JButton getCompleteMetadataButton() {
		if (completeMetadataButton == null) {
			completeMetadataButton = new JButton(ApplicationState.COMPLETE);
			completeMetadataButton.setName(COMPLETE_BUTTON_NAME);
			completeMetadataButton.setEnabled(false);

			completeMetadataButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					resetStatus();
					new CompleteWorker();
				}

				private void resetStatus() {
					metadataWithAlbum.clear();
					for (int i = 0; i < getDescriptionTable().getModel().getRowCount(); i++) {
						getDescriptionTable().getModel().setValueAt(ApplicationState.READY, i, ApplicationState.STATUS_COLUMN);
					}
				}
			});
		}
		return completeMetadataButton;
	}

	public JButton getApplyButton() {
		if (applyButton == null) {
			applyButton = new JButton(ApplicationState.APPLY);
			applyButton.setName(APPLY_BUTTON_NAME);
			applyButton.setEnabled(false);

			applyButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new WriteWorker();
				}

			});
		}
		return applyButton;
	}

	public JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton(SEND_SCROBBLINGS);
			sendButton.setName(SEND_BUTTON_NAME);
			sendButton.setEnabled(false);

			sendButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SendWorker();
				}
			});
		}
		return sendButton;
	}

	public JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton(LOAD_FILES);
			openButton.setName(OPEN_BUTTON_NAME);
			openButton.setMnemonic(KeyEvent.VK_O);

			openButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					tableLoaded = false;
					deleteALLRows(descriptionTable);
					metadataWithAlbum.clear();
					viewEngineConfigurator.getViewEngine().send(Actions.METADATA);
				}
			});
		}
		return openButton;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getDescriptionTable());
		}
		return scrollPane;
	}

	public JTable getDescriptionTable() {
		if (descriptionTable == null) {
			descriptionTable = new DescriptionTable();
			descriptionTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					descriptionTable.setToolTipText("In order to enter a custom metadata you have to click " + "on complete button first");
				}

			});

			descriptionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(tableLoaded){
						updateImage(descriptionTable.getSelectedRow());
					}
				}

			});

			descriptionTable.getModel().addTableModelListener(new TableModelListener() {

				@Override
				public void tableChanged(TableModelEvent e) {
					if (e.getType() == TableModelEvent.UPDATE && tableLoaded && e.getColumn() != ApplicationState.STATUS_COLUMN) {
						log.info("tableChanged");
						int column = e.getColumn();
						int row = e.getFirstRow();
						String value = getDescriptionTable().getModel().getValueAt(row, column).toString();
						log.info("value: " + value);
						List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
						Metadata metadata = metadataList.get(row);
						metadataAdapter.update(metadata, column, value);
						metadataWithAlbum.add(metadata);

						getDescriptionTable().getModel().setValueAt(ApplicationState.NEW_METADATA, row, ApplicationState.STATUS_COLUMN);

						controlEngineConfigurator.getControlEngine().remove(Model.METADATA_ARTIST);
						controlEngineConfigurator.getControlEngine().set(Model.METADATA_ARTIST, metadataWithAlbum, null);
						getApplyButton().setEnabled(true);
					}
				}
			});

		}

		return descriptionTable;
	}

	private void updateImage(int selectedRow) {
		TableModel model = getDescriptionTable().getModel();
		List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);

		log.info("selectedRow: " + selectedRow);
		Metadata metadata = metadataList.get(selectedRow);
		log.info("metadata: " + ToStringBuilder.reflectionToString(metadata));

		imagePanel.removeAll();
		if (metadata.getCoverArt() != null) {
			imagePanel.add(new JLabel(imageUtils.resize(metadata.getCoverArt(), THREE_HUNDRED, THREE_HUNDRED)));
			imageLabel.setText(ApplicationState.COVER_ART_FROM_FILE);
		} else if (metadata.getLastfmCoverArt() != null) {
			imagePanel.add(new JLabel(metadata.getLastfmCoverArt()));
			imageLabel.setText(ApplicationState.COVER_ART_FROM_LASTFM);
		} else {
			imagePanel.add(new JLabel(imageUtils.getDefaultImage()));
			imageLabel.setText(ApplicationState.COVER_ART_DEFAULT);
		}
		imagePanel.invalidate();
		imagePanel.revalidate();
	}

	public Frame getFrame() {
		if (frame == null) {
			frame = new JFrame(ApplicationState.APPLICATION_NAME);
			frame.setBounds(0, 0, ApplicationState.WIDTH, ApplicationState.HEIGHT);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setJMenuBar(getMenubar());
			frame.add(getPanel());
			frame.setVisible(true);
		}
		return frame;
	}

	private class WriteWorker {

		public WriteWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					final Set<Metadata> metadataWithAlbumList = viewEngineConfigurator.getViewEngine().get(Model.METADATA_ARTIST);
					getLabel().setText(ApplicationState.WRITTING_METADATA);
					counter = 0;
					for (final Metadata metadata : metadataWithAlbumList) {
						viewEngineConfigurator.getViewEngine().request(Actions.WRITE, metadata, new ResponseCallback<ActionResult>() {

							@Override
							public void onResponse(ActionResult result) {
								log.info("Writing metadata to " + metadata.getTitle() + " w/result: " + result);
								counter++;
								String message = ApplicationState.UPDATED;
								if (result != ActionResult.UPDATED) {
									message = ApplicationState.ERROR;
								}
								getDescriptionTable().getModel().setValueAt(message, getRow(metadata), ApplicationState.STATUS_COLUMN);
								if (counter >= metadataWithAlbumList.size()) {
									resetButtonsState();
									finishingWorker();
								}
							}

							private void finishingWorker() {
								getApplyButton().setEnabled(false);
							}
						});
					}
					return true;
				}

			};
			MainWindow.this.getCompleteMetadataButton().setEnabled(false);
			getOpenButton().setEnabled(false);
			swingWorker.execute();
		}

	}

	private class CompleteWorker {

		public CompleteWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					final List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
					getLabel().setText(ApplicationState.GETTING_ALBUM);
					counter = 0;
					for (final Metadata metadata : metadataList) {
						final int i = metadataList.indexOf(metadata);
						MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.COMPLETE_ALBUM, metadata, new ResponseCallback<ActionResult>() {

							@Override
							public void onResponse(ActionResult reponse) {
								updateStatus(counter++, metadataList.size());
								log.info("response in getting album " + metadata.getTitle() + ": " + reponse);
								if (reponse.equals(ActionResult.METADATA_SUCCESS)) {
									metadataWithAlbum.add(metadata);
									getDescriptionTable().getModel().setValueAt(metadata.getAlbum(), i, ApplicationState.ALBUM_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getTrackNumber(), i, ApplicationState.TRACK_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getTotalTracks(), i, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getCdNumber(), i, ApplicationState.CD_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getTotalCds(), i, ApplicationState.TOTAL_CDS_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(ApplicationState.NEW_METADATA, i, ApplicationState.STATUS_COLUMN);
								}
								if (counter >= metadataList.size()) {
									getCoverArt();
								}
							}

							private void getCoverArt() {
								getLabel().setText(ApplicationState.GETTING_COVER_ART);
								counter = 0;
								for (final Metadata metadata : metadataList) {
									final int i = metadataList.indexOf(metadata);
									MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.COMPLETE_COVER_ART, metadata, new ResponseCallback<ActionResult>() {

										@Override
										public void onResponse(ActionResult reponse) {
											updateStatus(counter++, metadataList.size());
											log.info("response in getting coverArt " + metadata.getTitle() + ": " + reponse);
											if (reponse.equals(ActionResult.METADATA_SUCCESS)) {
												metadataWithAlbum.add(metadata);
												getDescriptionTable().getModel().setValueAt(ApplicationState.NEW_METADATA, i, ApplicationState.STATUS_COLUMN);
											}
											if (counter >= metadataList.size()) {
												afterComplete(metadataWithAlbum);
											}
										}

									});
								}

							}

						});
					}
					return true;
				}
			};

			MainWindow.this.getCompleteMetadataButton().setEnabled(false);
			getOpenButton().setEnabled(false);
			swingWorker.execute();
		}
	}

	private void afterComplete(Set<Metadata> metadataWithOutArtist) {
		if (!metadataWithOutArtist.isEmpty()) {
			controlEngineConfigurator.getControlEngine().remove(Model.METADATA_ARTIST);
			controlEngineConfigurator.getControlEngine().set(Model.METADATA_ARTIST, metadataWithOutArtist, null);
			getApplyButton().setEnabled(true);
		}
		resetButtonsState();
	}

	private void resetButtonsState() {
		getLabel().setText(ApplicationState.DONE);
		getCompleteMetadataButton().setEnabled(true);
		getOpenButton().setEnabled(true);
		getDescriptionTable().setEnabled(true);
	}

	private void updateStatus(final int i, int size) {
		int progress = ((i + 1) * 100) / size;
		getProgressBar().setValue(progress);
	};

	private class SendWorker {
		public SendWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					final List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
					counter = 0;
					for (final Metadata metadata : metadataList) {
						MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.SEND, metadata, new ResponseCallback<ActionResult>() {

							@Override
							public void onResponse(ActionResult response) {
								log.info("response on sending " + metadata.getTitle() + ": " + response);
								updateStatus(counter++, metadataList.size());
								String message;
								switch (response) {
								case SUCCESS:
									message = ApplicationState.SENT;
									break;
								case LOGGED_OUT:
									message = ApplicationState.LOGGED_OUT;
									break;
								case SESSIONLESS:
									message = ApplicationState.SESSIONLESS;
									break;
								default:
									message = ApplicationState.ERROR;
									MainWindow.this.getLabel().setText(ApplicationState.NETWORK_ERROR);
								}
								getDescriptionTable().getModel().setValueAt(message, getRow(metadata), ApplicationState.STATUS_COLUMN);
							}

						});
					}
					return true;
				}

				@Override
				public void done() {
					getLabel().setText(ApplicationState.DONE);
					getCompleteMetadataButton().setEnabled(true);
					getSendButton().setEnabled(true);
					getOpenButton().setEnabled(true);
				}
			};
			swingWorker.execute();
			getCompleteMetadataButton().setEnabled(false);
			getSendButton().setEnabled(false);
			getOpenButton().setEnabled(false);
			getProgressBar().setVisible(true);
			getLabel().setText(ApplicationState.WORKING);
		}
	}

	private int getRow(Metadata metadataTarget) {
		TableModel model = getDescriptionTable().getModel();
		List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);

		for (int i = 0; i < model.getRowCount(); i++) {
			String artist = (String) model.getValueAt(i, 0);
			String title = (String) model.getValueAt(i, 1);
			if (artist.equals(metadataTarget.getArtist()) && title.equals(metadataTarget.getTitle())) {
				return i;
			}
		}

		return 0;
	}

	private class ClickAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private JButton button;

		public ClickAction(JButton button) {
			this.button = button;
		}

		public void actionPerformed(ActionEvent e) {
			button.doClick();
		}
	}

	private class DescriptionTableModelListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			if (MainWindow.this.getCompleteMetadataButton().getText().equals(ApplicationState.APPLY) && e.getColumn() != ApplicationState.STATUS_COLUMN) {
				int lastRow = e.getLastRow();
				DefaultTableModel model = (DefaultTableModel) e.getSource();
				String artist = (String) model.getValueAt(lastRow, 0);
				String trackName = (String) model.getValueAt(lastRow, 1);
				String album = (String) model.getValueAt(lastRow, 2);
			}
		}
	}

}
