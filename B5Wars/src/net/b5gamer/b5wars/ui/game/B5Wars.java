package net.b5gamer.b5wars.ui.game;

import net.b5gamer.b5wars.game.Game;

/**
 * B5Wars application
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class B5Wars {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		new GameFrame(new Game("Drazi vs. Brakiri 3000pts (Sample)"));
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
	}

}
