/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GeneralShape implements Shape, Cloneable {

	private final GeneralPath generalPath;
	private       boolean     enclosed    = false;
	
	public GeneralShape() {	
		this.generalPath = new GeneralPath();
	}

	public GeneralShape(GeneralPath generalPath) {
        if (generalPath == null) {
            throw new IllegalArgumentException("generalPath cannot be null");
        }	
        
        this.generalPath = generalPath;
	}
	
	public GeneralPath getGeneralPath() {
		return generalPath;
	}

	public boolean isEnclosed() {
		return enclosed;
	}

	protected void setEnclosed(boolean enclosed) {
		this.enclosed = enclosed;
	}

	public boolean contains(Point2D p) {
		return getGeneralPath().contains(p);
	}

	public boolean contains(Rectangle2D r) {
		return getGeneralPath().contains(r);
	}

	public boolean contains(double x, double y) {
		return getGeneralPath().contains(x, y);
	}

	public boolean contains(double x, double y, double w, double h) {
		return getGeneralPath().contains(x, y, w, h);
	}

	public Rectangle getBounds() {
		return getGeneralPath().getBounds();
	}

	public Rectangle2D getBounds2D() {
		return getGeneralPath().getBounds2D();
	}

	public PathIterator getPathIterator(AffineTransform at) {
		return getGeneralPath().getPathIterator(at);
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return getGeneralPath().getPathIterator(at, flatness);
	}

	public boolean intersects(Rectangle2D r) {
		return getGeneralPath().intersects(r);
	}

	public boolean intersects(double x, double y, double w, double h) {
		return getGeneralPath().intersects(x, y, w, h);
	} 
	
	public void transform(AffineTransform at) {
		getGeneralPath().transform(at);
	}

	@Override
	public Object clone() {
		GeneralShape shape = new GeneralShape((GeneralPath) getGeneralPath().clone());
		shape.setEnclosed(isEnclosed());
		
		return shape;
	}
	
}
