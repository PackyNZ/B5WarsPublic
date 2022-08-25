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
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.Unit;

public class SpecialNotesComponent extends ControlSheetComponent {

	private static final long serialVersionUID = 8905985651184038954L;
	
	public  static final int    DEFAULT_X     = 106;
	public  static final int    DEFAULT_Y     = 147;
	public  static final int    DEFAULT_WIDTH = 94;
	private static final int    NOTE_HEIGHT   = 10;
	private static final Stroke STROKE        = new BasicStroke(1.0f);
	private static final Font   HEADING_FONT  = (new Font("Arial", Font.PLAIN, 12)).deriveFont(AffineTransform.getScaleInstance(0.8, 1.0));
	private static final Font   NOTE_FONT     = new Font("Arial", Font.PLAIN, 8);

	private Unit              unit;
	private GlyphVector       heading = null;
	private List<GlyphVector> notes   = null;
	
	public SpecialNotesComponent(final Unit unit) {
		setUnit(unit);
		setLocation(DEFAULT_X, DEFAULT_Y);
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }

        this.unit = unit;
        setNotes(null);        
	}
	
	private GlyphVector getHeading() {
		return heading;
	}

	private void setHeading(GlyphVector heading) {
		this.heading = heading;
	}

	private List<GlyphVector> getNotes() {
		return notes;
	}

	private void setNotes(List<GlyphVector> notes) {
		this.notes = notes;
		
		Dimension size = new Dimension(DEFAULT_WIDTH, 12 + (getNoteCount() * NOTE_HEIGHT) + 4);
		setPreferredSize(size);
		setSize(size);		        
	}

	public int getNoteCount() {
		if (getNotes() != null) {
			return getNotes().size();
		} else {
			int count = 0;
			
			if (getUnit().getPeriodOfService() != null) {
				if (getUnit().getPeriodOfService().getMaximumAvailable() > 0) {
					count++;
				}
				if (getUnit().getPeriodOfService().isDeploymentLimited()) {
					count++;
				}				
				if (getUnit().getPeriodOfService().isEconomicCost()) {
					count++;
				}
			}
			count += getUnit().getTraitCount();
			
			return count;
		}
	}

	public synchronized void setup(Graphics2D graphics) {
		if (getNotes() == null) {
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();
			setHeading(HEADING_FONT.createGlyphVector(fontRenderContext, "SPECIAL NOTES"));
			
			List<GlyphVector> notes = new ArrayList<GlyphVector>(0);
			
			if (getUnit().getPeriodOfService() != null) {
				if (getUnit().getPeriodOfService().getMaximumAvailable() > 0) {
					StringBuilder note = new StringBuilder();
					note.append("Special Availability (");
					note.append("Only ").append(getUnit().getPeriodOfService().getMaximumAvailable()).append(" Exist"); 
					note.append(getUnit().getPeriodOfService().getMaximumAvailable() > 1 ? ")" : "s)");
					
					notes.add(NOTE_FONT.createGlyphVector(fontRenderContext, note.toString()));
				}
				
				if (getUnit().getPeriodOfService().isDeploymentLimited()) {
					notes.add(NOTE_FONT.createGlyphVector(fontRenderContext, getUnit().getPeriodOfService().getDeploymentLimitation()));		
				}
				
				if (getUnit().getPeriodOfService().isEconomicCost()) {
					notes.add(NOTE_FONT.createGlyphVector(fontRenderContext, getUnit().getPeriodOfService().getEconomicCost()));
				}
			}
			
			for (Iterator<Trait> iterator = getUnit().getTraitIterator(); iterator.hasNext();) {
				notes.add(NOTE_FONT.createGlyphVector(fontRenderContext, iterator.next().toString()));
			}
			
			setNotes(notes);			
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (getNotes() == null) {
			setup(graphics);
		}
		
		graphics.setStroke(STROKE);
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());		
		graphics.setColor(Color.black);
		graphics.drawRect(0, 0, getWidth(), getHeight());
		
		graphics.drawGlyphVector(getHeading(), 2, 11f);
		
		// workaround to get finer control over font scaling
		graphics.transform(AffineTransform.getScaleInstance(0.9, 1.0));

		float y = 11.5f;
		for (Iterator<GlyphVector> iterator = getNotes().iterator(); iterator.hasNext();) {
			y += NOTE_HEIGHT;
			graphics.drawGlyphVector(iterator.next(), 2, y);
		}		
	}
		
}
