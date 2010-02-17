package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.lastfm.ApplicationState;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class MainWindow {
	private static final String SEND_SCROBBLINGS = "Send";
	private static final String LOAD_FILES = "Open";
	private static final String APPLY_METADATA = "Complete";
	private static final String APPLICATION_NAME = "JAudioScrobbler";
	private static final String LOG_OUT = "logged out";
	private static final int WINDOW_WIDTH = 750;
	private static final int WINDOW_HEIGHT = 500;
	private JFrame frame;
	private JPanel panel;
	
	public JButton openButton;
	public JButton sendButton;
	public JButton completeMetadataButton;
	
	
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
		completeMetadataButton = new JButton(APPLY_METADATA);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		table = new DescriptionTable();
		ApplicationState.setDescriptionTable(table);
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
	
	public JTable getTable() {
		return table;
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
}
