package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
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
import javax.swing.text.JTextComponent;

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
	
	
	private JProgressBar progressBar;
	private JLabel label;
	
	public MainWindow() {
		doLayout();
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

	public JPanel getPanel() {
		return panel;
	}

	public JTextField getDirectoryField() {
		return textField;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public JLabel getLabel() {
		return label;
	}

	public void addOpenListener(ActionListener openListener) {
		openButton.addActionListener(openListener);
	}
	
	public void addSendListener(ActionListener sendListener){
		sendButton.addActionListener(sendListener);
	}
}
