/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubPath {

	private List<Segment> segments = new ArrayList<Segment>();
	
	protected List<Segment> getSegments() {
		return segments;
	}
	
	public Iterator<Segment> iterator() {
		return getSegments().iterator();
	}
	
	public void add(Segment segment) {
        if (segment == null) {
            throw new IllegalArgumentException("segment cannot be null");
        }	
        if ((getSegments().size() == 0) && !(segment instanceof MoveToSegment)) {
            throw new IllegalArgumentException("First " + Segment.class.getSimpleName() + " must be a " + MoveToSegment.class.getSimpleName());
        }	

        getSegments().add(segment);        
	}
	
	public void remove(Segment segment) {
		getSegments().remove(segment);
	}
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();

		for (Iterator<Segment> iterator = segments.iterator(); iterator.hasNext();) {						
			description.append(iterator.next()).append(" ");
		}
		
		return description.toString();		
	}
		
}
