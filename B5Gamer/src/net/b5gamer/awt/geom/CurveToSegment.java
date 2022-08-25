/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

public class CurveToSegment implements Segment {

	private final float[] coordinates = {0, 0, 0, 0, 0, 0};
	
	public CurveToSegment(final float x1, final float y1, final float x2, final float y2, final float x3, 
			final float y3) {
		coordinates[0] = x1;
		coordinates[1] = y1;
		coordinates[2] = x2;
		coordinates[3] = y2;
		coordinates[4] = x3;
		coordinates[5] = y3;
	}

	public float[] getCoordinates() {
		return coordinates;
	}

	public void appendTo(GeneralShape shape) {
		shape.getGeneralPath().curveTo(getCoordinates()[0], getCoordinates()[1], getCoordinates()[2], getCoordinates()[3], 
				getCoordinates()[4], getCoordinates()[5]);
	}
	
	public String toSVG() {
		StringBuilder path = new StringBuilder();
		
		path.append("C");
		for (int i = 0; i < getCoordinates().length; i++) {
			path.append(" ").append(getCoordinates()[i]);
		}
		
		return path.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		
		description.append("curveTo(");
		description.append(getCoordinates()[0]);
		description.append(",").append(getCoordinates()[1]);
		description.append(",").append(getCoordinates()[2]);
		description.append(",").append(getCoordinates()[3]);
		description.append(",").append(getCoordinates()[4]);
		description.append(",").append(getCoordinates()[5]);
		description.append(")");
				
		return description.toString();
	}
	
}
