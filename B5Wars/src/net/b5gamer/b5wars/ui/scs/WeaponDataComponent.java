/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class WeaponDataComponent extends ControlSheetComponent {

	private static final long serialVersionUID = 9210211157906243373L;

	public  static final int    DEFAULT_X      = 502;
	public  static final int    DEFAULT_Y      = 68;	
	public  static final int    DEFAULT_WIDTH  = 90;	
	public  static final int    DEFAULT_HEIGHT = 12;	
	private static final Stroke STROKE         = new BasicStroke(0.75f);
	private static final Font   HEADING_FONT   = (new Font("Arial", Font.PLAIN, 10)).deriveFont(AffineTransform.getScaleInstance(0.8, 1.0));
	
	private Shape       outline = null;
	private GlyphVector heading = null;
	
	public WeaponDataComponent() {
		setLocation(DEFAULT_X, DEFAULT_Y);
		
		Dimension size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(size);
		setSize(size);
	}
	
	private Shape getOutline() {
		return outline;
	}

	private void setOutline(Shape outline) {
		this.outline = outline;
	}

	private GlyphVector getHeading() {
		return heading;
	}

	private void setHeading(GlyphVector heading) {
		this.heading = heading;
	}

	@Override
	public synchronized void setup(Graphics2D graphics) {
		if (getHeading() == null) {
			GeneralPath outline = new GeneralPath();
			outline.moveTo(0.4f, 1f);
			outline.lineTo(getWidth() - 0.2f, 1f);
			outline.lineTo(getWidth() - 0.2f, getHeight());
			outline.lineTo(0.4f, getHeight());
			outline.closePath();
			setOutline(outline);
			
			setHeading(HEADING_FONT.createGlyphVector(graphics.getFontRenderContext(), "WEAPON DATA"));
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if (getHeading() == null) {
			setup(graphics);
		}
		
		graphics.setStroke(STROKE);
		graphics.setColor(Color.white);
		graphics.fill(getOutline());		
		graphics.setColor(Color.black);
		graphics.draw(getOutline());

		graphics.drawGlyphVector(getHeading(), 3.8f, 10.1f);
	}	
	
}
