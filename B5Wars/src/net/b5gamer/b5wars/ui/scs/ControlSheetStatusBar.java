/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.b5gamer.swing.JAnnotatedLabel;
import net.b5gamer.swing.JStatusBar;
import net.b5gamer.util.StatusListener;

public class ControlSheetStatusBar extends JStatusBar implements StatusListener {

	private static final long serialVersionUID = -4983107182302480731L;

	public ControlSheetStatusBar() {
		super(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1, 5, 1, 5, getBackground()));

		JLabel zoom = new JAnnotatedLabel("Zoom: ", null);
		add(zoom, BorderLayout.WEST);
		getLabels().put("Zoom", zoom);

		JLabel selected = new JLabel();
		selected.setHorizontalAlignment(SwingConstants.CENTER);
		add(selected, BorderLayout.CENTER);
		getLabels().put("Selected", selected);		

		JLabel position = new JLabel();
		add(position, BorderLayout.EAST);
		getLabels().put("Position", position);		
	}

}
