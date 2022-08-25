package net.b5gamer.b5wars.ui.map;

import net.b5gamer.map.Map;
import net.b5gamer.map.hex.HexMap;

/**
 * A MapRendererFactory is used to return a {@link net.b5gamer.b5wars.ui.map.MapRenderer} for a 
 * given {@link net.b5gamer.b5wars.map.Map} implementation
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class MapRendererFactory {

	/**
	 * return a {@link net.b5gamer.b5wars.ui.map.MapRenderer} for the given 
	 * {@link net.b5gamer.b5wars.map.Map} implementation
	 * 
	 * @param map map to return a {@link net.b5gamer.b5wars.ui.map.MapRenderer} for
	 * 
	 * @throws IllegalArgumentException map is of an unsupported type
	 */		
	public MapRenderer getMapRenderer(final Map map) throws IllegalArgumentException {
		if (map instanceof HexMap) {
			return new HexMapRenderer((HexMap) map);
		} else {
			throw new IllegalArgumentException(map.getClass().getName() + " is not supported");
		}		
	}

}
