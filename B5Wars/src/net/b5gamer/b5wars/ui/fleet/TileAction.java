package net.b5gamer.b5wars.ui.fleet;

import javax.swing.*;
import java.awt.event.*;

public class TileAction extends AbstractAction {
    
	private static final long serialVersionUID = -2179933384751750702L;

	private ControlSheetDesktopPane desktopPane;
    
    public TileAction(String name, ControlSheetDesktopPane desktopPane) {
        super(name);
        this.desktopPane = desktopPane;
    }
    
    public ControlSheetDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(ControlSheetDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	public void actionPerformed(ActionEvent e) {        
    	if (getDesktopPane() != null) {
    		getDesktopPane().setTiled(true);
    	}
    }
	
} 