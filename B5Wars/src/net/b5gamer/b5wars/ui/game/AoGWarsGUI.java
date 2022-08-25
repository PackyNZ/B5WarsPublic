package net.b5gamer.b5wars.ui.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import net.b5gamer.b5wars.game.Game;

/**
 * This class executes the game gui in standalone mode, where it runs locally on
 * a client machine
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class AoGWarsGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndDisplayGUI();
            }
        });
	}
	
	/**
	 * create and display the interface
	 * 
	 * @throws IOException 
	 */
	private static void createAndDisplayGUI() {
        JFrame frame = new JFrame(GameInterface.TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1250, 930)); // minimum for 0.25 scale map is 1001, 930
        
    	GameInterface gameInterface = null;
        try {
        	gameInterface = new GameInterface(new Game("TODO"));
        } catch (Exception e) { 
        	e.printStackTrace();
        }
        
        StandaloneMenuBar menuBar = new StandaloneMenuBar(gameInterface);
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(frame.getPreferredSize().width, 20));
        menuBar.setBorderPainted(false);
        
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(gameInterface, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
	
}
