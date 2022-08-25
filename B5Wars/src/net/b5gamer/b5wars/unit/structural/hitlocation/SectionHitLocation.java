/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import net.b5gamer.util.Properties;

/**
 * A SectionHitLocation indicates that a specific section is hit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SectionHitLocation extends HitLocation {

	private static final long serialVersionUID = 288578539833229257L;

	private final String sectionName; // name of the section to hit

	/**
	 * @param from       from value for the range used to determine whether this location is hit
	 * @param to         to value for the range used to determine whether this location is hit
	 * @param properties additional properties 
	 */
	public SectionHitLocation(final int from, final int to, final Properties properties) {
		this(from, to, properties.getProperty("sectionName"));
	}
	
	/**
	 * @param from        from value for the range used to determine whether this location is hit
	 * @param to          to value for the range used to determine whether this location is hit
	 * @param sectionName name of the section to hit
	 */
	public SectionHitLocation(final int from, final int to, final String sectionName) {
		super(from, to);
		
        if ((sectionName == null) || !(sectionName.length() > 0)) {
            throw new IllegalArgumentException("sectionName cannot be null or blank");
        } 
		
		this.sectionName = sectionName;
	}
	
	@Override
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("sectionName", getSectionName());
		
		return properties;
	}
	
	/**
	 * the name of the section to hit
	 * 
	 * @return the name of the section to hit
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	public String getLocationDescription() {
		StringBuilder description = new StringBuilder();
		
		description.append(getSectionName()).append(" Hit");
		
		return description.toString();
	}
	
}
