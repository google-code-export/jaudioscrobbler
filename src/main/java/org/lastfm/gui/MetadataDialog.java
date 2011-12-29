package org.lastfm.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;
import org.lastfm.dnd.ImageDropListener;
import org.lastfm.dnd.MainFrameDragOverListener;
import org.lastfm.dnd.MultiLayerDropTargetListener;
import org.lastfm.event.Events;
import org.lastfm.model.MetadataValues;
import org.lastfm.observ.ObservValue;
import org.lastfm.observ.Observer;

public class MetadataDialog extends AllDialog {
	private static final long serialVersionUID = 4326045585716235724L;
	private static final Rectangle CONTENT_PANEL_BOUNDS = new Rectangle(0, 0, 388, 337);
	private static final Rectangle IMAGE_BOUNDS = new Rectangle(123, 10, 150, 150);
	private static final Rectangle ALBUM_TEXTFIELD_BOUNDS = new Rectangle(123, 170, 200, 22);
	private static final Rectangle GENRE_TEXTFIELD_BOUNDS = new Rectangle(123, 190, 200, 22);
	private static final Rectangle TRACKS_TEXTFIELD_BOUNDS = new Rectangle(123, 210, 120, 22);
	private static final Rectangle CD_TEXTFIELD_BOUNDS = new Rectangle(123, 230, 120, 22);
	private static final Rectangle CDS_TEXTFIELD_BOUNDS = new Rectangle(123, 250, 120, 22);
	private static final Rectangle IMAGE_LABEL_BOUNDS = new Rectangle(24, 10, 226, 18);
	private static final Rectangle ALBUM_LABEL_BOUNDS = new Rectangle(24, 170, 226, 18);
	private static final Rectangle GENRE_LABEL_BOUNDS = new Rectangle(24, 190, 226, 18);
	private static final Rectangle TRACKS_LABEL_BOUNDS = new Rectangle(24, 210, 226, 18);
	private static final Rectangle CD_LABEL_BOUNDS = new Rectangle(24, 230, 226, 18);
	private static final Rectangle CDS_LABEL_BOUNDS = new Rectangle(24, 250, 226, 18);
	private static final Rectangle SEND_BUTTON_BOUNDS = new Rectangle(200, 290, 80, 22);
	private static final Rectangle CANCEL_BUTTON_BOUNDS = new Rectangle(109, 290, 80, 22);
	private static final String ALBUM_INPUT = "albumInput";
	private static final String GENRE_INPUT = "genreInput";
	private static final String TRACKS_INPUT = "tracksInput";
	private static final String CD_INPUT = "cdInput";
	private static final String CDS_INPUT = "cdsInput";
	private static final String IMAGE_LABEL = "imageLabel";
	private static final String ALBUM_LABEL = "albumLabel";
	private static final String GENRE_LABEL = "genreLabel";
	private static final String TRACKS_LABEL = "tracksLabel";
	private static final String CD_LABEL = "cdLabel";
	private static final String CDS_LABEL = "cdsLabel";
	private static final String BUTTON_NAME = "buttonOk";
	private static final String APPLY = "Apply";
	private static final String IMAGE = "Cover Art";
	private static final String ALBUM = "Album";
	private static final String GENRE = "Genre";
	private static final String TRACKS = "#Trks";
	private static final String CANCEL = "Cancel";
	private static final String CD = "#CD";
	private static final String CDS = "#CDs";
	private JPanel contentPanel;
	private JTextField albumTextField;
	private JTextField genreTextField;
	private JTextField tracksTextField;
	private JTextField cdTextField;
	private JTextField cdsTextField;
	private JButton sendButton;
	private JButton cancelButton;
	private final String message;
	private JLabel imageLabel;
	private JLabel albumLabel;
	private JLabel genreLabel;
	private JLabel tracksLabel;
	private JLabel cdLabel;
	private JLabel cdsLabel;
	private JPanel imagePanel;
	private ImageIcon imageIcon;
	private final ControlEngineConfigurator configurator;

	public MetadataDialog(JFrame frame, ControlEngineConfigurator controlEngineConfigurator, String message) {
		super(frame, true, message);
		this.configurator = controlEngineConfigurator;
		this.message = message;
		initializeContentPane();
		getTitleLabel().setText(dialogTitle());
		MultiLayerDropTargetListener multiLayerDropTargetListener = new MultiLayerDropTargetListener();
		setDragAndDrop(multiLayerDropTargetListener);
		setVisible(true);
	}
	
	private void setDragAndDrop(MultiLayerDropTargetListener multiLayerDropTargetListener) {
		setDropTarget(new DropTarget(this, multiLayerDropTargetListener));
		multiLayerDropTargetListener.addDragListener(this, new MainFrameDragOverListener(this));
		ImageDropListener listener = new ImageDropListener(new ImagePanel());
		listener.onDropped().add(new Observer<ObservValue<ImagePanel>>() {

			@Override
			public void observe(ObservValue<ImagePanel> t) {
				ImagePanel value = t.getValue();
				Image image = value.getImage();
				imageIcon = new ImageIcon(image);
				JLabel imageLabel = new JLabel(imageIcon);
				imagePanel.add(imageLabel);
				MetadataDialog.this.invalidate();
				MetadataDialog.this.revalidate();
			}
		});
		multiLayerDropTargetListener.addDropListener(imagePanel, listener);
	}
	
	@Override
	String dialogTitle() {
		return message;
	}

	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(null);
			contentPanel.setBounds(CONTENT_PANEL_BOUNDS);
			contentPanel.add(getImageLabel());
			contentPanel.add(getImagePanel());
			contentPanel.add(getAlbumLabel());
			contentPanel.add(getAlbumTextField());
			contentPanel.add(getGenreLabel());
			contentPanel.add(getGenreTextField());
			contentPanel.add(getTracksLabel());
			contentPanel.add(getTracksTextField());
			contentPanel.add(getCdLabel());
			contentPanel.add(getCdTextField());
			contentPanel.add(getCdsLabel());
			contentPanel.add(getCdsTextField());
			contentPanel.add(getSendButton());
			contentPanel.add(getCancelButton());
		}
		return contentPanel;
	}
	
	private JLabel getImageLabel() {
		if(imageLabel == null){
			imageLabel = new JLabel();
			imageLabel.setBounds(IMAGE_LABEL_BOUNDS);
			imageLabel.setName(IMAGE_LABEL);
			imageLabel.setText(IMAGE);
			imageLabel.requestFocus();
		}
		return imageLabel;
	}

	private JPanel getImagePanel() {
		if(imagePanel == null){
			imagePanel = new JPanel();
			imagePanel.setBounds(IMAGE_BOUNDS);
			imagePanel.setBackground(new Color(255,255,255));
		}
		return imagePanel;
	}

	@Override
	JComponent getContentComponent() {
		return getContentPanel();
	}

	private JLabel getCdsLabel() {
		if (cdsLabel == null) {
			cdsLabel = new JLabel();
			cdsLabel.setBounds(CDS_LABEL_BOUNDS);
			cdsLabel.setName(CDS_LABEL);
			cdsLabel.setText(CDS);
			cdsLabel.requestFocus();
		}
		return cdsLabel;
	}

	private JTextField getCdsTextField() {
		if (cdsTextField == null) {
			cdsTextField = new JTextField();
			cdsTextField.setBounds(CDS_TEXTFIELD_BOUNDS);
			cdsTextField.setName(CDS_INPUT);
		}
		return cdsTextField;
	}

	private JLabel getCdLabel() {
		if (cdLabel == null) {
			cdLabel = new JLabel();
			cdLabel.setBounds(CD_LABEL_BOUNDS);
			cdLabel.setName(CD_LABEL);
			cdLabel.setText(CD);
			cdLabel.requestFocus();
		}
		return cdLabel;
	}

	private JTextField getCdTextField() {
		if (cdTextField == null) {
			cdTextField = new JTextField();
			cdTextField.setBounds(CD_TEXTFIELD_BOUNDS);
			cdTextField.setName(CD_INPUT);
		}
		return cdTextField;
	}

	private JLabel getTracksLabel() {
		if (tracksLabel == null) {
			tracksLabel = new JLabel();
			tracksLabel.setBounds(TRACKS_LABEL_BOUNDS);
			tracksLabel.setName(TRACKS_LABEL);
			tracksLabel.setText(TRACKS);
			tracksLabel.requestFocus();
		}
		return tracksLabel;
	}

	private JTextField getTracksTextField() {
		if (tracksTextField == null) {
			tracksTextField = new JTextField();
			tracksTextField.setBounds(TRACKS_TEXTFIELD_BOUNDS);
			tracksTextField.setName(TRACKS_INPUT);
		}
		return tracksTextField;
	}
	
	private JLabel getAlbumLabel() {
		if (albumLabel == null) {
			albumLabel = new JLabel();
			albumLabel.setBounds(ALBUM_LABEL_BOUNDS);
			albumLabel.setName(ALBUM_LABEL);
			albumLabel.setText(ALBUM);
			albumLabel.requestFocus();
		}
		return albumLabel;
	}
	
	private JTextField getAlbumTextField() {
		if (albumTextField == null) {
			albumTextField = new JTextField();
			albumTextField.setBounds(ALBUM_TEXTFIELD_BOUNDS);
			albumTextField.setName(ALBUM_INPUT);
		}
		return albumTextField;
	}

	private JLabel getGenreLabel() {
		if (genreLabel == null) {
			genreLabel = new JLabel();
			genreLabel.setBounds(GENRE_LABEL_BOUNDS);
			genreLabel.setName(GENRE_LABEL);
			genreLabel.setText(GENRE);
			genreLabel.requestFocus();
		}
		return genreLabel;
	}
	
	private JTextField getGenreTextField() {
		if (genreTextField == null) {
			genreTextField = new JTextField();
			genreTextField.setBounds(GENRE_TEXTFIELD_BOUNDS);
			genreTextField.setName(GENRE_INPUT);
			genreTextField.addFocusListener(new FocusListener());
			genreTextField.addKeyListener(new KeyListener());
		}
		return genreTextField;
	}
	
	private JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton();
			sendButton.setBounds(SEND_BUTTON_BOUNDS);
			sendButton.setName(BUTTON_NAME);
			sendButton.setText(APPLY);
			sendButton.setEnabled(true);
			getRootPane().setDefaultButton(sendButton);
			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sendButton.setEnabled(false);
					MetadataValues metadataValues = new MetadataValues();
					metadataValues.setCoverart(imageIcon);
					metadataValues.setAlbum(getAlbumTextField().getText());
					metadataValues.setGenre(getGenreTextField().getText());
					metadataValues.setTracks(getTracksTextField().getText());
					metadataValues.setCd(getCdTextField().getText());
					metadataValues.setCds(getCdsTextField().getText());
					configurator.getControlEngine().fireEvent(Events.READY_TO_APPLY, new ValueEvent<MetadataValues>(metadataValues));
					closeDialog();
				}
			});
		}
		return sendButton;
	}
	
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(CANCEL_BUTTON_BOUNDS);
			cancelButton.setName(BUTTON_NAME);
			cancelButton.setText(CANCEL);
			cancelButton.addActionListener(new CloseListener());
		}
		return cancelButton;
	}
	
	private final class KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getSource().equals(genreTextField)) {
				if (genreTextField.getText().length() > 0) {
					getSendButton().setEnabled(true);
				}
			}
			if (getSendButton().isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER) {
				getSendButton().doClick();
			}
		}
	}

	private final class FocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			if (e.getSource().equals(genreTextField)) {
				if (!genreTextField.getText().equals("")) {
					getSendButton().setEnabled(true);
				}
			}
		}
	}

}
