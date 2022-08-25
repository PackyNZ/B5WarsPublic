package net.b5gamer.b5wars.ui.scs;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;

public final class ControlSheetPanelFactory {
	
	private ControlSheetPanelFactory() {
	}
	
	public static final ControlSheetPanel createControlSheetPanel(final Unit unit, final UsageMode mode) {
		if (unit instanceof StructuralUnit) {
			return new ShipControlSheetPanel((StructuralUnit) unit, mode);
		} else if (unit instanceof FighterFlight) {
			return new FighterControlSheetPanel((FighterFlight) unit, mode);
		} else {
			return null;
		}
	}
	
}
