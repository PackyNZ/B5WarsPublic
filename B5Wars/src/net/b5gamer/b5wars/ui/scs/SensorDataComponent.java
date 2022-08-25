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
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class SensorDataComponent extends ControlSheetComponent {
	
	private static final long serialVersionUID = -8087156649646526844L;

	public  static final int    DEFAULT_X      = 106;
	public  static final int    DEFAULT_Y      = 147;
	public  static final int    DEFAULT_WIDTH  = 94;
	public  static final int    DEFAULT_HEIGHT = 86;
	private static final Stroke STROKE         = new BasicStroke(0.5f);
	private static final Font   HEADING_FONT   = (new Font("Arial", Font.PLAIN, 12)).deriveFont(AffineTransform.getScaleInstance(0.8, 1.0));
	private static final Font   LABEL_FONT     = (new Font("Arial", Font.PLAIN, 9)).deriveFont(AffineTransform.getScaleInstance(0.9, 1.0));
	
	private GlyphVector       heading     = null;
	private GlyphVector       defensiveEW = null;
	private List<GlyphVector> targets     = null;

	public SensorDataComponent() {
		setLocation(DEFAULT_X, DEFAULT_Y);
		
		Dimension size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(size);
		setSize(size);		
	}
	
	private GlyphVector getHeading() {
		return heading;
	}

	private void setHeading(GlyphVector heading) {
		this.heading = heading;
	}

	private GlyphVector getDefensiveEW() {
		return defensiveEW;
	}

	private void setDefensiveEW(GlyphVector defensiveEW) {
		this.defensiveEW = defensiveEW;
	}

	private List<GlyphVector> getTargets() {
		return targets;
	}

	private void setTargets(List<GlyphVector> targets) {
		this.targets = targets;
	}

	public synchronized void setup(Graphics2D graphics) {
		if (getHeading() == null) {
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();

			setHeading(HEADING_FONT.createGlyphVector(fontRenderContext, "SENSOR DATA"));
			setDefensiveEW(LABEL_FONT.createGlyphVector(fontRenderContext, "Defensive EW"));
			
			List<GlyphVector> targets = new ArrayList<GlyphVector>(0);
			for (int i = 1; i <= 6; i++) {
				targets.add(LABEL_FONT.createGlyphVector(fontRenderContext, "Target #" + i));
			}
			setTargets(targets);
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
		graphics.fillRect(0, 0, getWidth(), getHeight());		
		graphics.setColor(Color.black);
		graphics.drawRect(0, 0, getWidth(), getHeight());

		graphics.drawGlyphVector(getHeading(), 2, 11);

		graphics.drawGlyphVector(getDefensiveEW(), 2, 21);
		graphics.drawRect(58, 13, 33, 10);

		for (int i = 1; i <= getTargets().size(); i++) {
			graphics.drawGlyphVector(getTargets().get(i - 1), 2, 21 + (i * 10));
			graphics.drawRect(41, 13 + (i * 10), 25, 10);
			graphics.drawRect(66, 13 + (i * 10), 25, 10);
		}
	}	

}
