/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JComponent;

import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.IconPosition;

public abstract class IconComponent extends JComponent implements Icon, Selectable, Deployable {

	private static final long serialVersionUID = -831290603902297934L;

	protected static final Color  NORMAL_COLOR     = Color.black;
	protected static final Color  CHANGED_COLOR    = new Color(204, 0, 0);
	protected static final Color  SELECTION_COLOR  = Color.red;
	protected static final Stroke SELECTION_STROKE = new BasicStroke(1.0f);
	
	private System  system   = null;
	private boolean selected = false;
	private boolean deployed = false;
	
	public IconComponent(System system) {
		this(system, false, false);
	}

	public IconComponent(System system, boolean selected, boolean deployed) {
		super();

		setSystem(system);
		this.selected = selected;
		this.deployed = deployed;
		
		setLocation((int) getIconPosition().getX(), (int) getIconPosition().getY());
		setToolTipText(toString());
	}

	public System getSystem() {
		return system;
	}

	protected void setSystem(System system) {
        if (system == null) {
            throw new IllegalArgumentException("system cannot be null");
        }

        this.system = system;
	}
	
	protected abstract IconPosition getIconPosition();

	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isDeployed() {
		return deployed;
	}
	
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		getIconPosition().setLocation(x, y);
		super.setBounds(x, y, width, height);
	}

	@Override
	public void setBounds(Rectangle r) {
		getIconPosition().setLocation(r.getBounds().getLocation());
		super.setBounds(r);
	}

	@Override
	public void setLocation(int x, int y) {
		getIconPosition().setLocation(x, y);
		super.setLocation(x, y);		
	}

	@Override
	public void setLocation(Point p) {
		getIconPosition().setLocation(p);
		super.setLocation(p);
	}
	
	public int getIconHeight() {
		return getHeight();
	}

	public int getIconWidth() {
		return getWidth();
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D graphics = (Graphics2D) g;
		AffineTransform transform = graphics.getTransform();
		graphics.transform(AffineTransform.getTranslateInstance(x, y));
		paint(graphics);
		graphics.setTransform(transform);
	}

	protected void paintSelection(Graphics2D graphics) {
		if (isSelected()) {
			graphics.setStroke(SELECTION_STROKE);
			graphics.setColor(SELECTION_COLOR);
			graphics.drawLine(0, 0, 4, 0);
			graphics.drawLine(0, 0, 0, 4);
			graphics.drawLine(0, getHeight() - 1, 4, getHeight() - 1);
			graphics.drawLine(0, getHeight() - 1, 0, getHeight() - 5);
			graphics.drawLine(getWidth() - 1, 0, getWidth() - 5, 0);
			graphics.drawLine(getWidth() - 1, 0, getWidth() - 1, 4);
			graphics.drawLine(getWidth() - 1, getHeight() - 1, getWidth() - 5, getHeight() - 1);
			graphics.drawLine(getWidth() - 1, getHeight() - 1, getWidth() - 1, getHeight() - 5);
		}		
	}
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		if (getSystem().getParent() != null) {
			description.append(getSystem().getParent().getName()).append(" ");
		}
		description.append(getSystem().getFullName());

		return description.toString();
	}	
	
}
