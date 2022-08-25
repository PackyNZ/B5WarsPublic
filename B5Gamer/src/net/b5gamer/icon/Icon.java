/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.awt.geom.GeneralShape;

public class Icon implements Cloneable {

	public static final double OFFSET_X = 2;
	public static final double OFFSET_Y = 2;
	
	protected List<GeneralShape> outlines            = null;
	protected GeneralPath        outline             = null;
	protected List<GeneralPath>  annotationLocations = null;

	public Icon(final List<GeneralShape> outlines) {
		this(outlines, null);
	}

	public Icon(final List<GeneralShape> outlines, final List<GeneralPath> annotationLocations) {
		setOutlines(outlines);
		setAnnotationLocations(annotationLocations);
	}

	public List<GeneralShape> getOutlines() {
		return outlines;
	}

	protected void setOutlines(List<GeneralShape> outlines) {
        if (outlines == null) {
            throw new IllegalArgumentException("outlines cannot be null");
        }

        this.outlines = outlines;

        setOutline(outlines);
	}
	
	public GeneralPath getOutline() {
		return outline;
	}

	protected void setOutline(GeneralPath outline) {
		this.outline = outline;
	}

	protected void setOutline(List<GeneralShape> outlines) {
        GeneralPath outline = null;

        for (Iterator<GeneralShape> iterator = outlines.iterator(); iterator.hasNext();) {
			if (outline == null) {
				outline = new GeneralPath(iterator.next());
			} else {
				outline.append(iterator.next(), false);
			}
		}       

		setOutline(outline);		
	}
	
	public List<GeneralPath> getAnnotationLocations() {
		return annotationLocations;
	}

	protected void setAnnotationLocations(List<GeneralPath> annotationLocations) {
		this.annotationLocations = annotationLocations;
	}

	public Point2D getAnnotationPoint(int index) {
		if ((getAnnotationLocations() == null) || (index < 0) || (index >= getAnnotationLocations().size())) {
			return null;
		} else {
			return getAnnotationLocations().get(index).getCurrentPoint();
		}
	}
	
	public Rectangle getBounds() {
		return getOutline().getBounds();
	}
	
	public Rectangle2D getBounds2D() {
		return getOutline().getBounds2D();
	}
	
	public Dimension getSize() {
		return getBounds().getSize();
	}

	public void reposition() {
		Rectangle2D bounds = getBounds2D();
		
		transform(AffineTransform.getTranslateInstance(
				(bounds.getX() - OFFSET_X) * -1.0, 
				(bounds.getY() - OFFSET_Y) * -1.0));		
	}

	public void mirror() {
		transform(AffineTransform.getScaleInstance(-1.0, 1.0));
		reposition();		
	}

	public void rotate(double degrees) {
		transform(AffineTransform.getRotateInstance(degrees * Math.PI / 180.0));
		reposition();		
	}
	
	public synchronized void transform(AffineTransform transform) {
		if (getOutlines() != null) {
	        for (Iterator<GeneralShape> iterator = getOutlines().iterator(); iterator.hasNext();) {
	        	iterator.next().transform(transform);
			}
		}
		
		if (getOutline() != null) {
			getOutline().transform(transform);
		}
		
		if (getAnnotationLocations() != null) {
	        for (Iterator<GeneralPath> iterator = getAnnotationLocations().iterator(); iterator.hasNext();) {
	        	iterator.next().transform(transform);
			}
		}
	}

	@Override
	public Object clone() {
		List<GeneralShape> outlines = new ArrayList<GeneralShape>(getOutlines().size());
        for (Iterator<GeneralShape> iterator = getOutlines().iterator(); iterator.hasNext();) {
        	outlines.add((GeneralShape) iterator.next().clone());
		}

		List<GeneralPath> annotationLocations = null;
		if (getAnnotationLocations() != null) {
			annotationLocations = new ArrayList<GeneralPath>(getAnnotationLocations().size());
	        for (Iterator<GeneralPath> iterator = getAnnotationLocations().iterator(); iterator.hasNext();) {
	        	annotationLocations.add((GeneralPath) iterator.next().clone());
			}
		}

		return new Icon(outlines, annotationLocations);
	}
	
}
