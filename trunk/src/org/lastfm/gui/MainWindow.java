package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.ApplicationState;
import org.lastfm.FileUtils;
import org.lastfm.HelperScrobbler;
import org.lastfm.InvalidId3VersionException;
import org.lastfm.Metadata;

public class MainWindow {
	private static final int WINDOW_WIDTH = 750;
	private static final int WINDOW_HEIGHT = 500;
	private JFrame frame;
	private JPanel panel;
	private JButton openButton;
	private JFileChooser fileChooser;
	private JTextField textField;
	private JTable table;
	private JPanel bottomPanel;
	private JButton sendButton;
	
	private List<Metadata> metadataList;
	private JProgressBar progressBar;
	private JLabel label;
	
	
	public MainWindow() {
		doLayout();
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int selection = fileChooser.showOpenDialog(panel);
				if(selection == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					textField.setText(file.getAbsolutePath());
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

			private void showFiles(File root) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException, InterruptedException {
				metadataList = new FileUtils().getFileList(root);
			}
		});
		
		sendButton.addActionListener(new ActionListener() {
			private HelperScrobbler scrobbler;

			@Override
			public void actionPerformed(ActionEvent e) {
				progressBar.setVisible(true);
				scrobbler = new HelperScrobbler();
				
				SwingWorker swingWorker = new SwingWorker<Boolean, Integer>(){
					int i=1;
					@Override
					protected Boolean doInBackground() throws Exception {
						try {
							if(metadataList!=null){
								for(Metadata metadata: metadataList){
									updateStatus(i);
									scrobbler.send(metadata);
									i++;
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
				label.setText("Done");
			}
		});
	}
	
	void updateStatus(final int i) {
        Runnable doSetProgressBarValue = new Runnable() {
            public void run() {
                progressBar.setValue(i * 100 / metadataList.size());
            }
        };
        SwingUtilities.invokeLater(doSetProgressBarValue);
    }

	private void doLayout() {
		frame = new JFrame("JAudioScrobbler");
		panel = new JPanel();
		bottomPanel = new JPanel();
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		table = new DescriptionTable();
		ApplicationState.setDescriptionTable(table);
		label = new JLabel("Status");
		fileChooser = new JFileChooser();
		textField = new JTextField(20);
		openButton = new JButton("Open");
		sendButton = new JButton("Send");
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.NORTH);
		bottomPanel.add(label, BorderLayout.EAST);
		bottomPanel.add(progressBar, BorderLayout.EAST);
		bottomPanel.add(textField,BorderLayout.EAST);
		bottomPanel.add(openButton,BorderLayout.WEST);
		bottomPanel.add(sendButton,BorderLayout.WEST);
		panel.add(bottomPanel);
		
		frame.add(panel);
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
