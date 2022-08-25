package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import net.b5gamer.b5wars.io.unit.UnitXMLWriter;

public class RefreshXMLAction extends AbstractAction {

	private static final long serialVersionUID = 5066082447725868986L;

	private final JTextComponent        component;
	private final ControlSheetInterface controlSheetInterface;
	
    public RefreshXMLAction(JTextComponent component, ControlSheetInterface controlSheetInterface) {
        super("Refresh");
        
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
			ByteArrayOutputStream xml = new ByteArrayOutputStream();
			UnitXMLWriter.write(getControlSheetInterface().getUnit(), xml);
			getComponent().setText(xml.toString());
			getComponent().setCaretPosition(0);
		} catch (Exception er) {
			JOptionPane.showMessageDialog(null, 
					"Failed to process Unit..." + System.getProperty("line.separator") + System.getProperty("line.separator") + er.getMessage(), 
					"Refresh", JOptionPane.ERROR_MESSAGE);
		}		
	}

}
