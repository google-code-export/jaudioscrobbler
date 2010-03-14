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
            "# Track",
            "Length",
            "Status"};
	
	static Object[][] data = {
		    {"", "", "", "", "", ""},
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
				this.getColumnModel().getColumn(i).setPreferredWidth(180);
			}
		}
	}
}
