package org.lastfm.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class DescriptionTable extends JTable{
	private static final long serialVersionUID = 1L;

	static String[] columnNames = {"Artist",
            "Track",
            "Album",
            "Genre",
            "Year",
            "# Track",
            "# Tracks",
            "# CD",
            "# CDs",
            "Status"};
	
	static Object[][] data = {
		    {"", "", "", "", "", "", "", "", "", ""},
		};


	public DescriptionTable() {
		DefaultTableModel model = new DefaultTableModel(data,columnNames);
		this.setModel(model);
		this.setEnabled(false);
		setPreferredWidth();
	}

	private void setPreferredWidth() {
		for(int i=0; i< this.getColumnCount(); i++){
			switch(i){
			case 0:
			case 1:
			case 2:	
			case 3:	
				this.getColumnModel().getColumn(i).setPreferredWidth(380);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:	
				this.getColumnModel().getColumn(i).setPreferredWidth(180);
				break;
			case 9:	
				this.getColumnModel().getColumn(i).setPreferredWidth(280);
				break;	
			}
		}
	}
}
