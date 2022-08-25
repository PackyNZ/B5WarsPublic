/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

public class MoveToSegment implements Segment {

	private final float[] coordinates = {0, 0};
	
	public MoveToSegment(final float x, final float y) {
		coordinates[0] = x;
		coordinates[1] = y;
	}

	public float[] getCoordinates() {
		return coordinates;
	}

	public void appendTo(GeneralShape shape) {
		shape.getGeneralPath().moveTo(getCoordinates()[0], getCoordinates()[1]);
	}
	
	public String toSVG() {
		StringBuilder path = new StringBuilder();
		
		path.append("M");
		for (int i = 0; i < getCoordinates().length; i++) {
			path.append(" ").append(getCoordinates()[i]);
		}
		
		return path.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		
		description.append("moveTo(");
		description.append(getCoordinates()[0]);
		description.append(",").append(getCoordinates()[1]);
		description.append(")");
				
		return description.toString();
	}
	
}