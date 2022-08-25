package net.b5gamer.map.vector;

import net.b5gamer.map.BoundaryType;
import net.b5gamer.map.Extents;
import net.b5gamer.map.Map;

/**
 * This class represents a two dimensional playing surface on which positioning and movement is 
 * non-regimented
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class VectorMap extends Map {
	
	private static final long serialVersionUID = -2345967613692659953L;

	public static final String EXTENT_UNITS = "Pixels"; // units the extents are in for this map implementation
	
	/**
	 * create a fixed boundary free form map with the default size of 1024x768 pixels
	 */		
	public VectorMap() {
		this(1024, 768);
	}
	
	/**
	 * create a fixed boundary free form map of the specified size in pixels
	 * 
	 * @param width  width of the map in pixels
	 * @param height height of the map in pixels
	 */	
	public VectorMap(final int width, final int height) {
		this(width, height, BoundaryType.FIXED);		
	}
	
	/**
	 * create a free form map with the specified extents
	 * 
	 * @param width        width of the map in pixels
	 * @param height       height of the map in pixels
	 * @param boundaryType the boundary type
	 */	
	public VectorMap(final int width, final int height, final BoundaryType boundaryType) {
		this(new Extents(width, height, EXTENT_UNITS, boundaryType));		
	}	
	
	/**
	 * create a free form map with the specified extents
	 * 
	 * @param extents extents of the map
	 */	
	public VectorMap(final Extents extents) {
		super(extents);
	}
	
}
