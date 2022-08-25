package net.b5gamer.b5wars.ui.scs;

import javax.swing.JInternalFrame;

import net.b5gamer.b5wars.unit.Unit;

public class ControlSheetInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = -2160351749634288588L;

	private final Unit                  unit;
	private final ControlSheetInterface controlSheetInterface;
	
	public ControlSheetInternalFrame(final Unit unit, final UsageMode mode) {
		super((unit != null) ? unit.getGivenName() : "", true, true, true, true);
		
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }
        
        this.unit = unit;
        this.controlSheetInterface = new ControlSheetInterface(unit, mode);
        
        setContentPane(getControlSheetInterface());
	}

	public Unit getUnit() {
		return unit;
	}

	public ControlSheetInterface getControlSheetInterface() {
		return controlSheetInterface;
	}
	
}
