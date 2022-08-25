package net.b5gamer.map.hex;

import net.b5gamer.map.BoundaryType;
import net.b5gamer.map.Extents;
import net.b5gamer.map.Map;

/**
 * This class represents a two dimensional playing surface on which positioning and movement is 
 * regimented using a hexagonal grid 
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class HexMap extends Map {

	private static final long serialVersionUID = 2734773364042834981L;
	
	public static final String EXTENT_UNITS = "Hexes"; // units the extents are in for this map implementation
		
	/**
	 * create a fixed boundary hex map with the default size of 42x30 hexes
	 */		
	public HexMap() {
		this(42, 30);
	}
	
	/**
	 * create a fixed boundary hex map of the specified size in hexes
	 * 
	 * @param width  width of the map in hexes
	 * @param height height of the map in hexes
	 */	
	public HexMap(final int width, final int height) {
		this(width, height, BoundaryType.FIXED);		
	}
	
	/**
	 * create a hex map with the specified extents
	 * 
	 * @param width        width of the map in hexes
	 * @param height       height of the map in hexes
	 * @param boundaryType the boundary type
	 */	
	public HexMap(final int width, final int height, final BoundaryType boundaryType) {
		this(new Extents(width, height, EXTENT_UNITS, boundaryType));		
	}	
	
	/**
	 * create a hex map with the specified extents
	 * 
	 * @param extents extents of the map
	 */	
	public HexMap(final Extents extents) {
		super(extents);
	}
		
	
//	public List<Feature> getFeatures() {
//		
//	}
//	
//	public List<Feature> getFeatures(final Geometry geometry) {
//		
//	}
	
}
