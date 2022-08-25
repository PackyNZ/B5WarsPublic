package net.b5gamer.b5wars.io.unit;

import java.util.ArrayList;
import java.util.List;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * Bean used for reading sections from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SectionBean {

	private String     className        = null;
	private String     name             = null;
	private Arc        arc              = null;
	private String     hitLocationChart = null;
	private Properties properties       = new Properties(); 
	private List<SystemBean>  systems  = new ArrayList<SystemBean>(0);
	private List<SectionBean> sections = new ArrayList<SectionBean>(0);
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Arc getArc() {
		return arc;
	}
	
	public void setArc(String arc) {
		if ((arc != null) && (arc.length() > 0)) {
			Arc theArc = Arc.getArc(arc);

			if (theArc != null) {
				this.arc = theArc;
			} else {
				this.arc = Arc.createArc(arc);
			}
		} else {
			this.arc = null;
		}
	}
	
	public String getHitLocationChart() {
		return hitLocationChart;
	}
	
	public void setHitLocationChart(String hitLocationChart) {
		this.hitLocationChart = hitLocationChart;
	}
	
	public void setDisplayStyle(String displayStyle) {
		getProperties().setProperty("displayStyle", displayStyle);
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public void addProperty(String name, String value) {
		properties.setProperty(name, value);
	}
		
	public List<SystemBean> getSystems() {
		return systems;
	}
	
	public void addSystem(SystemBean system) {
		systems.add(system);
	}	
	
	public List<SectionBean> getSections() {
		return sections;
	}
	
	public void addSection(SectionBean section) {
		sections.add(section);
	}	
	
}
