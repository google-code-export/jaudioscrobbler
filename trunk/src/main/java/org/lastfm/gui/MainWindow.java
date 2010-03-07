package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * 
 * @author Jose Luis De la Cruz
 * 
 */

public class MainWindow {
	static final String CTRL_O = "CTRL+O";
	static final String ENTER = "ENTER";
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

	JButton openButton;
	JButton sendButton;
	JButton completeMetadataButton;

	JTextField directorySelected;
	
	private JPanel bottomPanel;
	public JTable table;

	private JProgressBar progressBar;
	private JLabel label;
	private JLabel loginLabel;
	private JPanel topPanel;
	InputMap inputMap;
	

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
		openButton.setMnemonic(KeyEvent.VK_O);
		registerKeyStrokeAction();

		sendButton = new JButton(SEND_SCROBBLINGS);
		completeMetadataButton = new JButton(COMPLETE_BUTTON);

		sendButton.setEnabled(false);
		completeMetadataButton.setEnabled(false);

		progressBar = new JProgressBar();
		progressBar.setVisible(false);

		table = new DescriptionTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				table.setToolTipText("In order to enter a custom metadata you have to click "
						+ "on complete button first");
			}
		});
		label = new JLabel("Status");
		directorySelected = new JTextField(20);
		directorySelected.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);

		panel.setLayout(new BorderLayout());

		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		bottomPanel.add(label);
		bottomPanel.add(directorySelected);
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

	private void registerKeyStrokeAction() {
		KeyStroke ctrlo = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		inputMap = openButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(ctrlo, CTRL_O);
		inputMap.put(enter, ENTER);
		openButton.getActionMap().put(CTRL_O, new ClickAction(openButton));
		openButton.getActionMap().put(ENTER, new ClickAction(openButton));
	}

	public JPanel getPanel() {
		return panel;
	}

	public JTable getDescriptionTable() {
		return table;
	}

	public JTextField getDirectoryField() {
		return directorySelected;
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

	public void addSendListener(ActionListener sendListener) {
		sendButton.addActionListener(sendListener);
	}

	public void addCompleteListener(ActionListener completeListener) {
		completeMetadataButton.addActionListener(completeListener);
	}

	public Frame getFrame() {
		return frame;
	}

	public class ClickAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton button;

		public ClickAction(JButton button) {
			this.button = button;
		}

		public void actionPerformed(ActionEvent e) {
			button.doClick();
		}
	}
}
