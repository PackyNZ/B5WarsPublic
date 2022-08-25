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
 * !!--- work in progress ---!!
 * Combined command & control and sensor systems
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Control extends PoweredSystem {

	private static final long serialVersionUID = -7590952820116942849L;

	private final int baseSensorRating; // base amount of EW points produced by the sensor per turn
	private       int sensorRating;     // amount of EW points produced by the sensor per turn

	/**
	 * @param damageBoxes amount of damage the control can sustain before being destroyed
	 * @param armor       amount of armor protecting the control
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the control, may be null
	 * @param properties  additional properties 
	 */
	public Control(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"),
				PropertyReader.getInteger(properties, "sensorRating"));
	}
	
	/**
	 * @param damageBoxes  amount of damage the control can sustain before being destroyed
	 * @param armor        amount of armor protecting the control
	 * @param arc          arc for incoming fire, may be null
	 * @param name         name of the control, may be null
	 * @param power        amount of power consumed by the control
	 * @param sensorRating amount of EW points produced by the control per turn
	 */
	public Control(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int sensorRating) {
		super(damageBoxes, armor, arc, name, power);
		
        if (!(sensorRating > 0)) {
            throw new IllegalArgumentException("sensorRating must be a positive number");
        } 

        this.baseSensorRating = sensorRating;
        this.sensorRating     = sensorRating;
	}
		
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("sensorRating", String.valueOf(getBaseSensorRating()));
		
		return properties;
	}
	
	public String getType() {
		return "Control";
	}
		
	/**
	 * the base amount of EW points produced by the sensor per turn
	 * 
	 * @return the base amount of EW points produced by the sensor per turn
	 */
	public int getBaseSensorRating() {
		return baseSensorRating;
	}

	/**
	 * the amount of EW points produced by the sensor per turn
	 * 
	 * @return the amount of EW points produced by the sensor per turn
	 */
	public int getSensorRating() {
		return sensorRating;
	}
	
	/**
	 * the amount of EW points produced by the sensor per turn
	 * 
	 * @param sensorRating the amount of EW points produced by the sensor per turn
	 */
	protected void setSensorRating(final int sensorRating) {
        if (sensorRating > getBaseSensorRating()) {
            throw new IllegalArgumentException("sensorRating cannot exceed base sensor rating");
        } 

		this.sensorRating = sensorRating < 0 ? 0 : sensorRating;		
	}

	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 14) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 18) {
			// output slightly reduced, -1 EW
			setSensorRating(getSensorRating() - 1);
			Logger.info("    - output slightly reduced, -1 EW");
		} else if (roll <= 22) {
			// output noticeably reduced, -2 EW
			setSensorRating(getSensorRating() - 2);
			Logger.info("    - output noticeably reduced, -2 EW");
		} else if (roll <= 26) {
			// output significantly reduced, -3 EW
			setSensorRating(getSensorRating() - 3);
			Logger.info("    - output significantly reduced, -3 EW");
		} else {
			// output critically reduced, -4 EW
			setSensorRating(getSensorRating() - 4);			
			Logger.info("    - output critically reduced, -4 EW");
		}
	}	
	
	protected void adjustSystem() {
	}
	
	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Armor ").append(getArmor()).append("/").append(getBaseArmor());
		status.append(", Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());
		status.append(", Sensor Rating ").append(getSensorRating()).append("/").append(getBaseSensorRating()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getSensorRating());
	}
		
	@Override
	public Color getAnnotationColor(int index) {
		return (getSensorRating() < getBaseSensorRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}	
	
	@Override
	public int getRecognitionOrder() {
		return 210;
	}
	
}
