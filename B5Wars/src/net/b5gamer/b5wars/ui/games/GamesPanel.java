package net.b5gamer.b5wars.ui.games;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import net.b5gamer.b5wars.game.Game;
import net.b5gamer.b5wars.ui.B5WarsJPanel;

public class GamesPanel extends B5WarsJPanel {

	private static final long serialVersionUID = -4468578467330449601L;

	protected static final String ACTION_PLAY = "Play";
	
	public GamesPanel(final ActionListener listener) {
		JComboBox<String> unitList = new JComboBox<String>();
        unitList.setSelectedIndex(-1);
        unitList.addActionListener(listener);
    	unitList.addItem("Drazi vs. Brakiri 3000pts (Sample)");
        add(unitList, BorderLayout.NORTH);
        
        JButton playButton = new JButton("Play");
		playButton.setSize(new Dimension(140, 25));
//		playButton.setForeground(new Color(0, 0, 32));
//		playButton.setFont(new Font("Arial", Font.BOLD, 12));
		playButton.addActionListener(listener);
		playButton.setActionCommand(ACTION_PLAY);
		add(playButton, BorderLayout.SOUTH);		
	}
	
	public Game getSelectedGame() {
		return new Game("Drazi vs. Brakiri 3000pts (Sample)");
	}
	
}
