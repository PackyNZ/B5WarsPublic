package net.b5gamer.b5wars.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * This class implements common configuration for a {@link javax.swing.JFrame} 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class B5WarsJFrame extends JFrame {

	private static final long serialVersionUID = 1945100928896572704L;

	public static final String TITLE   = "B5Wars";
	public static final String VERSION = "0.2";
	
	/**
	 * create a centred {@link javax.swing.JFrame} with the specified size and default title
	 * 
	 * @param width  width to make the frame
	 * @param height height to make the frame
	 */
	public B5WarsJFrame(final int width, final int height) {
		this(null, width, height);
	}
	
	/**
	 * create a centred {@link javax.swing.JFrame} with the specified size and title
	 * 
	 * @param title  title for the frame
	 * @param width  width to make the frame
	 * @param height height to make the frame
	 */
	public B5WarsJFrame(final String title, final int width, final int height) {
        super(((title != null) && (title.length() > 0)) ? title : getApplicationTitle());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setSize(getPreferredSize());
        setLocationRelativeTo(null);
	}
	
	/**
	 * the application title
	 * 
	 * @return the application title
	 */
	public static String getApplicationTitle() {
		return TITLE + " v" + VERSION;
	}
	
}
