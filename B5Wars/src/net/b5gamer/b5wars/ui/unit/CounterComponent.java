/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.unit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JComponent;

import net.b5gamer.b5wars.unit.Unit;

public class CounterComponent extends JComponent implements Icon {

	private static final long serialVersionUID = 8160627224568875173L;

	private static final Font NUMBER_FONT = new Font("Arial", Font.PLAIN, 10);
	
	private Unit        unit          = null;
	private int         counterNumber = 0;
	private GlyphVector numberGlyph   = null;
	
	public CounterComponent(final Unit unit, final int counterNumber) {
		setUnit(unit);
		setCounterNumber(counterNumber);
	}
	
	public Unit getUnit() {
		return unit;
	}

	private void setUnit(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }
        if (unit.getCounter() == null) {
            throw new IllegalArgumentException("unit counter cannot be null");
        }
        
        this.unit = unit;
        
		Dimension size = new Dimension(getUnit().getCounter().getWidth(this), getUnit().getCounter().getHeight(this));
		setPreferredSize(size);
		setSize(size);
		
		setToolTipText(toString());        
	}

	public int getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(int counterNumber) {
		this.counterNumber = counterNumber;
		
		setNumberGlyph(null);
	}

	protected GlyphVector getNumberGlyph() {
		return numberGlyph;
	}

	private void setNumberGlyph(GlyphVector numberGlyph) {
		this.numberGlyph = numberGlyph;
	}

	public synchronized void setup(Graphics2D graphics) {
		if (getNumberGlyph() == null) {
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();
			
			setNumberGlyph(NUMBER_FONT.createGlyphVector(fontRenderContext, String.valueOf(getCounterNumber())));
		}
	}
	
	public int getIconHeight() {
		return getHeight();
	}

	public int getIconWidth() {
		return getWidth();
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D graphics = (Graphics2D) g;
		AffineTransform transform = graphics.getTransform();
		graphics.transform(AffineTransform.getTranslateInstance(x, y));
		paint(graphics);
		graphics.setTransform(transform);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (getNumberGlyph() == null) {
			setup(graphics);
		}
		
		// draw counter
		graphics.drawImage(getUnit().getCounter(), 0, 0, this);

		// draw counter number
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, 15, 15);
		graphics.setColor(Color.white);
		graphics.drawGlyphVector(getNumberGlyph(), 
				(float) ((15.0 - getNumberGlyph().getLogicalBounds().getWidth()) / 2.0), 
				(float) ((15.0 - getNumberGlyph().getLogicalBounds().getHeight()) / 2.0 + getNumberGlyph().getLogicalBounds().getHeight() - 2.0));
	}
	
	@Override
	public String toString() {
		return getUnit().toString();
	}
	
}
