/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

/**
 * Availability is used to denote whether a unit is a base model or variant and 
 * the units availability for use 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public enum Availability {
	
	/**
	 * base model, may be used in unlimited quantities
	 */	
	BASE ("Base"),
	
	/**
	 * common variant, may be used in unlimited quantities
	 */	
	COMMON ("Common"),
	
	/**
	 * uncommon variant, limited to one in three for that particular model
	 */	
	UNCOMMON ("Uncommon"),
	
	/**
	 * rare variant, limited to one in nine for that particular model
	 */	
	RARE ("Rare"),
	
	/**
	 * specialty variant, counted as rare and under special restrictions 
	 * unique to a particular unit
	 */	
	SPECIALTY ("Specialty"),
	
	/**
	 * unique variant, counted as rare and limited to a single unit
	 */	
	UNIQUE ("Unique");
	
	private final String name; // name of the availability

	/**
     * find and return an availability with a given name
     * 
     * @param  name name of the availability to return
     * @return      the availability, or null if an availability with the given 
     *              name doesn't exist
     */
    public static final Availability getAvailability(final String name) {
    	Availability result = null;

    	for (Availability availability : Availability.values()) {
            if (availability.getName().equalsIgnoreCase(name)) {
                result = availability;
                break;
            }                
    	} 

        return result;
    }
        
	/**
	 * @param name name of the availability
	 */
	private Availability(final String name) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
		
		this.name = name;
	}
	
	/**
	 * the name of the availability
	 * 
	 * @return the name of the availability
	 */
	public final String getName() {
		return name;
	}	

	@Override
	public final String toString() {
		StringBuilder description = new StringBuilder();

		if (this != Availability.BASE) {
			description.append(getName()).append(" Variant");
		}
		
		return description.toString();
	}
	
}
