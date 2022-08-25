package net.b5gamer.b5wars.ui.menu;

/**
 * B5Wars application
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class B5WarsMenu {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		new MenuFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
	}

}
