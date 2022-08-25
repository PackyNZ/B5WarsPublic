package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

public class AdjustSystemsAction extends AbstractAction {

	private static final long serialVersionUID = 8136604830883107817L;

	private final ControlSheetPanel controlSheetPanel;
    
    public AdjustSystemsAction(ControlSheetPanel controlSheetPanel) {
        super("Adjust Systems");
        
        if (controlSheetPanel == null) {
            throw new IllegalArgumentException("controlSheetPanel cannot be null");
        }
        
        this.controlSheetPanel = controlSheetPanel;
    }
    
	public ControlSheetPanel getControlSheetPanel() {
		return controlSheetPanel;
	}

	public void actionPerformed(ActionEvent e) {
		if (getControlSheetPanel().getUnit() != null) {
//			getControlSheetPanel().getUnit().handleEndOfTurnActions(); // TODO
			
			if (getControlSheetPanel() instanceof ShipControlSheetPanel) {
				for (Iterator<IconComponent> iterator = ((ShipControlSheetPanel) getControlSheetPanel()).getIcons().iterator(); iterator.hasNext();) {
					IconComponent component = iterator.next();
					
					if (component instanceof SystemComponent) {
						SystemComponent systemComponent = (SystemComponent) component;
						
						systemComponent.getMarkedDamageBoxes().addAll(systemComponent.getNewlyMarkedDamageBoxes());
						systemComponent.getNewlyMarkedDamageBoxes().clear();
					}
				}
			}
			
			getControlSheetPanel().repaint();
		}
	}

}
