/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.icon.IconPosition;
import net.b5gamer.map.Arc;

/**
 * This class provides a base implementation of a 
 * {@link net.b5gamer.b5wars.unit.structural.system.PowerRequiringSystem}
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class PoweredSystem extends System implements PowerRequiringSystem {

	private static final long serialVersionUID = -7195202180741776297L;

	private int          basePowerRequirement; // base amount of power required for the system to function
	private int          powerRequirement;     // amount of power required for the system to function
	private boolean      activated  = true;    // whether the system is activated
	private int          extraPower = 0;       // amount of extra power allocated to the system
	private IconPosition powerIconPosition = new IconPosition(); // position of the power icon
	
	/**
	 * @param damageBoxes amount of damage the system can sustain before being destroyed
	 * @param armor       amount of armor protecting the system
	 * @param arc         arc for incoming fire or outgoing effects, may be null
	 * @param name        name of the system, may be null
	 * @param power       amount of power required for the system to function
	 */
	protected PoweredSystem(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power) {
		super(damageBoxes, armor, arc, name);
		
        if (power < 0) {
            throw new IllegalArgumentException("power cannot be a negative number");
        } 

        this.basePowerRequirement = power;
        this.powerRequirement     = power;
	}
	
	/**
	 * the base amount of power required for the system to function
	 * 
	 * @return the base amount of power required for the system to function
	 */	
	public int getBasePowerRequirement() {
		return basePowerRequirement;
	}
	
	/**
	 * the amount of power required for the system to function
	 * 
	 * @return the amount of power required for the system to function
	 */	
	public int getPowerRequirement() {
		return powerRequirement;
	}

	/**
	 * the amount of power required for the system to function
	 * 
	 * @param powerRequirement the amount of power required for the system to function
	 */
	protected void setPowerRequirement(final int powerRequirement) {
		this.powerRequirement = powerRequirement < 0 ? 0 : powerRequirement;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(final boolean activated) {
		this.activated = activated;
	}
	
	public int getExtraPowerGrouping() {
		return 0;
	}
	
	public int getExtraPower() {
		return extraPower;
	}

	public void setExtraPower(final int extraPower) {
		this.extraPower = extraPower < 0 ? 0 : extraPower;
	}	

	public IconPosition getPowerIconPosition() {
		return powerIconPosition;
	}

	public void setPowerIconPosition(IconPosition powerIconPosition) {
		this.powerIconPosition = (powerIconPosition != null) ? powerIconPosition : new IconPosition();
	}
	
}
