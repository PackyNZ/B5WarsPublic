package net.b5gamer.b5wars.ui.game;

import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

public abstract class GameMenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = -7478872785063981278L;
	
	private final GameInterface gameInterface; // the game interface this menu communicates with
	
	/**
	 * @param gameInterface the game interface this menu communicates with
	 */
	public GameMenuBar(final GameInterface gameInterface) {
		super();
		
        if (gameInterface == null) {
            throw new IllegalArgumentException("gameInterface cannot be null");
        } 
        
        this.gameInterface = gameInterface;

        setBackground(gameInterface.getBackground());
        setForeground(gameInterface.getForeground());      
        setFont(gameInterface.getFont());        
	}

	/**
	 * the game interface this menu communicates with
	 * 
	 * @return the game interface this menu communicates with
	 */
	public GameInterface getGameInterface() {
		return gameInterface;
	}	
	
}
