package net.b5gamer.map;

/**
 * This class is used to denote the type of boundary for the {@link net.b5gamer.map.Extents}
 * of a {@link net.b5gamer.map.Map}
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public enum BoundaryType {

	/**
	 * the boundary is fixed, and anything moving outside of it is considered disengaged from 
	 * the battle 
	 */
	FIXED,

	/**
	 * the boundary is floating and can move in any direction, however all combatants must remain 
	 * within the {@link net.b5gamer.map.Extents} in relation to each other
	 */
	FLOATING,

	/**
	 * the boundary can float in any direction, however the original center of the map must remain 
	 * within the current {@link net.b5gamer.map.Extents}
	 */
	ANCHORED;
	
}
