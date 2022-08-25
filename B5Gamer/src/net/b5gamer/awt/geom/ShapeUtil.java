/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ShapeUtil {
	
	private ShapeUtil() {
	}

	public static final List<SubPath> parse(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException("shape cannot be null");
        }	

        List<SubPath> paths        = new ArrayList<SubPath>();
		SubPath       path         = null;
		PathIterator  pathIterator = shape.getPathIterator(null);
		
		while (!pathIterator.isDone()) {
			float[] coordinates = new float[6];
			int segmentType = pathIterator.currentSegment(coordinates);
			
			switch (segmentType) {
			case PathIterator.SEG_MOVETO:
				if (path != null) {
					paths.add(path);
				}			
				path = new SubPath();
				path.add(new MoveToSegment(coordinates[0], coordinates[1]));
				break;
			case PathIterator.SEG_LINETO:
				path.add(new LineToSegment(coordinates[0], coordinates[1]));
				break;
			case PathIterator.SEG_QUADTO:
				path.add(new QuadToSegment(coordinates[0], coordinates[1], coordinates[2], coordinates[3]));
				break;
			case PathIterator.SEG_CUBICTO:
				path.add(new CurveToSegment(coordinates[0], coordinates[1], coordinates[2], coordinates[3],
						coordinates[4], coordinates[5]));
				break;
			case PathIterator.SEG_CLOSE:
				path.add(new ClosePathSegment());
				break;
			default:
				break;
			}
			
			pathIterator.next();
		}	
		
		if (path != null) {
			paths.add(path);
		}
		
		return paths;
	}

	public static final GeneralShape construct(final SubPath path) {
		GeneralShape shape = new GeneralShape();
		
		for (Iterator<Segment> iterator = path.iterator(); iterator.hasNext();) {
			iterator.next().appendTo(shape);
		}
    	
    	return shape;
	}
	
	public static final GeneralShape construct(final List<SubPath> paths) {
		GeneralShape shape = new GeneralShape();
		
		for (Iterator<SubPath> iterator = paths.iterator(); iterator.hasNext();) {
			for (Iterator<Segment> segmentIterator = iterator.next().iterator(); segmentIterator.hasNext();) {
				segmentIterator.next().appendTo(shape);
			}
		}
    	
    	return shape;
	}

	public static final String toSVG(final SubPath path) {
		StringBuilder svg = new StringBuilder();
	
		boolean first = true;
		for (Iterator<Segment> iterator = path.iterator(); iterator.hasNext();) {
			if (first) {
				first = false;
			} else {
				svg.append(" ");
			}
			
			svg.append(iterator.next().toSVG());
		}
    	
    	return svg.toString();
	}

	public static final String toSVG(final Shape shape) {
		StringBuilder svg = new StringBuilder();
	
		boolean first = true;
		for (Iterator<SubPath> iterator = parse(shape).iterator(); iterator.hasNext();) {
			if (first) {
				first = false;
			} else {
				svg.append(" ");
			}

			svg.append(toSVG(iterator.next()));
		}
    	
    	return svg.toString();
	}
	
}
