/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.awt.geom.GeneralShape;

public class DamageableIcon extends Icon {

	private static final DamageBoxComparator COMPARATOR  = new DamageBoxComparator();
		
	private List<DamageBox> damageBoxes;
	
	public DamageableIcon(final List<GeneralShape> outlines, final List<DamageBox> damageBoxes) {
		this(outlines, null, damageBoxes);
	}
	
	public DamageableIcon(final List<GeneralShape> outlines, final List<GeneralPath> annotationLocations, 
			final List<DamageBox> damageBoxes) {
		super(outlines, annotationLocations);

		setDamageBoxes(damageBoxes);
	}
	
	@Override
	protected void setOutlines(List<GeneralShape> outlines) {
        this.outlines = outlines;

        if (outlines != null) {
        	setOutline(outlines);
        }
	}
	
	public List<DamageBox> getDamageBoxes() {
		return damageBoxes;
	}

	protected void setDamageBoxes(List<DamageBox> damageBoxes) {
        this.damageBoxes = (damageBoxes != null) ? damageBoxes : new ArrayList<DamageBox>(0);
        
    	orderDamageBoxes();
	}
	
	public void orderDamageBoxes() {
		Collections.sort(getDamageBoxes(), COMPARATOR);
	}	

	public int getTotalHits() {
		int hits = 0;
		
        for (Iterator<DamageBox> iterator = getDamageBoxes().iterator(); iterator.hasNext();) {
        	hits += iterator.next().getHits();
		}
        
        return hits;
	}
	
	@Override
	public Rectangle getBounds() {
		if (getOutline() != null) {
			return super.getBounds();
		} else {
			Rectangle bounds = null;
			
	        for (Iterator<DamageBox> iterator = getDamageBoxes().iterator(); iterator.hasNext();) {
				if (bounds == null) {
					bounds = new Rectangle(iterator.next().getShape().getBounds());
				} else {
					bounds.add(iterator.next().getShape().getBounds());
				}
			}			
			
			return (bounds == null ) ? new Rectangle((int) OFFSET_X, (int) OFFSET_Y, 0, 0) : bounds;
		}
	}

	@Override
	public Rectangle2D getBounds2D() {
		if (getOutline() != null) {
			return super.getBounds2D();
		} else {
			Rectangle2D bounds = null;
			
	        for (Iterator<DamageBox> iterator = getDamageBoxes().iterator(); iterator.hasNext();) {
				if (bounds == null) {
					bounds = (Rectangle2D) iterator.next().getShape().getBounds2D().clone();
				} else {
					bounds.add(iterator.next().getShape().getBounds2D());
				}
			}			
			
			return (bounds == null ) ? new Rectangle((int) OFFSET_X, (int) OFFSET_Y, 0, 0) : bounds;
		}
	}
	
	@Override
	public void mirror() {
		super.mirror();
		orderDamageBoxes();		
	}
	
	@Override
	public synchronized void transform(AffineTransform transform) {
		super.transform(transform);
		
        for (Iterator<DamageBox> iterator = getDamageBoxes().iterator(); iterator.hasNext();) {
        	iterator.next().getShape().transform(transform);
		}
	}

	@Override
	public Object clone() {
		List<GeneralShape> outlines = null;
		if (getOutlines() != null) {
			outlines = new ArrayList<GeneralShape>(getOutlines().size());
	        for (Iterator<GeneralShape> iterator = getOutlines().iterator(); iterator.hasNext();) {
				outlines.add((GeneralShape) iterator.next().clone());
			}
		}
		
		List<GeneralPath> annotationLocations = null;
		if (getAnnotationLocations() != null) {
			annotationLocations = new ArrayList<GeneralPath>(getAnnotationLocations().size());
	        for (Iterator<GeneralPath> iterator = getAnnotationLocations().iterator(); iterator.hasNext();) {
	        	annotationLocations.add((GeneralPath) iterator.next().clone());
			}
		}
		
		List<DamageBox> damageBoxes = new ArrayList<DamageBox>(getDamageBoxes().size());
        for (Iterator<DamageBox> iterator = getDamageBoxes().iterator(); iterator.hasNext();) {
			damageBoxes.add((DamageBox) iterator.next().clone());
		}
		
		return new DamageableIcon(outlines, annotationLocations, damageBoxes);
	}
	
}
