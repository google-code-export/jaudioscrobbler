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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationConextFactory;
import org.lastfm.action.Actions;
import org.lastfm.action.control.DefaultEngine;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands UI components for logging window
 */

public class LoginWindow {
	public JButton sendButton;
	public JTextField userName;
	public JTextField password;
	JFrame frame;
	private Log log = LogFactory.getLog(this.getClass());
	
	private ViewEngineConfigurator configurator;
	
	public LoginWindow() {
		doLayout();
	}
	
	private void doLayout() {
		frame = new JFrame();
		userName = new JTextField();
		userName.setName("userName");
		
		password = new JPasswordField();
		password.setName("password");
		
		sendButton = new JButton("Login");
		sendButton.setName("sendButton");
		
		frame.setBounds(300, 300, 300, 122);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(userName, BorderLayout.NORTH);
		panel.add(password, BorderLayout.CENTER);
		panel.add(sendButton, BorderLayout.SOUTH);
		
		frame.add(panel);
		frame.setVisible(false);
		
		sendButton.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("Sending Login message");
				ConfigurableApplicationContext context = ApplicationConextFactory.getApplicationContext();
				configurator = context.getBean(ViewEngineConfigurator.class);
				DefaultEngine defaultEngine = context.getBean(DefaultEngine.class);
				defaultEngine.start();
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, userName.getText() + "|" + password.getText());
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
		return sendButton;
	}

}
