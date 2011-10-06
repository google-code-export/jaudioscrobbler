package org.lastfm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.lastfm.ApplicationState;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.event.EventMethod;
import org.lastfm.event.Events;
import org.lastfm.model.User;
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
	private JPanel panel;
	private static final String USERNAME_TEXTFIELD_NAME = "username";
	private static final String PASSWORD_TEXTFIELD_NAME = "password";
	private static final String SEND_BUTTON_LABEL = "Login";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private static final Rectangle FRAME_BOUNDS = new Rectangle(300, 300, 300, 122);
	private static final int TEXT_COLUMNS = 10;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
	@Autowired
	private ViewEngineConfigurator configurator;
	
	public LoginWindow() {
		doLayout();
	}
	
	private void doLayout() {
		getFrame().add(getPanel());
		getFrame().setVisible(false);
		
		getSendButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, new User(usernameTextfield.getText(), passwordTextfield.getText()));
			}
		});
		
		getSendButton().addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, new User(usernameTextfield.getText(), passwordTextfield.getText()));
			}
		});
		
		getPasswordTextfield().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					configurator.getViewEngine().sendValueAction(Actions.LOGIN, new User(usernameTextfield.getText(), passwordTextfield.getText()));
				}
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
			panel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			panel.add(getUserNameLabel(), c);
			c.gridx = 1;
			c.gridy = 0;
			panel.add(getUsernameTextfield(), c);
			c.gridx = 0;
			c.gridy = 1;
			panel.add(getPasswordLabel(), c);
			c.gridx = 1;
			c.gridy = 1;
			panel.add(getPasswordTextfield(), c);
			c.insets = new Insets(0, 40, 0, 0);
			c.gridx = 1;
			c.gridy = 2;
			panel.add(getSendButton(), c);
		}
		return panel;
	}

	private JLabel getPasswordLabel() {
		if(passwordLabel == null){
			passwordLabel = new JLabel();
			passwordLabel.setText(ApplicationState.PASSWORD_LABEL);
		}
		return passwordLabel;
	}

	private JLabel getUserNameLabel() {
		if(usernameLabel == null){
			usernameLabel = new JLabel();
			usernameLabel.setText(ApplicationState.USERNAME_LABEL);
			
		}
		return usernameLabel;
	}

	public void addLoginListener(ActionListener loginListener){
		sendButton.addActionListener(loginListener);
	}
	
	public void addKeyListener(KeyListener keyListener){
		passwordTextfield.addKeyListener(keyListener);
	}

	private JTextField getUsernameTextfield() {
		if(usernameTextfield == null){
			usernameTextfield = new JTextField(TEXT_COLUMNS);
			usernameTextfield.setName(USERNAME_TEXTFIELD_NAME);
		}
		return usernameTextfield;
	}

	private JTextField getPasswordTextfield() {
		if(passwordTextfield == null){
			passwordTextfield = new JPasswordField(TEXT_COLUMNS);
			passwordTextfield.setName(PASSWORD_TEXTFIELD_NAME);
		}
		return passwordTextfield;
	}

	public JFrame getFrame() {
		if(frame == null){
			frame = new JFrame();
			frame.setBounds(FRAME_BOUNDS);
			frame.setResizable(false);
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
