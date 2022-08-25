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
public class AegisSensorPod extends Weapon {

	private static final long serialVersionUID = -6796984458713679397L;

	private final int baseRating; //
	private       int rating;     //

	public AegisSensorPod(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(Integer.parseInt(number), PropertyReader.getInteger(properties, "rating"));
	}
	
	public AegisSensorPod(final int number, final int rating) {
		super(5, 0, null, String.valueOf(number), 2);
		
        if (!(rating > 0)) {
            throw new IllegalArgumentException("rating must be a positive number");
        } 

        this.baseRating = rating;		
        this.rating     = rating;		
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("rating", String.valueOf(getBaseRating()));
		
		return properties;
	}
	
	public String getType() {
		return "Aegis Sensor Pod";
	}
	
	@Override
	public void setArc(final Arc arc) {
		this.arc = arc;
	}
	
	public int getBaseRating() {
		return baseRating;
	}

	/**
	 * the 
	 * 
	 * @return the 
	 */
	public int getRating() {
		return rating;
	}
	
	protected void setRating(int rating) {
		this.rating = rating;
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
		status.append(", Rating ").append(getRating()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getRating());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getRating() < getBaseRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
}