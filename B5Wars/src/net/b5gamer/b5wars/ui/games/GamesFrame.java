package net.b5gamer.b5wars.ui.games;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.b5gamer.b5wars.ui.B5WarsJFrame;
import net.b5gamer.b5wars.ui.game.GameFrame;

public class GamesFrame extends B5WarsJFrame implements ActionListener {
		
	private static final long serialVersionUID = -2960767257125926312L;

	private GamesPanel gamesPanel = null;

	public GamesFrame() {
        super("Games", 600, 400);
        
        gamesPanel = new GamesPanel(this);
        setContentPane(gamesPanel);

        pack();
        setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (GamesPanel.ACTION_PLAY.equals(e.getActionCommand())) {
			new GameFrame(gamesPanel.getSelectedGame());
			setVisible(false);
		}
	}	
	
}
