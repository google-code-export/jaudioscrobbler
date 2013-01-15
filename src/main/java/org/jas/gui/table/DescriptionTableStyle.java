package org.jas.gui.table;

import java.awt.Color;

import javax.swing.JTable;

import org.jas.gui.util.SynthColors;

public class DescriptionTableStyle extends JTable {

	private static final long serialVersionUID = -1153564046400612566L;

	public Color getEvenRowColor() {
		return SynthColors.CLEAR_GRAY245_245_245;
	}

	public Color getOddRowColor() {
		return SynthColors.WHITE255_255_255;
	}

	public Color getSelectedRowColor() {
		return SynthColors.BLUE175_205_225;
	}

	public Color getSelectedSeparatorColor() {
		return SynthColors.WHITE255_255_255;
	}

	public Color getGridColor() {
		return SynthColors.GRAY150_150_150;
	}

}
