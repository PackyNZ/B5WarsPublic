/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.UnitID;

/**
 * SCS Editor application
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SCSEditor {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					new ControlSheetFrame(DataManager.getUnitDao().load(new UnitID("Drazi Warbird Cruiser", null, 0)), UsageMode.EDIT);
//					new ControlSheetFrame(null, UsageMode.EDIT);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
	}

}
