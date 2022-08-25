/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import net.b5gamer.util.Properties;

/**
 * A SystemHitLocation indicates that a specific type of system or general weapon is hit, with
 * optional system name and/or section containing the system
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SystemHitLocation extends HitLocation {

	private static final long serialVersionUID = -2405914338417583139L;

	private final String systemType;  // type of system to hit
	private final String systemName;  // name of the system to hit, may be null for not applicable
	private final String sectionName; // name of the section the system must be located in, may be null for not applicable

	/**
	 * @param from       from value for the range used to determine whether this location is hit
	 * @param to         to value for the range used to determine whether this location is hit
	 * @param properties additional properties 
	 */
	public SystemHitLocation(final int from, final int to, final Properties properties) {
		this(from, to, properties.getProperty("systemType"), properties.getProperty("systemName"),
				properties.getProperty("sectionName"));
	}

	/**
	 * @param from        from value for the range used to determine whether this location is hit
	 * @param to          to value for the range used to determine whether this location is hit
	 * @param systemType  type of system to hit
	 * @param systemName  name of the system to hit, may be null for not applicable
	 * @param sectionName name of the section the system must be located in, may be null for not applicable
	 */
	public SystemHitLocation(final int from, final int to, String systemType, final String systemName,
			final String sectionName) {
		super(from, to);

        if ((systemType == null) || !(systemType.length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 
		
		this.systemType  = systemType;
		this.systemName  = ((systemName == null) || !(systemName.trim().length() > 0)) ? null : systemName;
		this.sectionName = ((sectionName == null) || !(sectionName.trim().length() > 0)) ? null : sectionName;
	}
	
	@Override
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("systemType", getSystemType());
		if (getSystemName() != null) {
			properties.setProperty("systemName", getSystemName());			
		}
		if (getSectionName() != null) {
			properties.setProperty("sectionName", getSectionName());			
		}

		return properties;
	}
	
	/**
	 * the type of system to hit
	 * 
	 * @return the type of system to hit
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * the name of the system to hit, will be null for not applicable
	 * 
	 * @return the name of the system to hit, will be null for not applicable
	 */
	public String getSystemName() {
		return systemName;
	}
	
	/**
	 * the name of the section the system must be located in, will be null for not applicable
	 * 
	 * @return the name of the section the system must be located in, will be null for not applicable
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	public String getLocationDescription() {
		StringBuilder description = new StringBuilder();
		
//		if (getSectionName() != null) {
//			description.append(getSectionName()).append(" ");
//		}
		description.append(getSystemType());
		if (getSystemName() != null) {
			description.append(" ").append(getSystemName());
		}		
		
		return description.toString();
	}
	
}
