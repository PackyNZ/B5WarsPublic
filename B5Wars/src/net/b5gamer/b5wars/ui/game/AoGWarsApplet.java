package net.b5gamer.b5wars.ui.game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import net.b5gamer.b5wars.game.Game;

/**
 * This class executes the game gui in hosted mode, where it runs within a clients
 * browser and communicates with a central server
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
@SuppressWarnings("removal")
public class AoGWarsApplet extends JApplet {

	private static final long serialVersionUID = -400075684685975607L;
	
    public void init() {
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

	/**
	 * create the game interface
	 */    
    @SuppressWarnings("deprecation")
	private void createGUI() {
    	GameInterface gameInterface = null;
        try {
        	gameInterface = new GameInterface(new Game("TODO"));
        } catch (Exception e) { 
        	e.printStackTrace();
        }
        
        HostedMenuBar menuBar = new HostedMenuBar(gameInterface);
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
        menuBar.setBorderPainted(false);
        
        setJMenuBar(menuBar);
        getContentPane().add(gameInterface, BorderLayout.CENTER);
    }
    
}
