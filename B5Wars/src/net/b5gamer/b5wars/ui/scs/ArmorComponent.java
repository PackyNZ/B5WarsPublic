/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.IconPosition;

public class ArmorComponent extends IconComponent {

	private static final long serialVersionUID = 5564752145793249908L;

	public  static       GeneralPath CIRCLE;
	private static final Stroke      STROKE     = new BasicStroke(1.5f);
	private static final Font        ARMOR_FONT = new Font("Arial", Font.PLAIN, 11);
	
	static {
		GeneralPath circle = new GeneralPath();
		circle.moveTo(6.75f,0.0f);
		circle.curveTo(10.4765625f,0.0f,13.5f,3.0234375f,13.5f,6.75f);
		circle.curveTo(13.5f,10.4765625f,10.4765625f,13.5f,6.75f,13.5f);
		circle.curveTo(3.0234375f,13.5f,0.0f,10.4765625f,0.0f,6.75f);
		circle.curveTo(0.0f,3.0234375f,3.0234375f,0.0f,6.75f,0.0f);
		circle.closePath(); 

		AffineTransform transform = AffineTransform.getTranslateInstance(2, 2);
		circle.transform(transform);
		
		CIRCLE = circle;		
	}

	public ArmorComponent(final System system) {
		super(system);

		Dimension size = new Dimension(CIRCLE.getBounds().width + 4, CIRCLE.getBounds().height + 4);
		setPreferredSize(size);
		setSize(size);
	}
	
	@Override
	public IconPosition getIconPosition() {
		return getSystem().getArmorIconPosition();
	}
	
	@Override
	public boolean contains(int x, int y) {
		return getBounds().contains(x, y) && CIRCLE.contains(x - getX(), y - getY());
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		graphics.setStroke(STROKE);

		// paint outline
		graphics.setColor(Color.white);
		graphics.fill(CIRCLE);
		graphics.setColor(Color.black);
		graphics.draw(CIRCLE);
		
		// paint number
		graphics.setColor((getSystem().getArmor() < getSystem().getBaseArmor()) ? CHANGED_COLOR : NORMAL_COLOR);
		graphics.setFont(ARMOR_FONT);
		
		FontMetrics fontMetrics = graphics.getFontMetrics(ARMOR_FONT);
		Rectangle2D textBounds = fontMetrics.getStringBounds(String.valueOf(getSystem().getArmor()), graphics);
		graphics.drawString(String.valueOf(getSystem().getArmor()), 
				(float) ((getSize().getWidth() - textBounds.getWidth()) / 2.0), 12.4f);	
		
		paintSelection(graphics);
	}
	
	@Override
	public String toString() {
		return super.toString() + " Armor";
	}
	
}
