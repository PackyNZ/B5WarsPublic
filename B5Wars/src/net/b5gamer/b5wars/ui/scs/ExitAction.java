package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = -491776745721882693L;

	public ExitAction() {
        super("Exit");
    }
    
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
