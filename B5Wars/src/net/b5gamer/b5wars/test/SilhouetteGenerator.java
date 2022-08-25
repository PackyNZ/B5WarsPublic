package net.b5gamer.b5wars.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.ui.render.ArcRenderer;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.map.Arc;

public class SilhouetteGenerator {

	public static void main(String[] args) throws Exception {
		String filename = "ArcSilhouette.xml";
		List<Unit> units = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename);
		StructuralUnit unit = (StructuralUnit) units.get(0);
		
		int width  = 600;
		int height = 700;		
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);       
        Graphics2D graphics = image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);
        
		// fill sections
        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
    		Section section = iterator.next();
    		
            if ((section.getArc() != null) && (!Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
            	graphics.drawImage(ArcRenderer.render(section.getArc(), new Color(Integer.parseInt("C7C8CA", 16)), 
            			width, height), 0, 0, null);
    		}
    	}

        // draw section outlines
		boolean hasSections = false;
        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
    		Section section = iterator.next();
    		
            if ((section.getArc() != null) && (!Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
            	graphics.drawImage(ArcRenderer.renderOutline(section.getArc(), new Color(Integer.parseInt("C7C8CA", 16)), 
        				Color.black, Color.black, width, height, section.getName()), 0, 0, null);

        		hasSections = true;
            }
    	}
    
        // draw primary section
        Section primary = unit.getSection(Section.PRIMARY);
        if (primary != null) {
        	double factor = 1.0;
        	int x = 0;
        	int y = 0;
        	if (hasSections || unit.getGeneralHitLocationChart() != null) {
        		factor = 2;
        		x = (int) (width / factor / 2.0);
          		y = (int) (height / factor / 2.0);
          	}

        	graphics.drawImage(ArcRenderer.render(Arc.FULL_360, new Color(Integer.parseInt("C7C8CA", 16)), 
        			(int) (width / factor), (int) (height / factor)), x, y, null);

        	graphics.drawImage(ArcRenderer.renderOutline(Arc.FULL_360, new Color(Integer.parseInt("C7C8CA", 16)), 
					Color.black, Color.black, (int) (width / factor), (int) (height / factor), Section.PRIMARY), x, y, null);
        }
        graphics.dispose();
        
        ImageIO.write(image, "png", new File(unit.getType() + "_Silhouette.png"));		
	}

}
