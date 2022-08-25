package net.b5gamer.b5wars.io.unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean used for reading hit location charts from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class HitLocationChartBean {

	private String name = null;
	private List<HitLocationBean> hitLocations = new ArrayList<HitLocationBean>(0);
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<HitLocationBean> getHitLocations() {
		return hitLocations;
	}
	
	public void addHitLocation(HitLocationBean hitLocation) {
		hitLocations.add(hitLocation);
	}
		
}
