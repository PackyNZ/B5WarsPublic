/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.b5gamer.awt.geom.GeneralShape;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.Icon;

public class IconRecognitionComponent extends ControlSheetComponent {

	private static final long serialVersionUID = -7832054631657099845L;

	public  static final int    DEFAULT_X      = 18;
	public  static final int    DEFAULT_WIDTH  = 86;	
	private static final int    HEADING_HEIGHT = 10;
	private static final int    LABEL_HEIGHT   = 7;	
	private static final Stroke STROKE         = new BasicStroke(0.75f);
	private static final Font   HEADING_FONT   = (new Font("Arial", Font.PLAIN, 10)).deriveFont(AffineTransform.getScaleInstance(0.83, 1.0));
	private static final Font   LABEL_FONT     = (new Font("Arial", Font.PLAIN, 7)).deriveFont(AffineTransform.getScaleInstance(0.9, 1.0));
	private static final double MAX_ICON_SIZE  = 24;
	private static final double ICON_SCALE     = 0.3;
	private static final Stroke ICON_STROKE    = new BasicStroke((float) (0.225 / ICON_SCALE));
	
	protected StructuralUnit                 unit;
	private   GlyphVector                    heading = null;
	private   Map<System, List<GlyphVector>> entries = null;
	
	public IconRecognitionComponent(final StructuralUnit unit) {
		setUnit(unit);
	}

	public StructuralUnit getUnit() {
		return unit;
	}

	public void setUnit(StructuralUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }

        this.unit = unit;
        setEntries(null);
	}

	private GlyphVector getHeading() {
		return heading;
	}

	private void setHeading(GlyphVector heading) {
		this.heading = heading;
	}

	private Map<System, List<GlyphVector>> getEntries() {
		return entries;
	}

	private void setEntries(Map<System, List<GlyphVector>> entries) {
		this.entries = entries;
	}

	public synchronized void setup(Graphics2D graphics) {
		if (getEntries() == null) {
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();
			setHeading(HEADING_FONT.createGlyphVector(fontRenderContext, "ICON RECOGNITION"));
			
			// create ordered list of unique system types
			List<System> systems = new ArrayList<System>();
			for (Iterator<System> iterator = getUnit().getSystemsOfClass(System.class, true).iterator(); iterator.hasNext();) {
				System system = iterator.next();

				if ((system.getRecognitionOrder() > 0) && (system.getRecognitionIcon() != null) && 
						(system.getRecognitionIcon().getOutlines() != null)) {
					boolean found = false;
					
					for (Iterator<System> iterator2 = systems.iterator(); iterator2.hasNext();) {
						if (iterator2.next().getType().equals(system.getType())) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						systems.add(system);
					}
				}
			}
			Collections.sort(systems, new IconRecognitionComparator());

			// create entries			
			Map<System, List<GlyphVector>> entries = new LinkedHashMap<System, List<GlyphVector>>();
			FontMetrics fontMetrics = graphics.getFontMetrics(LABEL_FONT);
			int height = HEADING_HEIGHT + 4;

			for (Iterator<System> iterator = systems.iterator(); iterator.hasNext();) {
				System system = iterator.next();
				
				List<GlyphVector> lines = new ArrayList<GlyphVector>();

				String line = abbreviateType(system.getType());
		        Rectangle2D lineBounds = fontMetrics.getStringBounds(line, graphics);
		        
		        // TODO multiple lines
		        if (lineBounds.getWidth() > DEFAULT_WIDTH - (MAX_ICON_SIZE + 1)) {
		        	int space = line.lastIndexOf(" ");
		        	space = (space == -1) ? line.lastIndexOf("/") : space;
		        	
		        	if (space != -1) {
						lines.add(LABEL_FONT.createGlyphVector(fontRenderContext, line.substring(0, space)));			        				        		
						lines.add(LABEL_FONT.createGlyphVector(fontRenderContext, line.substring(space + 1)));			        				        		
		        	} else {
						lines.add(LABEL_FONT.createGlyphVector(fontRenderContext, line));			        				        		
		        	}
		        } else {
					lines.add(LABEL_FONT.createGlyphVector(fontRenderContext, line));			        	
		        }
		        
		        entries.put(system, lines);
		        
				double scale = getIconScale(system.getRecognitionIcon());
				int rowHeight = (system.getRecognitionIcon().getBounds().height * scale > lines.size() * LABEL_HEIGHT) ? 
						(int) (system.getRecognitionIcon().getBounds().height * scale) : lines.size() * LABEL_HEIGHT;
		        height += rowHeight + 3;
			}			
		
			setEntries(entries);

			Dimension size = new Dimension(DEFAULT_WIDTH, height);
			setPreferredSize(size);
			setSize(size);
			setLocation(DEFAULT_X, 792 - height - 17);
		}
	}
	
	private String abbreviateType(String text) {
		text = text.replaceAll("Light",    "Lt");
		text = text.replaceAll("Medium",   "Med");
		text = text.replaceAll("Heavy",    "Hvy");
		text = text.replaceAll("Standard", "Std");
		text = text.replaceAll("Improved", "Impr");
		text = text.replaceAll("External", "Ext");
		
		return text;
	}
	
	private double getIconScale(Icon icon) {
		double scale = ICON_SCALE;
		scale = (icon.getBounds().getWidth() * scale > MAX_ICON_SIZE)  ? MAX_ICON_SIZE / icon.getBounds().getWidth()  : scale;
		scale = (icon.getBounds().getHeight() * scale > MAX_ICON_SIZE) ? MAX_ICON_SIZE / icon.getBounds().getHeight() : scale;
		
		return scale;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		AffineTransform transform = graphics.getTransform();

		if (getEntries() == null) {
			setup(graphics);
		}
		
		graphics.setStroke(STROKE);
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.black);
		graphics.drawRect(0, 0, getWidth(), getHeight());

		graphics.drawGlyphVector(getHeading(), 2, HEADING_HEIGHT);
		
		graphics.setStroke(ICON_STROKE);

		int y = HEADING_HEIGHT + 4;		
		for (Iterator<System> iterator = getEntries().keySet().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			List<GlyphVector> lines = getEntries().get(system);
			
			double scale = getIconScale(system.getRecognitionIcon());
			int rowHeight = (system.getRecognitionIcon().getBounds().height * scale > lines.size() * LABEL_HEIGHT) ? 
					(int) (system.getRecognitionIcon().getBounds().height * scale) : lines.size() * LABEL_HEIGHT;
				
			// paint system outline
			graphics.transform(AffineTransform.getTranslateInstance(
					(MAX_ICON_SIZE - (system.getRecognitionIcon().getBounds().width * scale)) / 2, 
					y + (rowHeight - (system.getRecognitionIcon().getBounds().height * scale)) / 2)); 
			graphics.transform(AffineTransform.getScaleInstance(scale, scale));

			if (system.getRecognitionIcon().getOutlines() != null) {
				for (Iterator<GeneralShape> iterator2 = system.getRecognitionIcon().getOutlines().iterator(); iterator2.hasNext();) {
					GeneralShape outline = iterator2.next();
					
					// fill outline
		//			if (outline.isEnclosed()) {
						graphics.setColor(system.getOutlineFillColor());
						graphics.fill(outline);
		//			}
					
					// draw outline
					graphics.setColor(system.getOutlineColor());
					graphics.draw(outline);
				}
			}
			
			graphics.setTransform(transform);

			// paint label
			graphics.setColor(Color.black);

			int index = 1;
			for (Iterator<GlyphVector> iterator3 = lines.iterator(); iterator3.hasNext();) {
				GlyphVector line = iterator3.next();
				
				graphics.drawGlyphVector(line, (int) MAX_ICON_SIZE, y + (index * LABEL_HEIGHT) + (rowHeight - (lines.size() * LABEL_HEIGHT)) / 2);
				
				index++;
			}		
			
			y += rowHeight + 3;
		}		
	}
	
}
