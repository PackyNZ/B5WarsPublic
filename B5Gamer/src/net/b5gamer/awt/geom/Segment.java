/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.awt.geom;

public interface Segment {

	public float[] getCoordinates();
	
	public void appendTo(GeneralShape shape);
	
	public String toSVG();
	
}
