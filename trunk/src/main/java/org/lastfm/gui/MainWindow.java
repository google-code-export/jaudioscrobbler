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

public class MainWindow {
	private static final int WINDOW_WIDTH = 750;
	private static final int WINDOW_HEIGHT = 500;
	private JFrame frame;
	private JPanel panel;
	
	private JButton openButton;
	private JButton sendButton;
	private JButton completeMetadataButton;
	
	
	private JTextField textField;
	private JTable table;
	private JPanel bottomPanel;
	
	
	private JProgressBar progressBar;
	private JLabel label;
	
	public MainWindow() {
		doLayout();
	}
	
	private void doLayout() {
		frame = new JFrame("JAudioScrobbler");
		panel = new JPanel();
		bottomPanel = new JPanel();
		
		openButton = new JButton("Open");
		sendButton = new JButton("Send");
		completeMetadataButton = new JButton("Complete");
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		table = new DescriptionTable();
		ApplicationState.setDescriptionTable(table);
		label = new JLabel("Status");
		textField = new JTextField(20);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.NORTH);
		bottomPanel.add(label);
		bottomPanel.add(textField);
		bottomPanel.add(progressBar);
		
		bottomPanel.add(openButton);
		bottomPanel.add(sendButton);
		bottomPanel.add(completeMetadataButton);

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
