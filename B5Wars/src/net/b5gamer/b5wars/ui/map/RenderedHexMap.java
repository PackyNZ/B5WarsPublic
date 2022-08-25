package net.b5gamer.b5wars.ui.map;

import net.b5gamer.map.Feature;
import net.b5gamer.map.hex.HexMap;
import net.b5gamer.map.hex.HexMetrics;

public class RenderedHexMap extends RenderedMap {
	
	protected final HexMetrics hexMetrics;

	public RenderedHexMap(HexMap map, double scale, int margin, HexMetrics hexMetrics) {
		super(map, scale, margin);
		
        if (hexMetrics == null) {
            throw new IllegalArgumentException("hexMetrics cannot be null");
        } 	
        
		this.hexMetrics = hexMetrics;
	}

	public HexMap getMap() {
		return (HexMap) super.getMap();
	}
	
	public HexMetrics getHexMetrics() {
		return hexMetrics;
	}

	public Feature[] getFeaturesAtPoint(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
