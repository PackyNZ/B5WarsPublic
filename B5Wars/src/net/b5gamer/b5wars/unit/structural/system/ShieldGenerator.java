/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * A shield generator is used to supply the very high power requirements of certain types of
 * shield emitters
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class ShieldGenerator extends PoweredSystem implements ResourceProvidingSystem {

	private static final long serialVersionUID = -7884570835983724258L;

	private final int baseShields;      // base number of shields that the shield generator can power
	private       int shields;          // number of shields that the shield generator can power
	private       int availableShields; // amount of shields the generator can still power 
	
	/**
	 * @param damageBoxes amount of damage the shield generator can sustain before being destroyed
	 * @param armor       amount of armor protecting the shield generator
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the shield generator, may be null
	 * @param properties  additional properties 
	 */
	public ShieldGenerator(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"),
				PropertyReader.getInteger(properties, "shields"));
	}
	
	/**
	 * @param damageBoxes amount of damage the shield generator can sustain before being destroyed
	 * @param armor       amount of armor protecting the shield generator
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the shield generator, may be null
	 * @param power       amount of power required for the shield generator to function
	 * @param shields     the number of shields that the shield generator can power
	 */	
	public ShieldGenerator(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int shields) {
		super(damageBoxes, armor, arc, name, power);
		
        if (!(shields > 0)) {
            throw new IllegalArgumentException("shields must be a positive number");
        } 		
		
		this.baseShields      = shields;
		this.shields          = shields;
		this.availableShields = shields;
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("shields", String.valueOf(getBaseShields()));
		
		return properties;
	}
	
	public String getType() {
		return "Shield Generator";
	}
	
	/**
	 * the base number of shields that the shield generator can power
	 * 
	 * @return the base number of shields that the shield generator can power
	 */
	public int getBaseShields() {
		return baseShields;
	}

	/**
	 * the number of shields that the shield generator can power
	 * 
	 * @return the number of shields that the shield generator can power
	 */
	public int getShields() {
		return shields;
	}

	/**
	 * the number of shields that the shield generator can power
	 * 
	 * @param shields the number of shields that the shield generator can power
	 */
	protected void setShields(int shields) {
		this.shields = shields;
	}

	/**
	 * the amount of shields the generator can still power 
	 * 
	 * @return the amount of shields the generator can still power 
	 */
	public int getAvailableShields() {
		return availableShields;
	}

	/**
	 * the amount of shields the generator can still power 
	 * 
	 * @param availableShields the amount of shields the generator can still power 
	 */
	protected void setAvailableShields(final int availableShields) {
		this.availableShields = availableShields < 0 ? 0 : availableShields;
	}

	public int getExtraPowerGrouping() {
		return getPowerRequirement();
	}
	
	public void setExtraPower(final int extraPower) {
		int previousExtraPower = getExtraPower();
		super.setExtraPower(extraPower);
		
		setAvailableShields((getAvailableShields() - (previousExtraPower / getExtraPowerGrouping())) + 
				(getExtraPower() / getExtraPowerGrouping()));
	}
	
	public int getOrder() {
		return 10;
	}

	public Resource getResourceProvided() {
		return Resource.SHIELDS;
	}
	
	public int getAvailableResource() {
		return getAvailableShields();
	}
	
	public void setAvailableResource(final int availableResource) {
		if (availableResource < 0) {
			throw new IllegalArgumentException("availableResource cannot be a negative number");
		}
		
		setAvailableShields(availableResource);		
	}
	
	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 15) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 21) {
			// power deficiency: +2 power required
			Logger.info("    - power deficiency: +2 power required");
			setPowerRequirement(getPowerRequirement() + 2);
		} else if (roll <= 26) {
			// control loss: -1 shield available
			Logger.info("    - control loss: -1 shield available");
			setShields(getShields() - 1);
		} else {
			// both above effects suffered
			Logger.info("    - power deficiency: +2 power required");
			setPowerRequirement(getPowerRequirement() + 2);
			Logger.info("    - control loss: -1 shield available");
			setShields(getShields() - 1);
		}
	}	
	
	protected void adjustSystem() {
		setAvailableShields(getShields() + (getExtraPower() / getExtraPowerGrouping()));		
	}

	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Armor ").append(getArmor()).append("/").append(getBaseArmor());
		status.append(", Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());
		status.append(", Shields ").append(getShields()).append("/").append(getBaseShields()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getShields());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getShields() < getBaseShields()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}	
	
	@Override
	public int getRecognitionOrder() {
		return 900;
	}
	
}
