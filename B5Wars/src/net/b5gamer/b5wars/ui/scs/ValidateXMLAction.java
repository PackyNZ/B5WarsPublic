package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;

public class ValidateXMLAction extends AbstractAction {

	private static final long serialVersionUID = -5054292804459010267L;

	private final JTextComponent component;
	
    public ValidateXMLAction(JTextComponent component) {
        super("Validate");
        
        if (component == null) {
            throw new IllegalArgumentException("component cannot be null");
        }
        
        this.component = component;
    }
    
	public JTextComponent getComponent() {
		return component;
	}

	public void actionPerformed(ActionEvent e) {
		try {
	    	UnitXMLReader.read(new ByteArrayInputStream(getComponent().getText().getBytes()));
	    	
			JOptionPane.showMessageDialog(null, "Unit Definition is valid.", "Validate", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception er) {
			JOptionPane.showMessageDialog(null, 
					"Unit Definition invalid..." + System.getProperty("line.separator") + System.getProperty("line.separator") + er.getMessage(), 
					"Validate", JOptionPane.ERROR_MESSAGE);
		}		
	}

}
