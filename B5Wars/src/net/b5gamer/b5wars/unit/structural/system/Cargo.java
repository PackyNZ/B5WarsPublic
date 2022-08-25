/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * Cargo represents a cargo hold on a vessel that is typically used to carry bulk cargo,
 * but can be outfitted for other purposes such as carrying passengers
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Cargo extends System {

	private static final long serialVersionUID = 3467385007739950424L;

	private final String  cargoType;       // type of cargo the hold is designed to carry
	private final int     transferRate;    // the rate at which cargo can be loaded/offloaded
	private final boolean droppable;       // whether the cargo hold can be dropped
	private       boolean dropped = false; // whether the cargo hold is dropped
	
	/**
	 * @param damageBoxes amount of damage the cargo hold can sustain before being destroyed
	 * @param armor       amount of armor protecting the cargo hold
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the reactor, may be null
	 * @param properties  additional properties 
	 */
	public Cargo(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, properties.getProperty("cargoType"),
				PropertyReader.getInteger(properties, "transferRate"),
				PropertyReader.getBoolean(properties, "droppable"));
	}
	
	/**
	 * @param damageBoxes  amount of damage the cargo hold can sustain before being destroyed
	 * @param armor        amount of armor protecting the cargo hold
	 * @param arc          arc for incoming fire, may be null
	 * @param name         name of the reactor, may be null
	 * @param cargoType    type of cargo the hold is designed to carry, may be null
	 * @param transferRate the rate at which cargo can be loaded/offloaded
	 * @param droppable    whether the cargo hold can be dropped
	 */	
	public Cargo(final int damageBoxes, final int armor, final Arc arc, final String name,
			final String cargoType, final int transferRate, final boolean droppable) {
		super(damageBoxes, armor, arc, name);
		
		if (!(transferRate > 0)) {
			throw new IllegalArgumentException("transferRate must be a positive number");
		}
		
		this.cargoType    = (cargoType != null && cargoType.length() > 0) ? cargoType : null;
		this.transferRate = transferRate;
		this.droppable    = droppable;	
	}

	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("cargoType", getCargoType());
		properties.setProperty("transferRate", String.valueOf(getTransferRate()));
		properties.setProperty("droppable", String.valueOf(isDroppable()));
		
		return properties;
	}
	
	public String getType() {
		return (getCargoType() != null) ? getCargoType() : "Cargo";
	}

	/**
	 * the type of cargo the hold is designed to carry
	 * 
	 * @return the type of cargo the hold is designed to carry
	 */
	public String getCargoType() {
		return cargoType;
	}

	/**
	 * the the rate at which cargo can be loaded/offloaded
	 * 
	 * @return the the rate at which cargo can be loaded/offloaded
	 */
	public int getTransferRate() {
		return transferRate;
	}

	/**
	 * whether the cargo hold can be dropped
	 * 
	 * @return whether the cargo hold can be dropped
	 */
	public boolean isDroppable() {
		return droppable;
	}

	/**
	 * whether the cargo hold is dropped
	 * 
	 * @return whether the cargo hold is dropped
	 */
	public boolean isDropped() {
		return dropped;
	}

	/**
	 * whether the cargo hold is dropped
	 * 
	 * @param dropped whether the cargo hold is dropped
	 */
	public void setDropped(final boolean dropped) {
		this.dropped = dropped;
	}

	protected void resolveCriticalHit(final int roll) {
		// no critical hits
	}	

	protected void adjustSystem() {
	}

	@Override
	public int getAnnotationCount() {
		return 2;
	}
	
	@Override
	public String getAnnotation(int index) {
		if ((index < 0) || (index >= getAnnotationCount())) {
			throw new IndexOutOfBoundsException();
		}
		
		return (index == 0) ? ((getName() != null) ? getName() : "") : String.valueOf(getTransferRate());
	}
	
	@Override
	public int getRecognitionOrder() {
		return 800;
	}
	
}
