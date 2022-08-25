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

import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.TractorBeam;
import net.b5gamer.icon.IconPosition;

public class PowerComponent extends IconComponent {

	private static final long serialVersionUID = 1047328439766449324L;

	public  static       GeneralPath DIAMOND;
	private static final Stroke      STROKE     = new BasicStroke(1.5f);
	private static final Font        POWER_FONT = new Font("Arial", Font.PLAIN, 9);

	static {
		GeneralPath diamond = new GeneralPath();
		diamond.moveTo(0.25f,7.25f);
		diamond.lineTo(7.0f,0.5f);
		diamond.lineTo(13.75f,7.25f);
		diamond.lineTo(7.0f,14.0f);
		diamond.lineTo(0.25f,7.25f);
		diamond.closePath(); 
		
		AffineTransform transform = AffineTransform.getTranslateInstance(2, 2);
		diamond.transform(transform);
		
		DIAMOND = diamond;		
	}

	public PowerComponent(final PoweredSystem system) {
		super(system);
		
		Dimension size = new Dimension(DIAMOND.getBounds().width + 4, DIAMOND.getBounds().height + 4);
		setPreferredSize(size);
		setSize(size);
	}
	
	public PoweredSystem getSystem() {
		return (PoweredSystem) super.getSystem();
	}

	@Override
	public IconPosition getIconPosition() {
		return getSystem().getPowerIconPosition();
	}
	
	@Override
	public boolean contains(int x, int y) {
		return getBounds().contains(x, y) && DIAMOND.contains(x - getX(), y - getY());
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
		graphics.fill(DIAMOND);
		graphics.setColor(Color.black);
		graphics.draw(DIAMOND);
		
		// paint number
		graphics.setColor((getSystem().getPowerRequirement() < getSystem().getBasePowerRequirement()) ? CHANGED_COLOR : NORMAL_COLOR);
		graphics.setFont(POWER_FONT);
		
		FontMetrics fontMetrics = graphics.getFontMetrics(POWER_FONT);
		String text = (getSystem() instanceof TractorBeam) ? "?" : String.valueOf(getSystem().getPowerRequirement());
		Rectangle2D textBounds = fontMetrics.getStringBounds(text, graphics);
		graphics.drawString(text, (float) ((getSize().getWidth() - textBounds.getWidth()) / 2.0), 12.25f);	
		
		paintSelection(graphics);		
	}
	
	@Override
	public String toString() {
		return super.toString() + " Power";
	}
	
}
