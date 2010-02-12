package org.lastfm;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.lastfm.gui.DescriptionTable;

public class ApplicationState {
	static DescriptionTable descriptionTable = null;
	static int row = 0;
	
	public static String userName;
	public static String password;
	public static int ERROR = -1;
	public static int OK = 0;
	public static int FAILURE = 2;
	
	public static final String LOGIN_FAIL = "Login fail";
	public static final String LOGGED_AS = "Logged as : ";
	public static final String DONE = "Done";
	public static final Object HAND_SHAKE_FAIL = "Handshake failed";

	public static void setDescriptionTable(JTable table) {
		descriptionTable = (DescriptionTable) table;
	}
	
	public static void update(Metadata metadata) {
		if(descriptionTable!= null){
			if(descriptionTable.getRowCount() <= row){
				DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
				model.addRow(new Object[]{ "", "", "", "", "", "" });
			}
			descriptionTable.setValueAt(metadata.getArtist(), row, 0);
			descriptionTable.setValueAt(metadata.getTitle(), row, 1);
			descriptionTable.setValueAt(metadata.getAlbum(), row, 2);
			descriptionTable.setValueAt(metadata.getTrackNumber(), row, 3);
			descriptionTable.setValueAt(metadata.getLength(), row, 4);
			descriptionTable.setValueAt("Ready", row, 5);
			row++;	
		}
	}
}