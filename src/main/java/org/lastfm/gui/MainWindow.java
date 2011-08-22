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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.lastfm.ApplicationState;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.event.EventMethod;
import org.lastfm.event.Events;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A principal JAudioScrobbler principal window
 */

public class MainWindow {
	private static final String JMENU_ITEM_LABEL = "Sign in Last.fm";
	private static final String JMENU_LABEL = "Last.fm";
	private static final int DIRECTORY_SELECTED_LENGHT = 20;
	private static final String STATUS_LABEL = "Status";
	static final String CTRL_O = "CTRL+O";
	static final String ENTER = "ENTER";
	private static final String LOGIN_MENU_ITEM = "loginMenuItem";
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
	public JTable descriptionTable;

	private JProgressBar progressBar;
	private JLabel label;
	private JLabel loginLabel;
	private JPanel topPanel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	InputMap inputMap;
	private JScrollPane scrollPane;
	private ViewEngineConfigurator configurator;
	

	public MainWindow() {
		doLayout();
	}
	
	@Autowired
	public void setAddConfigurator(ViewEngineConfigurator configurator) {
		this.configurator = configurator;
	}

	private void doLayout() {
		registerKeyStrokeAction();
		getFrame();
	}
	
	@SuppressWarnings("unused")
	@EventMethod(Events.USER_LOGGED)
	private void onUserLogged(){
		getLoginLabel().setText(ApplicationState.LOGGED_AS + ApplicationState.username);
		getSendButton().setEnabled(true);
	}
	
	@SuppressWarnings("unused")
	@EventMethod(Events.USER_LOGIN_FAILED)
	private void onUserLoginFailed(){
		getLoginLabel().setText(ApplicationState.LOGIN_FAIL);
	}

	private JMenuBar getMenubar() {
		if(menuBar == null){
			menuBar = new JMenuBar();
			menuBar.add(getMenu());
		}
		return menuBar;
	}

	private JMenu getMenu() {
		if(menu == null){
			menu = new JMenu(JMENU_LABEL);
			menu.setMnemonic(KeyEvent.VK_L);
			menu.add(getMenuItem());
		}
		return menu;
	}
	
	

	private JMenuItem getMenuItem() {
		if(menuItem == null){
			menuItem = new JMenuItem(JMENU_ITEM_LABEL);
			menuItem.setName(LOGIN_MENU_ITEM);
			
			menuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					configurator.getViewEngine().send(Actions.LASTFM);
				}
			});
		}
		return menuItem;
	}

	private JPanel getBottomPanel() {
		if(bottomPanel == null){
			bottomPanel = new JPanel();
			bottomPanel.add(getLabel());
			bottomPanel.add(getDirectoryField());
			bottomPanel.add(getProgressBar());
			bottomPanel.add(getOpenButton());
			bottomPanel.add(getSendButton());
			bottomPanel.add(getCompleteMetadataButton());
		}
		return bottomPanel;
	}

	private void registerKeyStrokeAction() {
		KeyStroke ctrlo = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		inputMap = getOpenButton().getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(ctrlo, CTRL_O);
		inputMap.put(enter, ENTER);
		getOpenButton().getActionMap().put(CTRL_O, new ClickAction(getOpenButton()));
		getOpenButton().getActionMap().put(ENTER, new ClickAction(getOpenButton()));
	}

	public JPanel getPanel() {
		if(panel == null){
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(getTopPanel(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
			panel.add(getBottomPanel(), BorderLayout.SOUTH);
		}
		return panel;
	}

	
	private JPanel getTopPanel() {
		if(topPanel == null){
			topPanel = new JPanel();
			topPanel.add(getLoginLabel());
		}
		return topPanel;
	}

	public JTextField getDirectoryField() {
		if(directorySelected == null){
			directorySelected = new JTextField(DIRECTORY_SELECTED_LENGHT);
			directorySelected.setEnabled(false);
		}
		return directorySelected;
	}

	public JProgressBar getProgressBar() {
		if(progressBar == null){
			progressBar = new JProgressBar();
			progressBar.setVisible(false);
		}
		return progressBar;
	}

	public JLabel getLabel() {
		if(label == null){
			label = new JLabel(STATUS_LABEL);
		}
		return label;
	}

	public JLabel getLoginLabel() {
		if(loginLabel == null){
			loginLabel = new JLabel(LOG_OUT);
		}
		return loginLabel;
	}

	public JButton getCompleteMetadataButton() {
		if(completeMetadataButton == null){
			completeMetadataButton = new JButton(COMPLETE_BUTTON);
			completeMetadataButton.setEnabled(false);
			
			completeMetadataButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					configurator.getViewEngine().send(Actions.COMPLETE);
				}
			});
		}
		return completeMetadataButton;
	}

	public JButton getSendButton() {
		if(sendButton == null){
			sendButton = new JButton(SEND_SCROBBLINGS);
			sendButton.setEnabled(false);
			
			sendButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					configurator.getViewEngine().send(Actions.SEND);
				}
			});
		}
		return sendButton;
	}

	public JButton getOpenButton() {
		if(openButton == null){
			openButton = new JButton(LOAD_FILES);
			openButton.setMnemonic(KeyEvent.VK_O);
			
			openButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					configurator.getViewEngine().send(Actions.METADATA);
				}
			});
		}
		return openButton;
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null){
			scrollPane = new JScrollPane(getDescriptionTable());
		}
		return scrollPane;
	}

	public JTable getDescriptionTable() {
		if(descriptionTable == null){
			descriptionTable = new DescriptionTable();
			descriptionTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					descriptionTable.setToolTipText("In order to enter a custom metadata you have to click "
							+ "on complete button first");
				}
			});
		}
		return descriptionTable;
	}

	public Frame getFrame() {
		if(frame == null){
			frame = new JFrame(APPLICATION_NAME);
			frame.add(getPanel());
			frame.setJMenuBar(getMenubar());
			frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		return frame;
	}

	public class ClickAction extends AbstractAction {
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
