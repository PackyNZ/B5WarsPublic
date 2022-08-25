package net.b5gamer.map;

public abstract class Feature {

	// -!-
	
	protected Geometry geometry = null;
	
	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
		
}
