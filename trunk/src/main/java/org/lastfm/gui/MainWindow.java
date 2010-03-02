package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class MainWindow {
	private static final String SEND_SCROBBLINGS = "Send";
	private static final String LOAD_FILES = "Open";
	private static final String APPLICATION_NAME = "JAudioScrobbler";
	private static final String LOG_OUT = "logged out";
	public static final String COMPLETE_BUTTON = "Complete";
	public static final String APPLY = "Apply";
	private static final int WINDOW_WIDTH = 750;
	private static final int WINDOW_HEIGHT = 500;
	private JFrame frame;
	private JPanel panel;
	
	private JButton openButton;
	private JButton sendButton;
	private JButton completeMetadataButton;
	
	
	private JTextField textField;
	private JPanel bottomPanel;
	public JTable table;
	
	
	private JProgressBar progressBar;
	private JLabel label;
	private JLabel loginLabel;
	private JPanel topPanel;
	
	public MainWindow() {
		doLayout();
	}
	
	private void doLayout() {
		frame = new JFrame(APPLICATION_NAME);
		panel = new JPanel();
		bottomPanel = new JPanel();
		topPanel = new JPanel();
		
		loginLabel = new JLabel(LOG_OUT);
		topPanel.add(loginLabel);
		
		openButton = new JButton(LOAD_FILES);
		sendButton = new JButton(SEND_SCROBBLINGS);
		completeMetadataButton = new JButton(COMPLETE_BUTTON);
		
		sendButton.setEnabled(false);
		completeMetadataButton.setEnabled(false);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		table = new DescriptionTable();
		label = new JLabel("Status");
		textField = new JTextField(20);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		panel.setLayout(new BorderLayout());
		
		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		bottomPanel.add(label);
		bottomPanel.add(textField);
		bottomPanel.add(progressBar);
		
		bottomPanel.add(openButton);
		bottomPanel.add(sendButton);
		bottomPanel.add(completeMetadataButton);

		panel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.add(panel);
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public JPanel getPanel() {
		return panel;
	}
	
	public JTable getDescritionTable() {
		return table;
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
	
	public JLabel getLoginLabel() {
		return loginLabel;
	}
	
	public JButton getCompleteButton() {
		return completeMetadataButton;
	}
	
	public JButton getSendButton() {
		return sendButton;
	}
	
	public JButton getOpenButton() {
		return openButton;
	}

	public void addOpenListener(ActionListener openListener) {
		openButton.addActionListener(openListener);
	}
	
	public void addSendListener(ActionListener sendListener){
		sendButton.addActionListener(sendListener);
	}
	
	public void addCompleteListener(ActionListener completeListener){
		completeMetadataButton.addActionListener(completeListener);
	}

	public Frame getFrame() {
		return frame;
	}
}
