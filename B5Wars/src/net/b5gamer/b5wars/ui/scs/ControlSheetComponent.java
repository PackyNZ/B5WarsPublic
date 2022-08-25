/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class ControlSheetComponent extends JComponent {

	private static final long serialVersionUID = 5872821813654758471L;

	public abstract void setup(Graphics2D graphics);

}
