/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.dice.Dice;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * An Engine provides thrust which is output by thrusters
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Engine extends System {

	private static final long serialVersionUID = -1745101641816150379L;

	private final int     baseFreeThrust;              // base amount of free thrust generated per turn
	private       int     freeThrust;                  // amount of free thrust generated per turn
	private final int     efficiencyPower;             // amount of power required to generate extra thrust
	private final int     efficiencyThrust;            // amount of extra thrust generated using additional power
	private       boolean zeroThrust          = false; // whether engine produces zero thrust for a turn due to a critical hit
	private       boolean maximumAcceleration = false; // whether maximum acceleration is mandatory for a turn due to a critical hit

	/**
	 * @param damageBoxes amount of damage the engine can sustain before being destroyed
	 * @param armor       amount of armor protecting the engine
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the engine, may be null
	 * @param properties  additional properties 
	 */
	public Engine(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "freeThrust"),
				parseEfficiencyPower(properties.getProperty("efficiency")), 
				parseEfficiencyThrust(properties.getProperty("efficiency")));
	}

	/**
	 * @param damageBoxes      amount of damage the engine can sustain before being destroyed
	 * @param armor            amount of armor protecting the engine
	 * @param arc              arc for incoming fire, may be null
	 * @param name             name of the engine, may be null
	 * @param freeThrust       amount of free thrust generated per turn
	 * @param efficiencyPower  amount of power required to generate extra thrust
	 * @param efficiencyThrust amount of extra thrust generated using additional power  
	 */
	public Engine(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int freeThrust, final int efficiencyPower, final int efficiencyThrust) {
		super(damageBoxes, armor, arc, name);
		
        if (!(freeThrust > 0)) {
            throw new IllegalArgumentException("freeThrust must be a positive number");
        } 
        if (!(efficiencyPower > 0)) {
            throw new IllegalArgumentException("efficiencyPower must be a positive number");
        } 
        if (!(efficiencyThrust > 0)) {
            throw new IllegalArgumentException("efficiencyThrust must be a positive number");
        } 
		
		this.baseFreeThrust   = freeThrust;
		this.freeThrust       = freeThrust;
		this.efficiencyPower  = efficiencyPower;
		this.efficiencyThrust = efficiencyThrust;
	}
		
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("freeThrust", String.valueOf(getBaseFreeThrust()));
		properties.setProperty("efficiency", String.valueOf(getEfficiency()));
		
		return properties;
	}
	
	public String getType() {
		return "Engine";
	}

	/**
	 * the base amount of free thrust generated per turn
	 * 
	 * @return the base amount of free thrust generated per turn
	 */
	public int getBaseFreeThrust() {
		return baseFreeThrust;
	}
	
	/**
	 * the amount of free thrust generated per turn
	 * 
	 * @return the amount of free thrust generated per turn
	 */
	public int getFreeThrust() {
		return freeThrust;
	}

	/**
	 * the amount of free thrust generated per turn
	 * 
	 * @param freeThrust the amount of free thrust generated per turn
	 */
	protected void setFreeThrust(final int freeThrust) {
		this.freeThrust = freeThrust < 0 ? 0 : freeThrust; 
	}
		
	/**
	 * the amount of power required to generate extra thrust
	 * 
	 * @return the amount of power required to generate extra thrust
	 */
	public int getEfficiencyPower() {
		return efficiencyPower;
	}

	/**
	 * the amount of extra thrust generated using additional power
	 * 
	 * @return the amount of extra thrust generated using additional power
	 */
	public int getEfficiencyThrust() {
		return efficiencyThrust;
	}

	/**
	 * the efficiency of the engine to convert power into thrust
	 * 
	 * @return the efficiency of the engine to convert power into thrust
	 */
	public String getEfficiency() {
		return new StringBuilder().append(getEfficiencyPower()).append("/").append(getEfficiencyThrust()).toString();
	}
	
	/**
	 * the amount of thrust produced per additional point of power
	 * 
	 * @return the amount of thrust produced per additional point of power
	 */
	public double getEfficiencyRatio() {
		return (double) getEfficiencyThrust() / (double) getEfficiencyPower();
	}
	
	/**
	 * whether engine produces zero thrust for a turn due to a critical hit
	 * 
	 * @return whether engine produces zero thrust for a turn due to a critical hit
	 */
	public boolean isZeroThrust() {
		return zeroThrust;
	}

	/**
	 * whether engine produces zero thrust for a turn due to a critical hit
	 * 
	 * @param zeroThrust whether engine produces zero thrust for a turn due to a critical hit
	 */
	protected void setZeroThrust(final boolean zeroThrust) {
		this.zeroThrust = zeroThrust;
	}

	/**
	 * whether maximum acceleration is mandatory for a turn due to a critical hit
	 * 
	 * @return whether maximum acceleration is mandatory for a turn due to a critical hit
	 */
	public boolean isMaximumAcceleration() {
		return maximumAcceleration;
	}

	/**
	 * whether maximum acceleration is mandatory for a turn due to a critical hit
	 * 
	 * @param maximumAcceleration whether maximum acceleration is mandatory for a turn due to a critical hit
	 */
	protected void setMaximumAcceleration(final boolean maximumAcceleration) {
		this.maximumAcceleration = maximumAcceleration;
	}

	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 14) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 20) {
			// thrust reduced, -2 thrust
			Logger.info("    - thrust reduced, -2 thrust");
			setFreeThrust(getFreeThrust() - 2);
		} else if (roll <= 27) {
			// engine shorted, 1-14 no thrust next turn, 15+ mandatory maximum acceleration next turn
			resolveEngineShort();
		} else {
			// both above effects suffered
			Logger.info("    - thrust reduced, -2 thrust");
			setFreeThrust(getFreeThrust() - 2);
			resolveEngineShort();
		}
	}	
	
	/**
	 * resolve engine shorted critical hit
	 */
	protected void resolveEngineShort() {
		int roll = Dice.d20.roll();
		
		if (roll <= 14) {
			// no thrust next turn
			Logger.info("    - engine shorted, no thrust next turn");
			setZeroThrust(true);
		} else {
			// mandatory maximum acceleration next turn
			Logger.info("    - engine shorted, mandatory maximum acceleration next turn");
			setMaximumAcceleration(true);
		}
	}
	
	protected void adjustSystem() {
		setZeroThrust(false);
		setMaximumAcceleration(false);
	}
	
	protected static int parseEfficiencyPower(final String efficiency) {
		int efficiencyPower = 0;
		
		try {
			efficiencyPower = Integer.valueOf(parseEfficiency(efficiency)[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("efficiency is in an invalid format");
        } 	
		
		return efficiencyPower;		
	}
	
	protected static int parseEfficiencyThrust(final String efficiency) {
		int efficiencyThrust = 0;

		try {
			efficiencyThrust = Integer.valueOf(parseEfficiency(efficiency)[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("efficiency is in an invalid format");
        } 		

		return efficiencyThrust;		
	}
	
	protected static String[] parseEfficiency(final String efficiency) {
        if ((efficiency == null) || !(efficiency.length() > 0)) {
            throw new IllegalArgumentException("efficiency cannot be null or blank");
        } 
		
		if (efficiency.contains("/")) {
			String[] values = efficiency.split("/");
			
			if (values.length == 2) {
				return values;
			} else {
				throw new IllegalArgumentException("efficiency is in an invalid format");
			}
		} else {			
			throw new IllegalArgumentException("efficiency is in an invalid format");
		}
	}
	
	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Armor ").append(getArmor()).append("/").append(getBaseArmor());
		status.append(", Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());
		status.append(", Free Thrust ").append(getFreeThrust()).append("/").append(getBaseFreeThrust()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getFreeThrust());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getFreeThrust() < getBaseFreeThrust()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}	
	
	@Override
	public int getRecognitionOrder() {
		return 400;
	}
	
}
