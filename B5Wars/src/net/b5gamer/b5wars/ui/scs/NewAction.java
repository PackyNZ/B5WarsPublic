package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;

public class NewAction extends AbstractAction {

	private static final long serialVersionUID = 6036159039308952886L;

	public static final String[] UNIT_TYPES = {
//		"Capital Base",
		"Capital Ship",
//		"Enormous Base",
//		"Enormous Unit",
		"Heavy Combat Vessel",
//		"Light Combat Vessel",
		"Medium Ship"};
//		"Orbital Satellite"};
	
    private final String                unitType;
	private final ControlSheetInterface controlSheetInterface;
    
    public NewAction(String unitType, ControlSheetInterface controlSheetInterface) {
        super(unitType);
        
        if ((unitType == null) || !(unitType.trim().length() > 0)) {
            throw new IllegalArgumentException("unitType cannot be null or blank");
        } 
        if (controlSheetInterface == null) {
            throw new IllegalArgumentException("controlSheetInterface cannot be null");
        }
        
        this.unitType = unitType;
        this.controlSheetInterface = controlSheetInterface;
    }
    
	public String getUnitType() {
		return unitType;
	}

	public ControlSheetInterface getControlSheetInterface() {
		return controlSheetInterface;
	}

	public void actionPerformed(ActionEvent e) {
		getControlSheetInterface().setUnit(null);
		
		BufferedReader input = null;
		try {
			StringBuilder xml = new StringBuilder();
			input = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(getUnitType() + ".xml")));
			String line = null;
			while ((line = input.readLine()) != null) {
				xml.append(line);
				xml.append(System.getProperty("line.separator"));
			}
			
			UnitXMLFrame xmlFrame = new UnitXMLFrame("New " + getUnitType(), getControlSheetInterface());
			xmlFrame.setXML(xml.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) { 
				try {
					input.close();
				} catch (IOException er) { } 
			}
		}
	}
	
}
