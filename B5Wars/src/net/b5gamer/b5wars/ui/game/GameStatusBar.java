package net.b5gamer.b5wars.ui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.b5gamer.swing.JAnnotatedLabel;
import net.b5gamer.swing.JStatusBar;
import net.b5gamer.util.StatusListener;

public class GameStatusBar extends JStatusBar implements StatusListener {

	private static final long serialVersionUID = -5667196680247198812L;

	public GameStatusBar(Color background, Color foreground, Font font) {
		super(new BorderLayout());
		setBackground(background);
		setForeground(foreground);
		setFont(font);
        setBorder(BorderFactory.createMatteBorder(1, 5, 1, 5, background));

		
		
		JLabel turnInfo = new JAnnotatedLabel("Turn: ", null);
		turnInfo.setBackground(background);
		turnInfo.setForeground(foreground);
		turnInfo.setFont(font);
		add(turnInfo, BorderLayout.WEST);
		getLabels().put(MapControl.STATUS_TURN, turnInfo);

		JLabel locationInfo = new JAnnotatedLabel("Location: ", null);
		locationInfo.setBackground(background);
		locationInfo.setForeground(foreground);
		locationInfo.setFont(font);
		add(locationInfo, BorderLayout.EAST);
		getLabels().put(MapControl.STATUS_LOCATION, locationInfo);

		
		
	}

}
