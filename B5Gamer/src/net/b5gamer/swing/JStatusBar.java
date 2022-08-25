/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.swing;

import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.b5gamer.util.StatusEvent;
import net.b5gamer.util.StatusListener;

public class JStatusBar extends JPanel implements StatusListener {

	private static final long serialVersionUID = -3082074925915407953L;

	private final Map<String, JLabel> labels = new HashMap<String, JLabel>(); // a map of all labels and the status keys they are used to display
	
	/**
	 * Creates a new {@link net.b5gamer.swing.JStatusBar} with a double buffer and a flow layout
	 */
	public JStatusBar() {
		super();
	}
	
	/**
	 * Creates a new {@link net.b5gamer.swing.JStatusBar} with {@link java.awt.FlowLayout} and 
	 * the specified buffering strategy
	 * 
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory 
	 *                         space to achieve fast, flicker-free updates
	 */
	public JStatusBar(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}
	
	/**
	 * Create a new buffered {@link net.b5gamer.swing.JStatusBar} with the specified layout manager
	 * 
	 * @param layout the LayoutManager to use
	 */
	public JStatusBar(LayoutManager layout) {
		super(layout);
	}
	
	/**
	 * Creates a new {@link net.b5gamer.swing.JStatusBar} with the specified layout manager and 
	 * buffering strategy
	 * 
	 * @param layout           the LayoutManager to use
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory 
	 *                         space to achieve fast, flicker-free updates
	 */
	public JStatusBar(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
	
	/**
	 * a map of all labels and the status keys they are used to display
	 * 
	 * @return a map of all labels and the status keys they are used to display
	 */
	protected Map<String, JLabel> getLabels() {
		return labels;
	}

	public void statusValueChanged(StatusEvent e) {
		JLabel label = getLabels().get(e.getKey());
		
		if (label != null) {
			label.setText(e.getValue());
		}
	}

}
