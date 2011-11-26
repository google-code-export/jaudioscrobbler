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
            "# Trk",
            "# Trks",
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
		this.setAutoCreateRowSorter(true);
		setPreferredWidth();
	}

	private void setPreferredWidth() {
		for(int i=0; i< this.getColumnCount(); i++){
			switch(i){
			case 0:
				this.getColumnModel().getColumn(i).setPreferredWidth(100);
				break;
			case 1:
				this.getColumnModel().getColumn(i).setMinWidth(180);
				this.getColumnModel().getColumn(i).setMaxWidth(Integer.MAX_VALUE);
				break;
			case 2:	
			case 3:	
				this.getColumnModel().getColumn(i).setPreferredWidth(100);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:	
				this.getColumnModel().getColumn(i).setMinWidth(50);
				this.getColumnModel().getColumn(i).setMaxWidth(50);
				break;
			case 9:	
				this.getColumnModel().getColumn(i).setMinWidth(60);
				this.getColumnModel().getColumn(i).setMaxWidth(60);
				break;	
			}
		}
	}
}
