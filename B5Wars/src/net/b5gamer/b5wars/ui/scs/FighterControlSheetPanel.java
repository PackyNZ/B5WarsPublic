/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;

public class FighterControlSheetPanel extends ControlSheetPanel {

	private static final long serialVersionUID = 2324941566009108294L;

	public FighterControlSheetPanel(final FighterFlight unit) {
		this(unit, null);
	}

	public FighterControlSheetPanel(final FighterFlight unit, final UsageMode mode) {
		super(unit, mode);
	}

    public FighterFlight getUnit() {
		return (FighterFlight) super.getUnit();
	}

    public void setUnit(final Unit unit) {
    	if (!(unit instanceof FighterFlight)) {
    		throw new IllegalArgumentException("unit must be a " + FighterFlight.class.getSimpleName());
    	}
    	
		super.setUnit(unit);
		
		getControlSheetComponents().add(new DatacardComponent(unit));
    	getControlSheetComponents().add(new WeaponDataComponent());
    }

	@Override
	protected JPopupMenu getPopupMenu() {
		// TODO
		return null;
	}
    
	@Override
	public void paintControlSheet(Graphics2D graphics, boolean exporting) {
		if (getUnit() != null) {
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, (int) getSCSWidth(), (int) getSCSHeight());		
			
			// TODO
			if (exporting || isShowData()) {
				if (getLogo() != null) {
					paint(graphics, getLogo());
				}
	
				for (Iterator<JComponent> iterator = getControlSheetComponents().iterator(); iterator.hasNext();) {
					paint(graphics, iterator.next());
				}
			}
		}
	}
	
}
