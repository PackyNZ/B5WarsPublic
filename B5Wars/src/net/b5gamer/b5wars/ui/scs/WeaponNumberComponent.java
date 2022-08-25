/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.icon.IconPosition;

public class WeaponNumberComponent extends IconComponent {

	private static final long serialVersionUID = -3322953769538795886L;

	private static final Font NUMBER_FONT = (new Font("Arial", Font.BOLD, 13)).deriveFont(AffineTransform.getScaleInstance(1.0, 0.8));

	public WeaponNumberComponent(final Weapon system) {
		super(system);
		
		Dimension size = new Dimension(16, 11);
		setPreferredSize(size);
		setSize(size);				
	}

	public Weapon getSystem() {
		return (Weapon) super.getSystem();
	}

	@Override
	public IconPosition getIconPosition() {
		return getSystem().getWeaponNumberPosition();
	}
	
	@Override
	public boolean contains(int x, int y) {
		return getBounds().contains(x, y);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// paint number
		graphics.setColor(Color.black);
		graphics.setFont(NUMBER_FONT);
		
		FontMetrics fontMetrics = graphics.getFontMetrics(NUMBER_FONT);
		Rectangle2D textBounds = fontMetrics.getStringBounds(String.valueOf(getSystem().getName()), graphics);
		graphics.drawString(String.valueOf(getSystem().getName()), 
				(float) ((getSize().getWidth() - textBounds.getWidth()) / 2.0), 8.75f);	
		
		paintSelection(graphics);		
	}
	
	@Override
	public String toString() {
		return super.toString() + " Number";
	}
	
}
