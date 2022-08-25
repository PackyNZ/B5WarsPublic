package net.b5gamer.map;

public class TerrainFeature extends Feature {

	// -!-

	protected final Terrain terrain;
	
	public TerrainFeature(Terrain terrain) {
        if (terrain == null) {
            throw new IllegalArgumentException("terrain cannot be null");
        } 
        
        this.terrain = terrain;
	}

	public Terrain getTerrain() {
		return terrain;
	}
	
}
