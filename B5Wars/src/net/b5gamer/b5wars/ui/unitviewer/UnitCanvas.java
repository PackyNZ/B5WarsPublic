/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.unitviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.ui.render.ArcRenderer;
import net.b5gamer.b5wars.ui.render.HexRenderer;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.system.DefensiveSystem;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.map.Arc;
import net.b5gamer.util.ImageUtil;

/**
 * This custom panel graphically displays a unit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class UnitCanvas extends JPanel {

	private static final long serialVersionUID = 723191574405371264L;
	
	public static final String DISPLAY_TYPE_SECTIONS = "Sections";
	public static final String DISPLAY_TYPE_WEAPONS  = "Weapons";
	public static final String DISPLAY_TYPE_DEFENSES = "Defenses";
	
	private Image   backgroundImage  = null; // background image
	private int     backgroundWidth  = 0;    // width of the background image
	private int     backgroundHeight = 0;    // height of the background image	
	private Image   hexClusterImage  = null; // hex image
	private int     shipX            = 0;    // x position to paint the ship image
	private int     shipY            = 0;    // y position to paint the ship image
	private boolean drawHex          = true; // whether to draw the hex image
	private boolean drawShipIcon     = true; // whether to draw the ship icon
	private Unit    unit             = null; // unit to display
	private String  displayType      = null; // type of item to display
	private String  displayItem      = null; // item to display, null = all
	private Arc     arc              = null; // arc to draw under the images
	private int     angle            = -1;   // angle to draw a line out from the center

	public UnitCanvas() {
		try {
			backgroundImage = ImageIO.read(this.getClass().getResourceAsStream("space.png"));
			if (backgroundImage != null) {
				backgroundWidth  = backgroundImage.getWidth(this);
				backgroundHeight = backgroundImage.getHeight(this);
				setSize(backgroundWidth, backgroundHeight);
			}
			
			hexClusterImage = ImageUtil.centerWithinNewImage(HexRenderer.renderHexCluster(60, Color.white),
					backgroundWidth + 2, backgroundHeight + 2);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(backgroundWidth, backgroundHeight);
	}

	/**
	 * @return the drawHex
	 */
	public boolean isDrawHex() {
		return drawHex;
	}

	/**
	 * @param drawHex the drawHex to set
	 */
	public void setDrawHex(boolean drawHex) {
		this.drawHex = drawHex;
	}

	/**
	 * @return the drawShipIcon
	 */
	public boolean isDrawShipIcon() {
		return drawShipIcon;
	}

	/**
	 * @param drawShipIcon the drawShipIcon to set
	 */
	public void setDrawShipIcon(boolean drawShipIcon) {
		this.drawShipIcon = drawShipIcon;
	}

	/**
	 * @return the unit
	 */
	public final Unit getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public final void setUnit(final Unit unit) {
		this.unit = unit;
		
		shipX = (getWidth() - unit.getCounter().getWidth(this)) / 2;
		shipY = (getHeight() - unit.getCounter().getHeight(this)) / 2;

		setArc(null);
		setAngle(-1);
	}
	
	/**
	 * @return the displayType
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * @param displayType the displayType to set
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/**
	 * @return the displayItem
	 */
	public String getDisplayItem() {
		return displayItem;
	}

	/**
	 * @param displayItem the displayItem to set
	 */
	public void setDisplayItem(String displayItem) {
		this.displayItem = displayItem;
	}

	/**
	 * the arc to draw under the images
	 * 
	 * @return the arc to draw under the images
	 */
	public final Arc getArc() {
		return arc;
	}

	/**
	 * the arc to draw under the images
	 * 
	 * @param arc the arc to draw under the images
	 */
	public final void setArc(final Arc arc) {
		this.arc = arc;
	}
	
	/**
	 * the angle to draw a line out from the center
	 * 
	 * @return the angle to draw a line out from the center
	 */
	public final int getAngle() {
		return angle;
	}

	/**
	 * the angle to draw a line out from the center
	 * 
	 * @param angle the angle to draw a line out from the center
	 */
	public final void setAngle(int angle) {
		this.angle = angle;
	}

	@Override
	public void paint(Graphics g) {		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2D.drawImage(backgroundImage, 0, 0, this);

		if (getUnit() != null) {
			if (getUnit() instanceof StructuralUnit) {
				if (DISPLAY_TYPE_SECTIONS.equalsIgnoreCase(getDisplayType())) {
					drawSections(g2D);
				} else if (DISPLAY_TYPE_WEAPONS.equalsIgnoreCase(getDisplayType())) {
					drawWeapons(g2D);
				} else if (DISPLAY_TYPE_DEFENSES.equalsIgnoreCase(getDisplayType())) {
					drawDefensiveSystems(g2D);
				}
			} else if (getUnit() instanceof FighterFlight) {
				drawFighterFlight(g2D);
			} else {
				Logger.debug("Unknown Unit type " + getUnit().getClass());
			}
		}				

		if (getAngle() >= 0) {
			g2D.setColor(Color.red);
			g2D.fillArc(2, 2, backgroundWidth - 4, backgroundHeight - 4, 360 - (getAngle() - 90) - 1, 1);
		}
	}
	
	private void drawSections(Graphics g) {
		boolean hasSections = false;
		
		if (!(getUnit() instanceof StructuralUnit)) {
			throw new IllegalArgumentException("Unit must be a " + StructuralUnit.class.getName());
		}
			
		StructuralUnit unit = (StructuralUnit) getUnit();
		
        // draw structurally sound sections
        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
    		Section section = iterator.next();
    		
            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(section.getName()))) && 
            		(!section.isDestroyed()) && (section.getArc() != null) && (!Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
//            	if (section.isStructurallySound()) {
            		g.drawImage(ArcRenderer.render(section.getArc(), new Color(Integer.parseInt("000020", 16)), 
            				backgroundWidth, backgroundHeight), 0, 0, this);
//        		}
    		}
    	}

//        // draw non-structurally sound sections
//        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
//    		Section section = iterator.next();
//    		
//            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(section.getName()))) &&
//            		(!section.isDestroyed()) && (section.getArc() != null) && (!Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
//            	if (!section.isStructurallySound()) {
//            		g.drawImage(ArcRenderer.render(section.getArc(), new Color(Integer.parseInt("000020", 16)), 
//            				backgroundWidth, backgroundHeight), 0, 0, this);
//        		}
//    		}
//    	}
        
		if (isDrawHex()) {
			g.drawImage(hexClusterImage, 0, 0, this);
		}
        
        // draw structurally sound sections
        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
    		Section section = iterator.next();
    		
            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(section.getName()))) &&
            		(!section.isDestroyed()) && (section.getArc() != null) && (!Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
            	if (section.isStructurallySound()) {
            		g.drawImage(ArcRenderer.renderOutline(section.getArc(), new Color(Integer.parseInt("000020", 16)), 
            				new Color(Integer.parseInt("8080CF", 16)), new Color(Integer.parseInt("8080CF", 16)),
            				backgroundWidth, backgroundHeight, section.getName()), 0, 0, this);
            		
            		hasSections = true;
        		}
    		}
    	}

        // draw non-structurally sound sections
        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
    		Section section = iterator.next();
    		
            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(section.getName()))) &&
            		(section.getArc() != null) && (!Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
            	if (!section.isStructurallySound()) {
            		g.drawImage(ArcRenderer.renderOutline(section.getArc(), new Color(Integer.parseInt("000020", 16)), 
            				new Color(Integer.parseInt("AA0000", 16)), new Color(Integer.parseInt("AA0000", 16)),
            				backgroundWidth, backgroundHeight, section.getName()), 0, 0, this);

            		hasSections = true;
            	}
    		}
    	}
        
        if ((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(Section.PRIMARY))) {
	        Section primary = unit.getSection(Section.PRIMARY);
	        if (primary != null) {
	        	int factor = 1;
	        	int x = 0;
	        	int y = 0;
	        	if (hasSections || unit.getGeneralHitLocationChart() != null) {
	        		factor = 3;
	        		x = backgroundWidth / factor;
	          		y = backgroundHeight / factor;
	          	}

	        	g.drawImage(ArcRenderer.render(Arc.FULL_360, new Color(Integer.parseInt("000020", 16)), 
						backgroundWidth / factor, backgroundHeight / factor), x, y, this);

	    		if ((factor == 1) && (isDrawHex())) {
	    			g.drawImage(hexClusterImage, 0, 0, this);
	    		}

	    		if (isDrawShipIcon()) {
	    			g.drawImage(unit.getCounter(), shipX, shipY, this);
	    		}		        	

	    		g.drawImage(ArcRenderer.renderOutline(Arc.FULL_360, new Color(Integer.parseInt("000020", 16)), 
						primary.isStructurallySound() ? new Color(Integer.parseInt("8080CF", 16)) : new Color(Integer.parseInt("AA0000", 16)), 
						primary.isStructurallySound() ? new Color(Integer.parseInt("8080CF", 16)) : new Color(Integer.parseInt("AA0000", 16)),
						backgroundWidth / factor, backgroundHeight / factor, Section.PRIMARY), x, y, this);
	        } else {
	    		if (isDrawShipIcon()) {
	    			g.drawImage(unit.getCounter(), shipX, shipY, this);
	    		}		        	
	        }
        } else {
    		if (isDrawShipIcon()) {
    			g.drawImage(unit.getCounter(), shipX, shipY, this);
    		}	        	
        }
	}
	
	private void drawWeapons(Graphics g) {
		if (!(getUnit() instanceof StructuralUnit)) {
			throw new IllegalArgumentException("Unit must be a " + StructuralUnit.class.getName());
		}
			
		drawSystems(((StructuralUnit) getUnit()).getSystemsOfClass(Weapon.class, true), g);
	}
	
	private void drawDefensiveSystems(Graphics g) {
		if (!(getUnit() instanceof StructuralUnit)) {
			throw new IllegalArgumentException("Unit must be a " + StructuralUnit.class.getName());
		}
			
		List<System> defensiveSystems = new ArrayList<System>(0);
		for (Iterator<System> iterator = ((StructuralUnit) getUnit()).getSystemsOfClass(DefensiveSystem.class, true).iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (((DefensiveSystem) system).getDefensiveBonus() > 0) {
				defensiveSystems.add(system);	
			}
		}			

		drawSystems(defensiveSystems, g);		
	}

	private void drawSystems(List<System> systems, Graphics g) {
		// draw arc fill
		for (Iterator<System> iterator = systems.iterator(); iterator.hasNext();) {
    		System system = iterator.next();
    		
            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(system.getFullName())))) {
            	if (!system.isDestroyed()) {
	        		g.drawImage(ArcRenderer.render(system.getArc(), new Color(Integer.parseInt("000020", 16)), 
	        				backgroundWidth, backgroundHeight), 0, 0, this);
            	}
    		}
		}

		if (isDrawHex()) {
			g.drawImage(hexClusterImage, 0, 0, this);
		}
        
        // draw outline and name of non-destroyed systems
		for (Iterator<System> iterator = systems.iterator(); iterator.hasNext();) {
    		System system = iterator.next();
    		
            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(system.getFullName()))) &&
            		(system.isValidTarget()) && (!system.isDestroyed())) {
        		g.drawImage(ArcRenderer.renderOutline(system.getArc(), new Color(Integer.parseInt("000020", 16)), 
        				new Color(Integer.parseInt("8080CF", 16)), new Color(Integer.parseInt("8080CF", 16)),
        				backgroundWidth, backgroundHeight, system.getFullName()), 0, 0, this);
    		}
		}

        // draw outline and name of destroyed systems
		for (Iterator<System> iterator = systems.iterator(); iterator.hasNext();) {
    		System system = iterator.next();
    		
            if (((getDisplayItem() == null) || (getDisplayItem().equalsIgnoreCase(system.getFullName()))) &&
            		((!system.isValidTarget()) || (system.isDestroyed()))) {
        		g.drawImage(ArcRenderer.renderOutline(system.getArc(), new Color(Integer.parseInt("000020", 16)), 
        				new Color(Integer.parseInt("AA0000", 16)), new Color(Integer.parseInt("AA0000", 16)),
        				backgroundWidth, backgroundHeight, system.getFullName()), 0, 0, this);
    		}
		}
        
		if (isDrawShipIcon()) {
			g.drawImage(unit.getCounter(), shipX, shipY, this);
		}	        	
	}
	
	private void drawFighterFlight(Graphics g) {
		if (!(getUnit() instanceof FighterFlight)) {
			throw new IllegalArgumentException("Unit must be a " + FighterFlight.class.getName());
		}
			
		if (isDrawHex()) {
			g.drawImage(hexClusterImage, 0, 0, this);
		}

		// TODO draw icon per fighter remaining
		if (isDrawShipIcon()) {
			g.drawImage(unit.getCounter(), shipX, shipY, this);
		}	        	
	}
	
}
