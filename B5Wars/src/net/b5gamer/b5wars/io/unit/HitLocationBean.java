package net.b5gamer.b5wars.io.unit;

import java.util.ArrayList;
import java.util.List;

import net.b5gamer.util.Properties;

/**
 * Bean used for reading hit locations from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class HitLocationBean {
	
	private String     className  = null;
	private int        from       = 0; 
	private int        to         = 0;   
	private Properties properties = new Properties(); 
	private List<HitLocationBean> hitLocations = new ArrayList<HitLocationBean>(0);
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public void addProperty(String name, String value) {
		properties.setProperty(name, value);
	}
	
	public List<HitLocationBean> getHitLocations() {
		return hitLocations;
	}
	
	public void addHitLocation(HitLocationBean hitLocation) {
		hitLocations.add(hitLocation);
	}
	
}
