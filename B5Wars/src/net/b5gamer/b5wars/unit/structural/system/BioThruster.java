/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;
import java.awt.Font;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class BioThruster extends System implements DirectionalSystem {

	private static final long serialVersionUID = -9192477368743387217L;

	public static final Font ANNOTATION_FONT = new Font("Arial", Font.PLAIN, 6);
	
	private int thrustRating; // maximum thrust that can be channelled through this thruster per turn
	
	/**
	 * @param damageBoxes amount of damage the bio-thruster can sustain before being destroyed
	 * @param armor       amount of armor protecting the bio-thruster
	 * @param arc         arc for incoming fire and direction of thrust expelled from this bio-thruster
	 * @param name        name of the bio-thruster, may be null
	 * @param properties  additional properties 
	 */
	public BioThruster(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "thrustRating"));
	}

	/**
	 * @param damageBoxes  amount of damage the bio-thruster can sustain before being destroyed
	 * @param armor        amount of armor protecting the bio-thruster
	 * @param arc          arc for incoming fire and direction of thrust expelled from this bio-thruster
	 * @param name         name of the bio-thruster, may be null
	 * @param thrustRating maximum thrust that can be channelled through this bio-thruster per turn
	 */
	public BioThruster(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int thrustRating) {
		super(damageBoxes, armor, arc, name);
		
        if (!(thrustRating > 0)) {
            throw new IllegalArgumentException("thrustRating must be a positive number");
        } 

        this.thrustRating = thrustRating;
    }
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("thrustRating", String.valueOf(getBaseThrustRating()));
		
		return properties;
	}
	
	public String getType() {
		return "Bio-Thruster";
	}
	
	/**
	 * the base maximum thrust that can be channelled through this thruster per turn
	 * 
	 * @return the base maximum thrust that can be channelled through this thruster per turn
	 */
	public int getBaseThrustRating() {
		return thrustRating;
	}
	
	/**
	 * the maximum thrust that can be channelled through this thruster per turn
	 * 
	 * @return the maximum thrust that can be channelled through this thruster per turn
	 */
	public int getThrustRating() {
		return thrustRating;
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
		status.append(", Thrust Rating ").append(getThrustRating()).append("/").append(getBaseThrustRating()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getThrustRating());
	}
	
	@Override
	public Font getAnnotationFont(int index) {
		return ANNOTATION_FONT;
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getThrustRating() < getBaseThrustRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}	
	
	@Override
	public int getRecognitionOrder() {
		return 110;
	}
	
}
