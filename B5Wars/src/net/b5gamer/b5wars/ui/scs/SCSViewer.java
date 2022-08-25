/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

/**
 * SCS Viewer application
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SCSViewer {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					new ControlSheetFrame(null, UsageMode.VIEW);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
	}

}
