/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.swing;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.util.Map;

import javax.swing.JPanel;

/**
 * An extension of {@link javax.swing.JPanel} that allows for greater control over graphics 
 * performance
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class JPaintingPanel extends JPanel {

	private static final long serialVersionUID = 8924579261578893778L;

	private       boolean        optimizedDrawingEnabled = true; // whether optimized drawing is enabled
	private final RenderingHints renderingHints          = new RenderingHints(null); // all rendering hints
	
	/**
	 * Creates a new {@link net.b5gamer.swing.JPaintingPanel} with a double buffer and a flow layout
	 */
	public JPaintingPanel() {
		this(new FlowLayout(), true);
	}
	
	/**
	 * Creates a new {@link net.b5gamer.swing.JPaintingPanel} with {@link java.awt.FlowLayout} and 
	 * the specified buffering strategy
	 * 
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory 
	 *                         space to achieve fast, flicker-free updates
	 */
	public JPaintingPanel(boolean isDoubleBuffered) {
		this(new FlowLayout(), isDoubleBuffered);
	}
	
	/**
	 * Create a new buffered {@link net.b5gamer.swing.JPaintingPanel} with the specified layout manager
	 * 
	 * @param layout the LayoutManager to use
	 */
	public JPaintingPanel(LayoutManager layout) {
		this(layout, true);
	}
	
	/**
	 * Creates a new {@link net.b5gamer.swing.JPaintingPanel} with the specified layout manager and 
	 * buffering strategy
	 * 
	 * @param layout           the LayoutManager to use
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory 
	 *                         space to achieve fast, flicker-free updates
	 */
	public JPaintingPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
		setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}	
	
	/**
	 * Sets whether this component tiles its children -- that is, if it can guarantee that the 
	 * children will not overlap.
	 * 
	 * @param optimizedDrawingEnabled true if this component tiles its children -- that is, if it 
	 *                                can guarantee that the children will not overlap
	 */
	public void setOptimizedDrawingEnabled(boolean optimizedDrawingEnabled) {
		this.optimizedDrawingEnabled = optimizedDrawingEnabled;
	}

	@Override
    public boolean isOptimizedDrawingEnabled() {
    	return optimizedDrawingEnabled;
    }

	/**
	 * Sets the values of an arbitrary number of preferences for the rendering algorithms.
	 * 
	 * @param hints the rendering hints to be set
	 */
	public void addRenderingHints(Map<RenderingHints.Key,?> hints) {
		synchronized (renderingHints) {
			getRenderingHints().putAll(hints);
		}
	}
	
	/**
	 * Returns the value of a single preference for the rendering algorithms.
	 * 
	 * @param  hintKey the key corresponding to the hint to get. 
	 * @return         an object representing the value for the specified hint key. Some of the 
	 *                 keys and their associated values are defined in the 
	 *                 {@link java.awt.RenderingHints} class.
	 */
	public Object getRenderingHint(RenderingHints.Key hintKey) {
		return getRenderingHints().get(hintKey);
	}
	
	/**
	 * Gets the preferences for the rendering algorithms.
	 * 
	 * @return a reference to an instance of {@link java.awt.RenderingHints} that contains the 
	 *         current preferences.
	 */
	public RenderingHints getRenderingHints() {
		return renderingHints;
	}
	
	/**
	 * Sets the value of a single preference for the rendering algorithms.
	 * 
	 * @param hintKey   the key of the hint to be set.
	 * @param hintValue the value indicating preferences for the specified hint category.
	 */
	public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
		synchronized (renderingHints) {
			getRenderingHints().put(hintKey, hintValue);
		}
	}
	
	/**
	 * Replaces the values of all preferences for the rendering algorithms with the specified hints.
	 * 
	 * @param hints the rendering hints to be set
	 */
	public void setRenderingHints(Map<?,?> hints) {
		synchronized (renderingHints) {
			getRenderingHints().clear();
			getRenderingHints().putAll(hints);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		if (!getRenderingHints().isEmpty()) {
			Graphics2D graphics = (Graphics2D) g;
			graphics.addRenderingHints(getRenderingHints());

			super.paint(graphics);
		} else {
			super.paint(g);
		}
	}
	
}
