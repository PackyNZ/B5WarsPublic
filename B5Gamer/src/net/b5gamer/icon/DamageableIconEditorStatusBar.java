/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DamageableIconEditorStatusBar extends JPanel implements DamageBoxListener {

	private static final long serialVersionUID = 978969017379194070L;

	private   final JLabel hitsLabel;
	protected final int    maximumHits;
	
	public DamageableIconEditorStatusBar(int maximumHits) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0), true);
        setBorder(BorderFactory.createMatteBorder(1, 5, 1, 5, getBackground()));
        setBackground(Color.white);
        
		JLabel label = new JLabel("Hits: ");
		add(label);
		hitsLabel = new JLabel();
		add(hitsLabel);
		JLabel maxLabel = new JLabel("/" + maximumHits);
		add(maxLabel);		
		
		this.maximumHits = maximumHits;
	}
	
	public JLabel getHitsLabel() {
		return hitsLabel;
	}

	public int getMaximumHits() {
		return maximumHits;
	}

	public void hitsValueChanged(HitsEvent e) {
		getHitsLabel().setText(String.valueOf(e.getValue()));
		getHitsLabel().setForeground((e.getValue() == getMaximumHits()) ? Color.black : Color.red);
	}
	
}
