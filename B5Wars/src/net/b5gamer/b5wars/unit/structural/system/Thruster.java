/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;
import java.awt.Font;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.dice.Dice;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * Thrusters allow a vessel to change speed and direction of motion 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Thruster extends System implements DirectionalSystem {

	private static final long serialVersionUID = -2840813266149133098L;

	public static final Font ANNOTATION_FONT = new Font("Arial", Font.PLAIN, 6);
	
	private final int baseThrustRating;            // base maximum thrust that can be channelled through this thruster per turn
	private       int thrustRating;                // maximum thrust that can be channelled through this thruster per turn
	private       int thrustChannelled        = 0; // amount of thrust channelled through the thruster this turn
	private       int lostThrust              = 0; // amount of thrust lost per turn if this thruster is used
	private       int thrusterEfficiency      = 1; // thrust points that must be used per point of thrust output 
	private       int efficiencyCriticalCount = 0; // number of times thruster has suffered an efficiency reduced critical hit
	
	/**
	 * @param damageBoxes amount of damage the thruster can sustain before being destroyed
	 * @param armor       amount of armor protecting the thruster
	 * @param arc         arc for incoming fire and direction of thrust expelled from this thruster
	 * @param name        name of the thruster, may be null
	 * @param properties  additional properties 
	 */
	public Thruster(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "thrustRating"));
	}

	/**
	 * @param damageBoxes  amount of damage the thruster can sustain before being destroyed
	 * @param armor        amount of armor protecting the thruster
	 * @param arc          arc for incoming fire and direction of thrust expelled from this thruster
	 * @param name         name of the thruster, may be null
	 * @param thrustRating maximum thrust that can be channelled through this thruster per turn
	 */
	public Thruster(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int thrustRating) {
		super(damageBoxes, armor, arc, name);
		
        if (!(thrustRating > 0)) {
            throw new IllegalArgumentException("thrustRating must be a positive number");
        } 

        this.baseThrustRating = thrustRating;
        this.thrustRating     = thrustRating;
    }
		
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("thrustRating", String.valueOf(getBaseThrustRating()));
		
		return properties;
	}
	
	public String getType() {
		return "Thruster";
	}
	
	/**
	 * the base maximum thrust that can be channelled through this thruster per turn
	 * 
	 * @return the base maximum thrust that can be channelled through this thruster per turn
	 */
	public int getBaseThrustRating() {
		return baseThrustRating;
	}
	
	/**
	 * the maximum thrust that can be channelled through this thruster per turn
	 * 
	 * @return the maximum thrust that can be channelled through this thruster per turn
	 */
	public int getThrustRating() {
		return thrustRating;
	}

	/**
	 * the maximum thrust that can be channelled through this thruster per turn
	 * 
	 * @param thrustRating the maximum thrust that can be channelled through this thruster per turn
	 */
	private void setThrustRating(final int thrustRating) {
		this.thrustRating = thrustRating < 0 ? 0 : thrustRating;
		
		// if thrust rating reaches 0 thruster melts down and is destroyed
		if (thrustRating <= 0) {
			setDamageBoxes(0);
			setDestroyed(true);
		}				
	}

	/**
	 * the amount of thrust channelled through the thruster this turn
	 * 
	 * @return the amount of thrust channelled through the thruster this turn
	 */
	public int getThrustChannelled() {
		return thrustChannelled;
	}

	/**
	 * the amount of thrust channelled through the thruster this turn
	 * 
	 * @param thrustChannelled the amount of thrust channelled through the thruster this turn
	 */
	public void setThrustChannelled(final int thrustChannelled) {
		this.thrustChannelled = thrustChannelled < 0 ? 0 : thrustChannelled;
	}
	
	/**
	 * the amount of thrust lost per turn if this thruster is used
	 * 
	 * @return the amount of thrust lost per turn if this thruster is used
	 */
	public int getLostThrust() {
		return lostThrust;
	}

	/**
	 * the amount of thrust lost per turn if this thruster is used
	 * 
	 * @param lostThrust the amount of thrust lost per turn if this thruster is used
	 */
	private void setLostThrust(final int lostThrust) {
		this.lostThrust = lostThrust < 0 ? 0 : lostThrust;
	}

	/**
	 * the thrust points that must be used per point of thrust output 
	 * 
	 * @return the thrust points that must be used per point of thrust output 
	 */
	public int getThrusterEfficiency() {
		return thrusterEfficiency;
	}

	/**
	 * the thrust points that must be used per point of thrust output 
	 * 
	 * @param thrusterEfficiency the thrust points that must be used per point of thrust output 
	 */
	private void setThrusterEfficiency(final int thrusterEfficiency) {
		this.thrusterEfficiency = thrusterEfficiency < 0 ? 0 : thrusterEfficiency;
	}
	
	/**
	 * the number of times thruster has suffered an efficiency reduced critical hit
	 * 
	 * @return the number of times thruster has suffered an efficiency reduced critical hit
	 */
	private int getEfficiencyCriticalCount() {
		return efficiencyCriticalCount;
	}

	/**
	 * the number of times thruster has suffered an efficiency reduced critical hit
	 * 
	 * @param efficiencyCriticalCount the number of times thruster has suffered an efficiency reduced critical hit
	 */
	private void setEfficiencyCriticalCount(final int efficiencyCriticalCount) {
		this.efficiencyCriticalCount = efficiencyCriticalCount < 0 ? 0 : efficiencyCriticalCount;
	}

	protected void determineAndResolveCriticalHits() {
		// resolve critical hit for overthrusting, handle first before thrust rating reduced by critical hits
		if (getThrustChannelled() > getThrustRating()) {
			resolveCriticalHit(Dice.d20.roll() + getDamageSuffered() + (getThrustChannelled() - getThrustRating()));
		}
	
		// resolve normal critical hit
		if (!isDestroyed() && isDamagedThisTurn()) {
			resolveCriticalHit(Dice.d20.roll() + getDamageSuffered());
		}
	}
	
	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 14) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 19) {
			// outlet failure, first point of thrust per turn ignored and -1 thrust rating
			resolveOutletFailure();
			Logger.info("    - outlet failure, first point of thrust per turn ignored and -1 thrust rating");
		} else if (roll <= 24) {
			// efficiency reduced, requires extra thrust per point of thrust output
			resolveEfficiencyReduced();
		} else {
			// both above effects suffered
			resolveOutletFailure();
			Logger.info("    - outlet failure, first point of thrust per turn ignored and -1 thrust rating");
			resolveEfficiencyReduced();
		}
	}	
	
	/**
	 * resolve outlet failure critical hit
	 */
	private void resolveOutletFailure() {
		setLostThrust(getLostThrust() + 1);
		setThrustRating(getThrustRating() - 1);	
	}
	
	/**
	 * resolve efficiency reduced critical hit
	 */
	private void resolveEfficiencyReduced() {
		// gravtic drives ignore first efficiency reduced critical
		if ((getEfficiencyCriticalCount() > 0) || 
				(getParentUnit() == null) || (!getParentUnit().hasTrait(Trait.GRAVITIC))) {
			setThrusterEfficiency(getThrusterEfficiency() + 1);
			Logger.info("    - efficiency reduced, requires extra thrust per point of thrust output");
		} else {
			Logger.info("    - gravitic thruster, ignoring first efficiency reduced critical hit");			
		}
		
		setEfficiencyCriticalCount(getEfficiencyCriticalCount() + 1);			
	}
	
	protected void adjustSystem() {
		setThrustChannelled(0);
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
	protected String getIconFilenamePrefix() {
		StringBuilder filename = new StringBuilder();
		if (getArc().contains(180)) {
			filename.append("Main ");
		} else if (getArc().contains(0)) {
			filename.append("Retro ");
		} else if (getArc().contains(270)) {
			filename.append("Port ");
		} else if (getArc().contains(90)) {
			filename.append("Starboard ");
		}
		
		filename.append(getType());

		return filename.toString();
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
		return 100;
	}
	
}
