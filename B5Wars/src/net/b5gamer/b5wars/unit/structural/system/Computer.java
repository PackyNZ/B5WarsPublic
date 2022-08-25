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
 * An advanced computer system providing additional fire control capabilities
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class Computer extends System implements FireControlSystem {

	private static final long serialVersionUID = -6429368030385129915L;

	public static final Font ANNOTATION_FONT = new Font("Arial", Font.PLAIN, 7);
	
	private final int baseBonusFireControlPoints; // base amount of bonus fire control points provided by the computer
	
	/**
	 * @param damageBoxes amount of damage the computer can sustain before being destroyed
	 * @param armor       amount of armor protecting the computer
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the computer, may be null
	 * @param properties  additional properties 
	 */
	public Computer(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "bonusFireControlPoints"));
	}
	
	/**
	 * @param damageBoxes amount of damage the pilot can sustain before being destroyed
	 * @param armor       amount of armor protecting the pilot
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the pilot, may be null
	 * @param groupSize   amount to group damage into to determine bonus fire control points 
	 */		
	public Computer(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int bonusFireControlPoints) {
		super(damageBoxes, armor, arc, name);
		
		if (!(bonusFireControlPoints > 0)) {
			throw new IllegalArgumentException("bonusFireControlPoints must be a positive number");
		}
		
		this.baseBonusFireControlPoints = bonusFireControlPoints;
	}

	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("bonusFireControlPoints", String.valueOf(getBaseBonusFireControlPoints()));
		
		return properties;
	}
	
	public String getType() {
		return "Computer";
	}
	
	/**
	 * the base amount of bonus fire control points provided by the computer
	 * 
	 * @return the base amount of bonus fire control points provided by the computer
	 */
	public int getBaseBonusFireControlPoints() {
		return baseBonusFireControlPoints;
	}

	/**
	 * the amount of bonus fire control points provided by the computer
	 * 
	 * @return the amount of bonus fire control points provided by the computer
	 */
	public int getBonusFireControlPoints() {
		return getDamageBoxes() / (getBaseDamageBoxes() / getBaseBonusFireControlPoints());
	}
	
	protected void resolveCriticalHit(final int roll) {
		// no critical hits
	}	
	
	protected void adjustSystem() {
	}

	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getBonusFireControlPoints());
	}
	
	@Override
	public Font getAnnotationFont(int index) {
		return ANNOTATION_FONT;
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getBonusFireControlPoints() < getBaseBonusFireControlPoints()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 810;
	}
	
}
