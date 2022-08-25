package net.b5gamer.b5wars.map;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.map.Feature;

public class UnitFeature extends Feature {

	protected final Unit unit;
	
	public UnitFeature(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        } 
        
        this.unit = unit;
	}

	public Unit getUnit() {
		return unit;
	}
	
}
