package net.b5gamer.b5wars.ui.game;

import java.awt.Dimension;

import javax.swing.JFrame;

import net.b5gamer.b5wars.game.Game;
import net.b5gamer.b5wars.ui.B5WarsJFrame;

public class GameFrame extends B5WarsJFrame {

	private static final long serialVersionUID = -7889886124603324210L;

	public GameFrame(Game game) {
        super(TITLE + " - " + game.getName(), 1024, 768);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        GameInterface gameInterface = null;
        try {
        	gameInterface = new GameInterface(game);
        } catch (Exception e) { 
        	e.printStackTrace();
        }
  
        StandaloneMenuBar menuBar = new StandaloneMenuBar(gameInterface);
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
        menuBar.setBorderPainted(false);
        
        setJMenuBar(menuBar);
        setContentPane(gameInterface);

        pack();
        setVisible(true);
	}
	
}
