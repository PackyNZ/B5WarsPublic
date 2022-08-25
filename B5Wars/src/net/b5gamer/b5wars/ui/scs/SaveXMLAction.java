package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.unit.Unit;

public class SaveXMLAction extends AbstractAction {

	private static final long serialVersionUID = 5192125064000189180L;

	private final JTextComponent        component;
	private final ControlSheetInterface controlSheetInterface;
	
    public SaveXMLAction(JTextComponent component, ControlSheetInterface controlSheetInterface) {
        super("Save");
        
        if (component == null) {
            throw new IllegalArgumentException("component cannot be null");
        }
        if (controlSheetInterface == null) {
            throw new IllegalArgumentException("controlSheetInterface cannot be null");
        }
        
        this.component = component;
        this.controlSheetInterface = controlSheetInterface;
    }
    
	public JTextComponent getComponent() {
		return component;
	}

	public ControlSheetInterface getControlSheetInterface() {
		return controlSheetInterface;
	}

	public void actionPerformed(ActionEvent e) {
		try {
	    	Unit unit = UnitXMLReader.read(new ByteArrayInputStream(getComponent().getText().getBytes()));
	    	getControlSheetInterface().setUnit(unit);	    	
		} catch (Exception er) {
			JOptionPane.showMessageDialog(null, 
					"Unit Definition invalid..." + System.getProperty("line.separator") + System.getProperty("line.separator") + er.getMessage(), 
					"Save", JOptionPane.ERROR_MESSAGE);
		}		
	}

}
