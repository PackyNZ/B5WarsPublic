package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ToggleDataAction extends AbstractAction {

	private static final long serialVersionUID = 7514224284882329611L;

	private final ControlSheetPanel controlSheetPanel;
    
    public ToggleDataAction(ControlSheetPanel controlSheetPanel) {
        super("Data");
        
        if (controlSheetPanel == null) {
            throw new IllegalArgumentException("controlSheetPanel cannot be null");
        }
        
        this.controlSheetPanel = controlSheetPanel;
    }
    
	public ControlSheetPanel getControlSheetPanel() {
		return controlSheetPanel;
	}

	public void actionPerformed(ActionEvent e) {
		getControlSheetPanel().setShowData(!getControlSheetPanel().isShowData());
		getControlSheetPanel().repaint();
	}

}
