package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

public abstract class HyperspaceDrive extends PoweredSystem {

	private static final long serialVersionUID = -2938078365429131624L;
	
	private final int jumpDelay;          // number of turns delay between closing and opening a jump point
	private       int turnsUntilJump = 0; // number of turns delay until jump drive can be used again

	/**
	 * @param damageBoxes amount of damage the drive can sustain before being destroyed
	 * @param armor       amount of armor protecting the drive
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the drive, may be null
	 * @param power       amount of power required for the drive to function
	 * @param jumpDelay   number of turns delay between closing and opening a jump point
	 */
	public HyperspaceDrive(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int jumpDelay) {
		super(damageBoxes, armor, arc, name, power);
		
        if (jumpDelay < 0) {
            throw new IllegalArgumentException("jumpDelay cannot be a negative number");
        } 

        this.jumpDelay = jumpDelay;
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("jumpDelay", String.valueOf(getJumpDelay()));
		
		return properties;
	}
	
	/**
	 * the number of turns delay between closing and opening a jump point
	 * 
	 * @return the number of turns delay between closing and opening a jump point
	 */
	public int getJumpDelay() {
		return jumpDelay;
	}
	
	/**
	 * the number of turns delay until jump drive can be used again
	 * 
	 * @return the number of turns delay until jump drive can be used again
	 */
	public int getTurnsUntilJump() {
		return turnsUntilJump;
	}

	/**
	 * the number of turns delay until jump drive can be used again
	 * 
	 * @param turnsUntilJump the number of turns delay until jump drive can be used again
	 */
	public void setTurnsUntilJump(final int turnsUntilJump) {
		this.turnsUntilJump = turnsUntilJump < 0 ? 0 : turnsUntilJump;
	}
	
	protected void adjustSystem() {
		setTurnsUntilJump(getTurnsUntilJump() - 1);
	}
	
}
