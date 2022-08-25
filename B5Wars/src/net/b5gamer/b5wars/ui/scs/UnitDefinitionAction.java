package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.b5gamer.b5wars.io.unit.UnitXMLWriter;

public class UnitDefinitionAction extends AbstractAction {

	private static final long serialVersionUID = -6527758357597208304L;

	private final ControlSheetInterface controlSheetInterface;
    
    public UnitDefinitionAction(ControlSheetInterface controlSheetInterface) {
        super("Unit Definition");
        
        if (controlSheetInterface == null) {
            throw new IllegalArgumentException("controlSheetInterface cannot be null");
        }
        
        this.controlSheetInterface = controlSheetInterface;
    }
    
	public ControlSheetInterface getControlSheetInterface() {
		return controlSheetInterface;
	}

	public void actionPerformed(ActionEvent e) {		
		try {
			ByteArrayOutputStream xml = new ByteArrayOutputStream();
			UnitXMLWriter.write(getControlSheetInterface().getUnit(), xml);
			UnitXMLFrame xmlFrame = new UnitXMLFrame(getControlSheetInterface().getUnit().getName(), getControlSheetInterface());
			xmlFrame.setXML(xml.toString());
		} catch (Exception er) {
			JOptionPane.showMessageDialog(null, 
					"Failed to process Unit..." + System.getProperty("line.separator") + System.getProperty("line.separator") + er.getMessage(), 
					"Unit Definition", JOptionPane.ERROR_MESSAGE);
		}	
		
	}

}
