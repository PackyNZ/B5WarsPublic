/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;
import java.util.List;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class EnergyDiffuser extends System implements RepairSystem {

	private static final long serialVersionUID = -7393595670931604906L;

	private final int baseDischargeRating; // base rate at which the diffuser can discharge stored energy/damage
	private       int dischargeRating;     // rate at which the diffuser can discharge stored energy/damage
	
	/**
	 * @param damageBoxes amount of damage the energy diffuser can sustain before being destroyed
	 * @param armor       amount of armor protecting the energy diffuser
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the energy diffuser, may be null
	 * @param properties  additional properties 
	 */
	public EnergyDiffuser(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "dischargeRating"));
	}

	/**
	 * @param damageBoxes     amount of damage the energy diffuser can sustain before being destroyed
	 * @param armor           amount of armor protecting the energy diffuser
	 * @param arc             arc for incoming fire, may be null 
	 * @param name            name of the energy diffuser, may be null
	 * @param dischargeRating rate at which the diffuser can discharge stored energy/damage
	 */
	public EnergyDiffuser(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int dischargeRating) {
		super(damageBoxes, armor, arc, name);
		
        if (!(dischargeRating > 0)) {
            throw new IllegalArgumentException("dischargeRating must be a positive number");
        } 

        this.baseDischargeRating = dischargeRating;		
        this.dischargeRating     = dischargeRating;		
	}
		
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("dischargeRating", String.valueOf(getBaseDischargeRating()));
		
		return properties;
	}
	
	public String getType() {
		return "Energy Diffuser";
	}
		
	/**
	 * the base rate at which the diffuser can discharge stored energy/damage
	 * 
	 * @return the base rate at which the diffuser can discharge stored energy/damage
	 */
	public int getBaseDischargeRating() {
		return baseDischargeRating;
	}
	
	/**
	 * the rate at which the diffuser can discharge stored energy/damage
	 * 
	 * @return the rate at which the diffuser can discharge stored energy/damage
	 */
	public int getDischargeRating() {
		return dischargeRating;
	}
	
	/**
	 * the rate at which the diffuser can discharge stored energy/damage
	 * 
	 * @param dischargeRating the rate at which the diffuser can discharge stored energy/damage
	 */
	public void setDischargeRating(final int dischargeRating) {
		this.dischargeRating = dischargeRating < 0 ? 0 : dischargeRating;
	}

	public List<System> getEligibleSystems(){
		return getParent().getSystemsOfClass(Tendril.class);
	}
	
	public int getRepairCapacity() {
		// TODO
		return 0;
	}
	
	public int repair(System system, int amount) {
		// TODO
		return 0;
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
	}

	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Armor ").append(getArmor()).append("/").append(getBaseArmor());
		status.append(", Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());
		status.append(", Rating ").append(getDischargeRating()).append("/").append(getBaseDischargeRating()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getDischargeRating());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getDischargeRating() < getBaseDischargeRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 820;
	}
	
}
