package net.b5gamer.map;

import java.io.Serializable;

/**
 * This class represents the extents of a {@link net.b5gamer.map.Map}
 *  
 * @author Alex Packwood (aka PackyNZ)
*/
public class Extents implements Serializable {

	private static final long serialVersionUID = 5493271718182973841L;
	
	private final int          width;        // width of the extents 
	private final int          height;       // height of the extents 
	private final String       units;        // units the extents are in
	private final BoundaryType boundaryType; // the boundary type
	
	/**
	 * @param width        width of the extents 
	 * @param height       height of the extents
	 * @param units        units the extents are in
	 * @param boundaryType the boundary type
	 */
	public Extents(final int width, final int height, final String units, 
			final BoundaryType boundaryType) {
        if (!(width > 0)) {
            throw new IllegalArgumentException("width must be a positive number");
        } 
        if (!(height > 0)) {
            throw new IllegalArgumentException("height must be a positive number");
        } 
        if ((units == null) || !(units.length() > 0)) {
            throw new IllegalArgumentException("units cannot be null or blank");
        } 
        if (boundaryType == null) {
            throw new IllegalArgumentException("boundaryType cannot be null");
        } 
		
		this.width        = width;
		this.height       = height;
		this.units        = units;
		this.boundaryType = boundaryType;
	}

	/**
	 * the width of the extents 
	 * 
	 * @return the width of the extents 
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * the height of the extents 
	 * 
	 * @return the height of the extents 
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * the units the extents are in
	 * 
	 * @return the units the extents are in
	 */	
	public String getUnits() {
		return units;
	}

	/**
	 * the boundary type
	 * 
	 * @return the boundary type
	 */	
	public BoundaryType getBoundaryType() {
		return boundaryType;
	}	
	
}
