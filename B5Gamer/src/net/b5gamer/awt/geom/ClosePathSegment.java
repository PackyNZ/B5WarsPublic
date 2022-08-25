/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

public class ClosePathSegment implements Segment {

	private final float[] coordinates = {};
	
	public ClosePathSegment() {
	}

	public float[] getCoordinates() {
		return coordinates;
	}

	public void appendTo(GeneralShape shape) {
		shape.getGeneralPath().closePath();
		shape.setEnclosed(true);
	}
	
	public String toSVG() {
		return "Z";
	}
	
	@Override
	public String toString() {
		return "closePath()";
	}
	
}
