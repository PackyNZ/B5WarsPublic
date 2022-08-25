package net.b5gamer.b5wars.ui.games;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import net.b5gamer.b5wars.ui.game.GameInterface;
import net.b5gamer.b5wars.ui.game.HostedMenuBar;

@SuppressWarnings("removal")
public class GamesApplet extends JApplet implements ActionListener {
	
	private static final long serialVersionUID = -7137722219344501170L;

	private GamesPanel    gamesPanel    = null;
	private GameInterface gameInterface = null;
	
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
        setLayout(null);
    	
    	gamesPanel = new GamesPanel(this);
    	gamesPanel.setSize(getSize());
    	getContentPane().add(gamesPanel);
    }
    
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {		
		if (GamesPanel.ACTION_PLAY.equals(e.getActionCommand())) {
	        try {
	        	gameInterface = new GameInterface(gamesPanel.getSelectedGame());
	        	gameInterface.setSize(getSize().width, getSize().height - 20);
	        } catch (Exception er) { 
	        	er.printStackTrace();
	        }
	        
	        getContentPane().add(gameInterface);
	        gamesPanel.setVisible(false);
	        
	        HostedMenuBar menuBar = new HostedMenuBar(gameInterface);
	        menuBar.setOpaque(true);
	        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
	        menuBar.setBorderPainted(false);
	        
	        setJMenuBar(menuBar);
		}
	}
	
}
