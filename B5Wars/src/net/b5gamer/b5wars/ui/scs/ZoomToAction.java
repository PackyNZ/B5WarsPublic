package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ZoomToAction extends AbstractAction {

	private static final long serialVersionUID = 2961363777057956187L;

	public static final int[] ZOOM_LEVELS = {50, 75, 100, 125, 150, 200, 400, 800};
	
    private final int               zoomLevel;
	private final ControlSheetPanel controlSheetPanel;
    
    public ZoomToAction(int zoomLevel, ControlSheetPanel controlSheetPanel) {
        super(zoomLevel + "%");
        
        if (controlSheetPanel == null) {
            throw new IllegalArgumentException("controlSheetPanel cannot be null");
        }
        
        this.zoomLevel         = zoomLevel;
        this.controlSheetPanel = controlSheetPanel;
    }
    
	public int getZoomLevel() {
		return zoomLevel;
	}

	public ControlSheetPanel getControlSheetPanel() {
		return controlSheetPanel;
	}

	public void actionPerformed(ActionEvent e) {
		getControlSheetPanel().setFitToScreen(false);
		getControlSheetPanel().setScale(getZoomLevel() / 100.0);
		getControlSheetPanel().repaint();
	}

}
