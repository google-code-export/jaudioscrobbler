package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.lastfm.ApplicationState;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands UI components for logging window
 */

public class LoginWindow {
	private JButton sendButton;
	private JTextField userName;
	private JTextField password;
	private JFrame frame;
	private ViewEngineConfigurator configurator;
	
	@Autowired
	public void setAddConfigurator(ViewEngineConfigurator configurator) {
		this.configurator = configurator;
	}
	
	public LoginWindow() {
		doLayout();
	}
	
	private void doLayout() {
		frame = new JFrame();
		userName = new JTextField();
		userName.setName("userName");
		
		password = new JPasswordField();
		password.setName("password");
		
		frame.setBounds(300, 300, 300, 122);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(userName, BorderLayout.NORTH);
		panel.add(password, BorderLayout.CENTER);
		panel.add(getSendButton(), BorderLayout.SOUTH);
		
		frame.add(panel);
		frame.setVisible(false);
		
		getSendButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder credentials = packCredentials();
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, credentials.toString());
			}

			private StringBuilder packCredentials() {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(userName.getText());
				stringBuilder.append(ApplicationState.DELIMITER);
				stringBuilder.append(password.getText());
				return stringBuilder;
			}
		});
	}
	
	public void addLoginListener(ActionListener loginListener){
		sendButton.addActionListener(loginListener);
	}
	
	public void addKeyListener(KeyListener keyListener){
		password.addKeyListener(keyListener);
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

	public JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton("Login");
			sendButton.setName("sendButton");
		}
		return sendButton;
	}

}
