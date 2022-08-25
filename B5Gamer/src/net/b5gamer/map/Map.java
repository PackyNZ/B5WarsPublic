package net.b5gamer.map;

import java.io.Serializable;

/**
 * This class represents the playing area in which all playing pieces are located
 *  
 * @author Alex Packwood (aka PackyNZ)
*/
public abstract class Map implements Serializable {

	private static final long serialVersionUID = -1461526852430781140L;
	
	private final Extents extents; // extents of the map
	
	/**
	 * create a map with the specified extents
	 * 
	 * @param extents extents of the map
	 */
	public Map(final Extents extents) {
        if (extents == null) {
            throw new IllegalArgumentException("extents cannot be null");
        } 

        this.extents = extents;
	}

	/**
	 * the extents of the map
	 * 
	 * @return the extents of the map
	 */
	public Extents getExtents() {
		return extents;
	}

}
