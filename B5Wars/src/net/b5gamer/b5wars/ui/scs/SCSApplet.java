/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import net.b5gamer.b5wars.io.DataAccessMode;
import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;

@SuppressWarnings("removal")
public class SCSApplet extends JApplet {

	private static final long serialVersionUID = -1835486013347374621L;

	private Unit      unit;
	private UsageMode mode;

	@SuppressWarnings("deprecation")
	public void init() {
		DataManager.setServerUrl(this.getCodeBase().toString());
    	DataManager.setDataAccessMode(DataAccessMode.REMOTE);

    	// set unit
    	String name = getParameter("name");
    	if ((name != null) && (name.trim().length() > 0)) {
    		int version = 0;
    		try {
    			version = Integer.parseInt(getParameter("version"));
    		} catch (Exception e) {}

    		try {
				setUnit(DataManager.getUnitDao().load(new UnitID(name, getParameter("model"), version)));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	} else {
    		setUnit(null);
    	}

    	// set mode
    	setUsageMode(UsageMode.getUsageMode(getParameter("mode")));

    	try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	createGUI();
                }
            });
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	public Unit getUnit() {
		return unit;
	}

	protected void setUnit(Unit unit) {
		this.unit = unit;
	}

	public UsageMode getUsageMode() {
		return mode;
	}

	protected void setUsageMode(UsageMode mode) {
		this.mode = mode;
	}

	@SuppressWarnings("deprecation")
	private void createGUI() {
		ControlSheetInterface controlSheetInterface = new ControlSheetInterface(getUnit(), getUsageMode());
		
		ControlSheetMenuBar menuBar = new ControlSheetMenuBar(controlSheetInterface);
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
        menuBar.setBorderPainted(true);
        setJMenuBar(menuBar);
	    
        setContentPane(controlSheetInterface);
	}

}
