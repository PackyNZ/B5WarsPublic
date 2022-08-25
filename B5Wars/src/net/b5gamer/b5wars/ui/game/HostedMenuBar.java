package net.b5gamer.b5wars.ui.game;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class HostedMenuBar extends GameMenuBar {

	private static final long serialVersionUID = 6086231609252509909L;

	public HostedMenuBar(final GameInterface gameInterface) {
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
		
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);		
	}	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
