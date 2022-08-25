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
 * A PowerCapacitor provides power to weapons and other powered systems on a vessel 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class PowerCapacitor extends System implements PowerProvidingSystem {

	private static final long serialVersionUID = 8911256505896835580L;

	private       int availablePower;   // amount of power generated by the reactor each turn
	private final int baseRechargeRate;
	private       int rechargeRate;

	/**
	 * @param damageBoxes amount of damage the power capacitor can sustain before being destroyed
	 * @param armor       amount of armor protecting the power capacitor
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the power capacitor, may be null
	 * @param properties  additional properties 
	 */
	public PowerCapacitor(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "availablePower"),
				PropertyReader.getInteger(properties, "rechargeRate"));
	}

	/**
	 * @param damageBoxes    amount of damage the power capacitor can sustain before being destroyed
	 * @param armor          amount of armor protecting the power capacitor
	 * @param arc            arc for incoming fire, may be null
	 * @param name           name of the reactor, may be null
	 * @param availablePower excess power generated by the power capacitor each turn
	 * @param rechargeRate  
	 */
	public PowerCapacitor(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int availablePower, final int rechargeRate) {
		super(damageBoxes, armor, arc, name);
		
        if (!(rechargeRate > 0)) {
            throw new IllegalArgumentException("rechargeRate must be a positive number");
        } 	
        
        this.availablePower   = availablePower;
        this.baseRechargeRate = rechargeRate;
        this.rechargeRate     = rechargeRate;
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("availablePower", String.valueOf(getAvailablePower())); // TODO
		properties.setProperty("rechargeRate", String.valueOf(getBaseRechargeRate()));
		
		return properties;
	}
	
	public String getType() {
		return "Power Capacitor";
	}
	
	public int getBaseRechargeRate() {
		return baseRechargeRate;
	}

	public int getRechargeRate() {
		return rechargeRate;
	}

	public void setRechargeRate(int rechargeRate) {
		this.rechargeRate = rechargeRate;
	}

	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getBaseAvailablePower() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getAvailablePower() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAvailablePower(int availablePower) {
		// TODO Auto-generated method stub
		
	}
		
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getRechargeRate());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getRechargeRate() < getBaseRechargeRate()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 610;
	}
	
}