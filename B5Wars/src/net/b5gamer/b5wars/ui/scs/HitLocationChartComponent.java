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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;

public class HitLocationChartComponent extends ControlSheetComponent {

	private static final long serialVersionUID = -5997673111791590228L;

	public  static final int    DEFAULT_X       = 18;
	public  static final int    DEFAULT_Y       = 147;	
	public  static final int    DEFAULT_WIDTH   = 86;	
	private static final int    CHART_HEIGHT    = 9;
	private static final int    LOCATION_HEIGHT = 7;
	private static final Stroke STROKE          = new BasicStroke(0.5f);
	private static final Font   CHART_FONT      = (new Font("Arial", Font.PLAIN, 10)).deriveFont(AffineTransform.getScaleInstance(0.75, 1.0));
	private static final Font   LOCATION_FONT   = (new Font("Arial", Font.PLAIN, 7)).deriveFont(AffineTransform.getScaleInstance(0.8, 1.0));
	
	protected StructuralUnit unit;
	private   Map<GlyphVector, List<HitLocationLine>> labels = null;
	
	public HitLocationChartComponent(final StructuralUnit unit) {
		setUnit(unit);
		setLocation(DEFAULT_X, DEFAULT_Y);
	}

	public StructuralUnit getUnit() {
		return unit;
	}

	public void setUnit(StructuralUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }

        this.unit = unit;
        setLabels(null);
	}
	
	private Map<GlyphVector, List<HitLocationLine>> getLabels() {
		return labels;
	}

	private void setLabels(Map<GlyphVector, List<HitLocationLine>> labels) {
		this.labels = labels;
	}

	public synchronized void setup(Graphics2D graphics) {
		if (getLabels() == null) {
			Map<GlyphVector, List<HitLocationLine>> labels = new LinkedHashMap<GlyphVector, List<HitLocationLine>>();
			int numCharts = 0;
			int numLines  = 0;

			FontMetrics fontMetrics = graphics.getFontMetrics(LOCATION_FONT);
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();
			
			for (Iterator<HitLocationChart> iterator = getUnit().getHitLocationCharts().iterator(); iterator.hasNext();) {
				HitLocationChart hitLocationChart = iterator.next();
				List<HitLocationLine> lines = new ArrayList<HitLocationLine>();
				
				for (Iterator<HitLocation> locationIter = hitLocationChart.getHitLocationIterator(); locationIter.hasNext();) {
					HitLocation hitLocation = locationIter.next();

					String line = abbreviateHitLocation(hitLocation.getLocationDescription());
					
					// TODO complete wrap text
			        Rectangle2D lineBounds = fontMetrics.getStringBounds(line, graphics);
					
			        if (lineBounds.getWidth() > DEFAULT_WIDTH - 23) {
			        	int space = line.lastIndexOf(" ");
			        	space = (space == -1) ? line.lastIndexOf("/") : space;
			        	
			        	if (space != -1) {
							lines.add(new HitLocationLine(
									LOCATION_FONT.createGlyphVector(fontRenderContext, hitLocation.getRange() + ":"),
									LOCATION_FONT.createGlyphVector(fontRenderContext, line.substring(0, space))));			        	
							lines.add(new HitLocationLine(
									null,
									LOCATION_FONT.createGlyphVector(fontRenderContext, line.substring(space + 1))));			        	
			        	} else {
							lines.add(new HitLocationLine(
									LOCATION_FONT.createGlyphVector(fontRenderContext, hitLocation.getRange() + ":"),
									LOCATION_FONT.createGlyphVector(fontRenderContext, line)));			        	
			        	}
			        } else {
						lines.add(new HitLocationLine(
								LOCATION_FONT.createGlyphVector(fontRenderContext, hitLocation.getRange() + ":"),
								LOCATION_FONT.createGlyphVector(fontRenderContext, line)));			        	
			        }
				}
				
				labels.put(CHART_FONT.createGlyphVector(fontRenderContext, hitLocationChart.getName()), lines);
				numCharts++;
				numLines += lines.size();
			}			
			
			setLabels(labels);

			Dimension size = new Dimension(DEFAULT_WIDTH, (CHART_HEIGHT * numCharts) + (LOCATION_HEIGHT * numLines) + (numCharts * 2));
			setPreferredSize(size);
			setSize(size);
		}
	}
	
	private String abbreviateHitLocation(String text) {
		text = text.replaceAll("Light",    "Lt");
		text = text.replaceAll("Medium",   "Med");
		text = text.replaceAll("Heavy",    "Hvy");
		text = text.replaceAll("Standard", "Std");
		text = text.replaceAll("Improved", "Impr");
		
		return text;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if (getLabels() == null) {
			setup(graphics);
		}
		
		graphics.setStroke(STROKE);
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.black);
		
		int y = 0;
		for (Iterator<GlyphVector> iterator = getLabels().keySet().iterator(); iterator.hasNext();) {
			GlyphVector chart = iterator.next();
			List<HitLocationLine> lines = getLabels().get(chart);
			
			graphics.drawRect(0, y, getWidth(), CHART_HEIGHT + (LOCATION_HEIGHT * lines.size()) + 2);
			graphics.drawGlyphVector(chart, 2, y + CHART_HEIGHT);
			
			int index = 1;
			for (Iterator<HitLocationLine> lineIter = lines.iterator(); lineIter.hasNext();) {
				HitLocationLine line = lineIter.next();

				if (line.getRange() != null) {
					graphics.drawGlyphVector(line.getRange(), 2, y + CHART_HEIGHT + (index * LOCATION_HEIGHT));
				}
				graphics.drawGlyphVector(line.getLocation(), 22, y + CHART_HEIGHT + (index * LOCATION_HEIGHT));

				index++;
			}			
			
			y += CHART_HEIGHT + (LOCATION_HEIGHT * lines.size()) + 2;
		}		
	}
	
}
