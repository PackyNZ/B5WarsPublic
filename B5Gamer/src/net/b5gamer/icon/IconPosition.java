/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class IconPosition implements Serializable {

	private static final long serialVersionUID = -5244027478365355238L;

	private double  x          = 0.0;
	private double  y          = 0.0;
	private boolean mirror     = false;
	private double  rotation   = 0.0;
	private int     index      = 1;
	private String  definition = null;
	
	public IconPosition() {
	}
	
	public IconPosition(double x, double y, boolean mirror, double rotation, int index, String definition) {
		this.x          = x;
		this.y          = y;
		this.mirror     = mirror;
		this.rotation   = rotation;
		this.index      = index;
		this.definition = definition;
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Point2D getLocation() {
		return new Point2D.Double(getX(), getY());
	}
	
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setLocation(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public void setLocation(Point p) {
		setX(p.getX());
		setY(p.getY());
	}
	
	public boolean isMirror() {
		return mirror;
	}
	
	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation % 360.0;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getDefinition() {
		return definition;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
}
