package net.b5gamer.b5wars.ui.game;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class StandaloneMenuBar extends GameMenuBar {

	private static final long serialVersionUID = 6086231609252509909L;

	private static final String ACTION_EXIT = "Exit";
	
	public StandaloneMenuBar(final GameInterface gameInterface) {
		super(gameInterface);
		
        // define file menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setBackground(gameInterface.getBackground());
        fileMenu.setForeground(gameInterface.getForeground());
        add(fileMenu);

        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newGameMenuItem.addActionListener(this);
        fileMenu.add(newGameMenuItem);

        JMenuItem saveGameMenuItem = new JMenuItem("Save Game");
        saveGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveGameMenuItem.addActionListener(this);
        fileMenu.add(saveGameMenuItem);

        fileMenu.addSeparator();
		
        JMenuItem exitMenuItem = new JMenuItem(ACTION_EXIT);
        exitMenuItem.setActionCommand(ACTION_EXIT);
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);		
	}	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == ACTION_EXIT) {
			System.exit(0);
		}
	}

}
