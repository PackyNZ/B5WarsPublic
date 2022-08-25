/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.IconPosition;

public class ArcComponent extends IconComponent {

	private static final long serialVersionUID = -2620021200973680523L;

	public  static       GeneralPath[] HEXES     = new GeneralPath[7];
	private static final int           ARC_SIZE  = 16;
	private static final Color         ARC_COLOR = new Color(104, 105, 108);
	private static final Stroke        STROKE    = new BasicStroke(0.375f);

	static {
		GeneralPath hex = new GeneralPath();
		hex.moveTo(4.90625f,1.625f);
		hex.lineTo(6.59375f,1.625f);
		hex.lineTo(7.4375f,3.3125f);
		hex.lineTo(6.59375f,5.0f);
		hex.lineTo(4.90625f,5.0f);
		hex.lineTo(4.0625f,3.3125f);
		hex.lineTo(4.90625f,1.625f);
		hex.closePath();
		HEXES[0] = hex;
		
		hex = new GeneralPath();
		hex.moveTo(4.90625f,5.0f);
		hex.lineTo(6.59375f,5.0f);
		hex.lineTo(7.4375f,6.6875f);
		hex.lineTo(6.59375f,8.375f);
		hex.lineTo(4.90625f,8.375f);
		hex.lineTo(4.0625f,6.6875f);
		hex.lineTo(4.90625f,5.0f);
		hex.closePath();
		HEXES[1] = hex;
		
		hex = new GeneralPath();
		hex.moveTo(4.90625f,8.375f);
		hex.lineTo(6.59375f,8.375f);
		hex.lineTo(7.4375f,10.0625f);
		hex.lineTo(6.59375f,11.75f);
		hex.lineTo(4.90625f,11.75f);
		hex.lineTo(4.0625f,10.0625f);
		hex.lineTo(4.90625f,8.375f);
		hex.closePath(); 
		HEXES[2] = hex;
		
		hex = new GeneralPath();
		hex.moveTo(7.4375f,6.6875f);
		hex.lineTo(9.125f,6.6875f);
		hex.lineTo(9.96875f,8.375f);
		hex.lineTo(9.125f,10.0625f);
		hex.lineTo(7.4375f,10.0625f);
		hex.lineTo(6.59375f,8.375f);
		hex.lineTo(7.4375f,6.6875f);
		hex.closePath();
		HEXES[3] = hex;
		
		hex = new GeneralPath();
		hex.moveTo(7.4375f,3.3125f);
		hex.lineTo(9.125f,3.3125f);
		hex.lineTo(9.96875f,5.0f);
		hex.lineTo(9.125f,6.6875f);
		hex.lineTo(7.4375f,6.6875f);
		hex.lineTo(6.59375f,5.0f);
		hex.lineTo(7.4375f,3.3125f);
		hex.closePath(); 
		HEXES[4] = hex;
		
		hex = new GeneralPath();
		hex.moveTo(2.375f,6.6875f);
		hex.lineTo(4.0625f,6.6875f);
		hex.lineTo(4.90625f,8.375f);
		hex.lineTo(4.0625f,10.0625f);
		hex.lineTo(2.375f,10.0625f);
		hex.lineTo(1.53125f,8.375f);
		hex.lineTo(2.375f,6.6875f);
		hex.closePath(); 
		HEXES[5] = hex;
		
		hex = new GeneralPath();
		hex.moveTo(2.375f,3.3125f);
		hex.lineTo(4.0625f,3.3125f);
		hex.lineTo(4.90625f,5.0f);
		hex.lineTo(4.0625f,6.6875f);
		hex.lineTo(2.375f,6.6875f);
		hex.lineTo(1.53125f,5.0f);
		hex.lineTo(2.375f,3.3125f);
		hex.closePath(); 
		HEXES[6] = hex;		
		
		// reposition hexes
		AffineTransform transform = AffineTransform.getTranslateInstance(4.2, 3.4);
		for (int index = 0; index < HEXES.length; index++) {
			HEXES[index].transform(transform);
		}
	}

	public ArcComponent(final System system) {
		super(system);
		
		Dimension size = new Dimension(ARC_SIZE + 4, ARC_SIZE + 4);
		setPreferredSize(size);
		setSize(size);
	}
	
	@Override
	public IconPosition getIconPosition() {
		return getSystem().getArcIconPosition();
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

		graphics.setStroke(STROKE);

		// paint arc
		if (getSystem().getArc() != null) {
	        Arc2D arc = getSystem().getArc().toShape(ARC_SIZE, ARC_SIZE);

			graphics.setColor(ARC_COLOR);
			graphics.fill(arc);
			graphics.setColor(Color.black);
			graphics.draw(arc);
		}
		
		// fill hexes
		if ((getSystem().getArc() != null) && (getSystem().getArc().getDegrees() >= 360)) {
			graphics.setColor(Color.white);
			for (int index = 0; index < HEXES.length; index++) {
				graphics.fill(HEXES[index]);
			}
		}
		
		// paint hexes
		graphics.setColor(Color.black);
		for (int index = 0; index < HEXES.length; index++) {
			graphics.draw(HEXES[index]);
		}
		
		paintSelection(graphics);		
	}
	
	@Override
	public String toString() {
		return super.toString() + " Arc";
	}
	
	
}
