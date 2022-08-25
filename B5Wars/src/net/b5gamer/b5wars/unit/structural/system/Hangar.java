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
 * A Hangar allows a vessel to contain smaller craft such as fighters and shuttles
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Hangar extends System {

	// TODO fighters and shuttles present
	// TODO launch direction - arc??
	// TODO fighter type restriction
	// TODO shuttles

	private static final long serialVersionUID = 3603010055124536946L;

	private final int    baseLaunchRate;               // base number of fighters/shuttles that can be launched/land per turn
	private       int    launchRate;                   // number of fighters/shuttles that can be launched/land per turn
	private       double operationsRateModifier = 1.0; // modifier to the rate at which hangar operations are performed 
	private       int    bayCollapseCount       = 0;   // number of times hanger has suffered a partial bay collapse critical hit

	/**
	 * @param damageBoxes amount of damage the hangar can sustain before being destroyed
	 * @param armor       amount of armor protecting the hangar
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the hangar, may be null
	 * @param properties  additional properties 
	 */
	public Hangar(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "launchRate"));
		
		// launchDirections
		// fighters
		// fighterPermitted
		// fighterProhibited
		
		// number of fighters/shuttles
		// type of fighters/shuttles
		// shuttleStats
	}

	/**
	 * @param damageBoxes amount of damage the hangar can sustain before being destroyed
	 * @param armor       amount of armor protecting the hangar
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the hangar, may be null
	 * @param launchRate  number of fighters/shuttles that can be launched/land per turn
	 */
	public Hangar(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int launchRate) {
		super(damageBoxes, armor, arc, name);

        if (!(launchRate > 0)) {
            throw new IllegalArgumentException("launchRate must be a positive number");
        } 

        this.baseLaunchRate = launchRate;
        this.launchRate     = launchRate;
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("launchRate", String.valueOf(getBaseLaunchRate()));
		
		return properties;
	}
	
	public String getType() {
		return "Hangar";
	}
	
	public int getBaseLaunchRate() {
		return baseLaunchRate;
	}

	/**
	 * the number of fighters/shuttles that can be launched/land per turn
	 * 
	 * @return the number of fighters/shuttles that can be launched/land per turn
	 */
	public int getLaunchRate() {
		return launchRate;
	}
	
	/**
	 * the number of fighters/shuttles that can be launched/land per turn
	 * 
	 * @param launchRate the number of fighters/shuttles that can be launched/land per turn
	 */
	private void setLaunchRate(final int launchRate) {
		this.launchRate = launchRate < 0 ? 0 : launchRate;
	}
	
	/**
	 * the modifier to the rate at which hangar operations are performed
	 * 
	 * @return the modifier to the rate at which hangar operations are performed
	 */
	public double getOperationsRateModifier() {
		return operationsRateModifier;
	}

	/**
	 * the modifier to the rate at which hangar operations are performed
	 * 
	 * @param operationsRateModifier the modifier to the rate at which hangar operations are performed
	 */
	public void setOperationsRateModifier(final double operationsRateModifier) {
		this.operationsRateModifier = operationsRateModifier < 0 ? 0 : operationsRateModifier;
	}	

	/**
	 * the number of times hanger has suffered a partial bay collapse critical hit
	 * 
	 * @return the number of times hanger has suffered a partial bay collapse critical hit
	 */
	private int getBayCollapseCount() {
		return bayCollapseCount;
	}

	/**
	 * the number of times hanger has suffered a partial bay collapse critical hit
	 * 
	 * @param bayCollapseCount the number of times hanger has suffered a partial bay collapse critical hit
	 */
	private void setBayCollapseCount(final int bayCollapseCount) {
		this.bayCollapseCount = bayCollapseCount < 0 ? 0 : bayCollapseCount;
	}

	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 12) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 18) {
			// docking links damaged, all hangar bay activities take twice as long
			setOperationsRateModifier(0.5);
			Logger.info("    - docking links damaged, all hangar bay activities take twice as long");
		} else if (roll <= 24) {
			// partial bay collapse, first critical 1/2 launch rate (round down), second critical 1/6 launch rate, third critical hanger destroyed 
			resolvePartialBayCollapse();
		} else {
			// both above effects suffered
			setOperationsRateModifier(0.5);
			Logger.info("    - docking links damaged, all hangar bay activities take twice as long");
			resolvePartialBayCollapse();
		}
	}	
	
	/**
	 * resolve partial bay collapse critical hit
	 */
	private void resolvePartialBayCollapse() {
		if (getBayCollapseCount() < 1) {
			// first occurrence, 1/2 launch rate
			setLaunchRate((getLaunchRate() - (getLaunchRate() % 2)) / 2);
			Logger.info("    - first partial bay collapse, 1/2 launch rate");
		} else if (getBayCollapseCount() < 2) {
			// second occurrence, 1/6 launch rate
			setLaunchRate((getLaunchRate() - (getLaunchRate() % 6)) / 6);
			Logger.info("    - second partial bay collapse, 1/6 launch rate");
		} else {
			// third occurrence, hangar destroyed
//			setDamageBoxes(0);
			setDestroyed(true);
			Logger.info("    - third partial bay collapse, hangar destroyed");
		}
		
		setBayCollapseCount(getBayCollapseCount() + 1);
	}
	
	protected void adjustSystem() {
		// TODO
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getLaunchRate());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getLaunchRate() < getBaseLaunchRate()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 700;
	}
	
}
