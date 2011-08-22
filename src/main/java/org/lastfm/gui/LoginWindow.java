package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.lastfm.action.Actions;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.event.EventMethod;
import org.lastfm.event.Events;
import org.lastfm.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands UI components for logging window
 */

public class LoginWindow {
	private JButton sendButton;
	private JTextField usernameTextfield;
	private JTextField passwordTextfield;
	private JFrame frame;
	private ViewEngineConfigurator configurator;
	private JPanel panel;
	private static final String USERNAME_TEXTFIELD_NAME = "userName";
	private static final String PASSWORD_TEXTFIELD_NAME = "password";
	private static final String SEND_BUTTON_LABEL = "Login";
	private static final String SEND_BUTTON_NAME = "sendButton";
	
	@Autowired
	public void setAddConfigurator(ViewEngineConfigurator configurator) {
		this.configurator = configurator;
	}
	
	public LoginWindow() {
		doLayout();
	}
	
	private void doLayout() {
		getFrame().add(getPanel());
		getFrame().setVisible(false);
		
		getSendButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, new Credentials(usernameTextfield.getText(), passwordTextfield.getText()));
			}
		});
		
		getSendButton().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, new Credentials(usernameTextfield.getText(), passwordTextfield.getText()));
			}
		});
	}
	
	@SuppressWarnings("unused")
	@EventMethod(Events.USER_LOGGED)
	private void onUserLogged(){
		this.getFrame().dispose();
	}
	
	private JPanel getPanel() {
		if(panel == null){
			panel = new JPanel(new BorderLayout());
			panel.add(getUsername(), BorderLayout.NORTH);
			panel.add(getPassword(), BorderLayout.CENTER);
			panel.add(getSendButton(), BorderLayout.SOUTH);
		}
		return panel;
	}

	public void addLoginListener(ActionListener loginListener){
		sendButton.addActionListener(loginListener);
	}
	
	public void addKeyListener(KeyListener keyListener){
		passwordTextfield.addKeyListener(keyListener);
	}

	private JTextField getUsername() {
		if(usernameTextfield == null){
			usernameTextfield = new JTextField();
			usernameTextfield.setName(USERNAME_TEXTFIELD_NAME);
		}
		return usernameTextfield;
	}

	private JTextField getPassword() {
		if(passwordTextfield == null){
			passwordTextfield = new JPasswordField();
			passwordTextfield.setName(PASSWORD_TEXTFIELD_NAME);
		}
		return passwordTextfield;
	}

	public JFrame getFrame() {
		if(frame == null){
			frame = new JFrame();
			frame.setBounds(300, 300, 300, 122);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		return frame;
	}

	private JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton(SEND_BUTTON_LABEL);
			sendButton.setName(SEND_BUTTON_NAME);
		}
		return sendButton;
	}
}
