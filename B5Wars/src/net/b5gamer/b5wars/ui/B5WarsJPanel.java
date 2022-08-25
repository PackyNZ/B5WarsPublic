package net.b5gamer.b5wars.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import net.b5gamer.swing.JPaintingPanel;

/**
 * This class implements common configuration for a {@link net.b5gamer.swing.JPaintingPanel} 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class B5WarsJPanel extends JPaintingPanel {

	private static final long serialVersionUID = -5344525851675892386L;

	public static final Color BACKGROUND_COLOR = new Color(0, 0, 32);
	public static final Color FOREGROUND_COLOR = new Color(204, 204, 204);
	public static final Color BORDER_COLOR     = new Color(64, 64, 128);
	public static final Font  FONT             = new Font("Verdana", Font.PLAIN, 11);
	
	/**
	 * Creates a new {@link net.b5gamer.b5wars.ui.B5WarsJPanel} with a double buffer and a flow layout
	 */
	public B5WarsJPanel() {
		this(new FlowLayout(), true);
	}
	
	/**
	 * Creates a new {@link net.b5gamer.b5wars.ui.B5WarsJPanel} with {@link java.awt.FlowLayout} and 
	 * the specified buffering strategy
	 * 
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory space
	 *                         to achieve fast, flicker-free updates
	 */
	public B5WarsJPanel(boolean isDoubleBuffered) {
		this(new FlowLayout(), isDoubleBuffered);
	}
	
	/**
	 * Create a new buffered {@link net.b5gamer.b5wars.ui.B5WarsJPanel} with the specified layout 
	 * manager
	 * 
	 * @param layout the LayoutManager to use
	 */
	public B5WarsJPanel(LayoutManager layout) {
		this(layout, true);
	}
	
	/**
	 * Creates a new {@link net.b5gamer.b5wars.ui.B5WarsJPanel} with the specified layout manager 
	 * and buffering strategy
	 * 
	 * @param layout           the LayoutManager to use
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory 
	 *                         space to achieve fast, flicker-free updates
	 */
	public B5WarsJPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setFont(FONT);
	}
	    
}
