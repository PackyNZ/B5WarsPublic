package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class FitWindowAction extends AbstractAction {

	private static final long serialVersionUID = -1225242706243835446L;

	private final ControlSheetPanel controlSheetPanel;
    
    public FitWindowAction(ControlSheetPanel controlSheetPanel) {
        super("Fit Window");
        
        if (controlSheetPanel == null) {
            throw new IllegalArgumentException("controlSheetPanel cannot be null");
        }
        
        this.controlSheetPanel = controlSheetPanel;
    }
    
	public ControlSheetPanel getControlSheetPanel() {
		return controlSheetPanel;
	}

	public void actionPerformed(ActionEvent e) {
		getControlSheetPanel().setFitToScreen(true);
		getControlSheetPanel().repaint();
	}

}
