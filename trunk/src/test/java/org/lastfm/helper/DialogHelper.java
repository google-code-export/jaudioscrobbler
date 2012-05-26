package org.lastfm.helper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogHelper {

	public void showMessageDialog(JFrame frame, String title) {
		JOptionPane.showMessageDialog(frame, title + " has a corrupted coverArt");
	}
	
}
