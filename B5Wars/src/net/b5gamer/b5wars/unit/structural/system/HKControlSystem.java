/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class HKControlSystem extends PoweredSystem {

	private static final long serialVersionUID = -4424690304408542619L;

	private final int baseControlRating; // base number of flights that can be controlled
	private       int controlRating;     // number of flights that can be controlled
	
	/**
	 * @param damageBoxes amount of damage the HK control system can sustain before being destroyed
	 * @param armor       amount of armor protecting the HK control system
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the HK control system, may be null
	 * @param properties  additional properties 
	 */
	public HKControlSystem(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"),
				PropertyReader.getInteger(properties, "controlRating"));
	}
	
	/**
	 * @param damageBoxes   amount of damage the HK control system can sustain before being destroyed
	 * @param armor         amount of armor protecting the HK control system
	 * @param arc           arc for incoming fire, may be null 
	 * @param name          name of the HK control system, may be null
	 * @param power         amount of power required for the HK control system to function
	 * @param controlRating the number of flights that can be controlled
	 */
	public HKControlSystem(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int controlRating) {
		super(damageBoxes, armor, arc, name, power);
		
        if (!(controlRating > 0)) {
            throw new IllegalArgumentException("controlRating must be a positive number");
        } 

        this.baseControlRating = controlRating;
        this.controlRating     = controlRating;		
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("controlRating", String.valueOf(getBaseControlRating()));
		
		return properties;
	}
	
	public String getType() {
		return "HK Control System";
	}
	
	/**
	 * the base number of flights that can be controlled
	 * 
	 * @return the base number of flights that can be controlled
	 */
	public int getBaseControlRating() {
		return baseControlRating;
	}

	/**
	 * the number of flights that can be controlled
	 * 
	 * @return the number of flights that can be controlled
	 */
	public int getControlRating() {
		return controlRating;
	}

	/**
	 * the number of flights that can be controlled
	 * 
	 * @param controlRating the number of flights that can be controlled
	 */	
	public void setControlRating(int controlRating) {
		this.controlRating = controlRating;
	}

	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Armor ").append(getArmor()).append("/").append(getBaseArmor());
		status.append(", Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());
		status.append(", Control Rating ").append(getControlRating()).append("/").append(getBaseControlRating()).append(")");
		
		return status.toString();
	}	
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getControlRating());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getControlRating() < getBaseControlRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 830;
	}
	
}
