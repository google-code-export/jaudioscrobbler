package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow {
	public JButton sendButton;
	public JTextField userName;
	public JTextField password;
	JFrame frame;
	
	public LoginWindow() {
		doLayout();
	}
	
	private void doLayout() {
		frame = new JFrame();
		userName = new JTextField();
		password = new JPasswordField();
		sendButton = new JButton("Login");
		
		frame.setBounds(300, 300, 300, 122);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(userName, BorderLayout.NORTH);
		panel.add(password, BorderLayout.CENTER);
		panel.add(sendButton, BorderLayout.SOUTH);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void addLoginListener(ActionListener loginListener){
		sendButton.addActionListener(loginListener);
	}

	public JTextField getUsername() {
		return userName;
	}

	public JTextField getPassword() {
		return password;
	}

	public JFrame getFrame() {
		return frame;
	}

}
