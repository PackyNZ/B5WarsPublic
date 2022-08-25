package net.b5gamer.b5wars.ui;

import net.b5gamer.util.ClassLocator;

/**
 * This class displays a specified {@link net.b5gamer.b5wars.ui.B5WarsJFrame}
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class B5WarsGUI {

	private static String frameName; // name of the frame to display
	
	/**
	 * @param args the name of the {@link net.b5gamer.b5wars.ui.B5WarsJFrame} to display
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: " + B5WarsGUI.class.getName() + " <frameName>");
			System.exit(0);
		}
		frameName = args[0];
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					displayFrame();
				} catch (Exception e) {
					System.out.println("Falied to display " + frameName);
					e.printStackTrace();
				}
            }
        });
	}

	/**
	 * display the specified frame
	 * 
	 * @throws Exception failed to display the specified frame
	 */
	private static void displayFrame() throws Exception {
		Class<?> frameClass = ClassLocator.find(frameName, B5WarsJFrame.class, null);
		frameClass.getDeclaredConstructor().newInstance();
    }

}
