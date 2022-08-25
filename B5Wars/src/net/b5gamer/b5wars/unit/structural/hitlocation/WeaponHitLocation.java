/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import net.b5gamer.util.Properties;

/**
 * A WeaponHitLocation indicates that a specific type of weapon is hit, with optional weapon 
 * number and/or section containing the weapon
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class WeaponHitLocation extends HitLocation {

	private static final long serialVersionUID = -8391651540615248106L;

	private final String weaponType;   // type of weapon to hit
	private final String weaponNumber; // number of the weapon to hit, may be null for not applicable
	private final String sectionName;  // name of the section the weapon must be located in, may be null for not applicable
	
	/**
	 * @param from       from value for the range used to determine whether this location is hit
	 * @param to         to value for the range used to determine whether this location is hit
	 * @param properties additional properties 
	 */
	public WeaponHitLocation(final int from, final int to, final Properties properties) {
		this(from, to, properties.getProperty("weaponType"), properties.getProperty("weaponNumber"),
				properties.getProperty("sectionName"));
	}

	/**
	 * @param from         from value for the range used to determine whether this location is hit
	 * @param to           to value for the range used to determine whether this location is hit
	 * @param weaponType   type of weapon to hit
	 * @param weaponNumber number of the weapon to hit, may be null for not applicable
	 * @param sectionName  name of the section the weapon must be located in, may be null for not applicable
	 */
	public WeaponHitLocation(final int from, final int to, final String weaponType, final String weaponNumber,
			final String sectionName) {
		super(from, to);
		
        if ((weaponType == null) || !(weaponType.length() > 0)) {
            throw new IllegalArgumentException("weaponType cannot be null or blank");
        } 

        this.weaponType   = weaponType;
		this.weaponNumber = ((weaponNumber == null) || !(weaponNumber.trim().length() > 0)) ? null : weaponNumber;
		this.sectionName  = ((sectionName == null) || !(sectionName.trim().length() > 0)) ? null : sectionName;
	}
	
	@Override
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("weaponType", getWeaponType());
		if (getWeaponNumber() != null) {
			properties.setProperty("weaponNumber", getWeaponNumber());			
		}
		if (getSectionName() != null) {
			properties.setProperty("sectionName", getSectionName());			
		}

		return properties;
	}
	
	/**
	 * the type of weapon to hit
	 * 
	 * @return the type of weapon to hit
	 */
	public String getWeaponType() {
		return weaponType;
	}
	
	/**
	 * number of the weapon to hit, will be null for not applicable
	 * 
	 * @return number of the weapon to hit, will be null for not applicable
	 */
	public String getWeaponNumber() {
		return weaponNumber;
	}	
	
	/**
	 * the name of the section the weapon must be located in, will be null for not applicable
	 * 
	 * @return the name of the section the weapon must be located in, will be null for not applicable
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	public String getLocationDescription() {
		StringBuilder description = new StringBuilder();
		
//		if (getSectionName() != null) {
//			description.append(getSectionName()).append(" ");
//		}
		description.append(getWeaponType());
		if (getWeaponNumber() != null) {
			description.append(" #").append(getWeaponNumber());
		}		
		
		return description.toString();
	}
	
}
