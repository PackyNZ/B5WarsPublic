/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * Resources that a {@link net.b5gamer.b5wars.unit.structural.system.ResourceProvidingSystem}
 * can provide, or a {@link net.b5gamer.b5wars.unit.structural.system.ResourceConsumingSystem}
 * can consume 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public enum Resource {
	
	/**
	 * the number of shields that can be maintained/powered by a 
	 * {@link net.b5gamer.b5wars.unit.structural.system.ShieldGenerator} 
	 */	
	SHIELDS ("Shields"),
	
	/**
	 * the amount of shield regeneration available from a  
	 * {@link net.b5gamer.b5wars.unit.structural.system.startrek.DeflectorShield} 
	 */	
	SHIELD_REGENERATION ("regeneration");
	
	private final String name; // name of the resource
        
	/**
	 * @param name name of the resource
	 */
	private Resource(final String name) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        } 
		
		this.name = name;
	}

	/**
	 * the name of the resource
	 * 
	 * @return the name of the resource
	 */
	public final String getName() {
		return name;
	}	
	
}
