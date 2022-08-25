/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

/**
 * Deployment is used to denote any restrictions to a units deployment
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public enum Deployment {
	
	/**
	 * may be purchased in any quantity or combination desired
	 */
	UNLIMITED ("Unlimited", 100),
	
	/**
	 * maximum of 33% of combat points may be spent on limited units, however a 
	 * minimum of one limited unit is permitted even if it's the only unit  
	 */
	LIMITED ("Limited", 33),
	
	/**
	 * maximum of 10% of combat points may be spent on restricted units, however
	 * a minimum of one restricted unit is permitted if accompanied by a military
	 * escort
	 */
	RESTRICTED ("Restricted", 10),
	
	/**
	 * special deployment limitations that are unique to a particular unit
	 */
	SPECIAL ("Special", 0);

	private final String name;       // name of the deployment
	private final int    percentage; // maximum percentage of combat points that may be spent on units
	                                 // of this deployment, a value of 0 denotes special restrictions

	/**
     * find and return a deployment with a given name
     * 
     * @param  name name of the deployment to return
     * @return      the deployment, or null if a deployment with the given name
     *              doesn't exist
     */
    public static final Deployment getDeployment(final String name) {
    	Deployment result = null;

    	for (Deployment deployment : Deployment.values()) {
            if (deployment.getName().equalsIgnoreCase(name)) {
                result = deployment;
                break;
            }                
    	} 

        return result;
    }
        
	/**
	 * @param name       name of the deployment
	 * @param percentage maximum percentage of combat points that may be spent on units of this 
	 *                   deployment, a value of 0 denotes special restrictions
	 */
	private Deployment(final String name, final int percentage) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
        if ((percentage < 0) || (percentage > 100)) {
            throw new IllegalArgumentException("percentage must be between 0 and 100 inclusive");
        } 
		
		this.name       = name;
		this.percentage = percentage;
	}
	
	/**
	 * the name of the deployment
	 * 
	 * @return the name of the deployment
	 */
	public final String getName() {
		return name;
	}

	/**
	 * the maximum percentage of combat points that may be spent on units of this deployment, 
	 * a value of 0 denotes special restrictions
	 * 
	 * @return the maximum percentage of combat points that may be spent on units of this 
	 *         deployment, a value of 0 denotes special restrictions
	 */
	public final int getPercentage() {
		return percentage;
	}
	
	@Override
	public final String toString() {
		StringBuilder description = new StringBuilder();
		description.append(getName()).append(" Deployment");

		if (this == Deployment.SPECIAL) {
			description.append(" Rules");				
		} else if ((getPercentage() > 0) && (getPercentage() < 100)) {
			description.append(" (").append(getPercentage()).append("%)");				
		}
		
		return description.toString();
	}
	
}
