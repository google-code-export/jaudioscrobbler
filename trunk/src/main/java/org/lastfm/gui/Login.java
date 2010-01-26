package org.lastfm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;

import org.lastfm.ApplicationState;
import org.lastfm.LoginController;

public class Login {
	JButton sendButton;
	JTextField userName;
	JTextField password;
	JFrame frame;
	
	public Login() {
		doLayout();
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = -1;
				String usernm = userName.getText();
				String passwd = password.getText();
				
				LoginController controller = new LoginController();
				try {
					result = controller.login(usernm, passwd);
					if(result == ApplicationState.OK){
						ApplicationState.userName = userName.getText();
						ApplicationState.password = password.getText();
						frame.dispose();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				
			}
		});
		
	}
	
	private void doLayout() {
		frame = new JFrame();
		userName = new JTextField();
		password = new JPasswordField();
		sendButton = new JButton("Login");
		
		frame.setBounds(300, 300, 300, 105);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(userName, BorderLayout.NORTH);
		panel.add(password, BorderLayout.CENTER);
		panel.add(sendButton, BorderLayout.SOUTH);
		
		frame.add(panel);
		frame.setVisible(true);
	}

}
