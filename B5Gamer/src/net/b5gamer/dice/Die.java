/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.dice;

/**
 * A Die is a multi-sided object which may be rolled to generate a number between 1 and the 
 * number of sides on the die inclusive 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class Die {
	
	private final String name;          // name of the die
	private final int    numberOfSides; // number of sides on the die	
	
	/**
	 * @param numberOfSides number of sides on the die
	 */
	protected Die(final int numberOfSides) {
        if (!(numberOfSides > 0)) {
            throw new IllegalArgumentException("numberOfSides must be a positive number");
        } 
		
		this.name          = "d" + numberOfSides;
		this.numberOfSides = numberOfSides;
	}
	
	/**
	 * the name of the die
	 * 
	 * @return the name of the die
	 */
	public final String getName() {
		return name;
	}

	/**
	 * the number of sides on the die
	 * 
	 * @return the number of sides on the die
	 */
	public final int getNumberOfSides() {
		return numberOfSides;
	}
    		
	/**
	 * the result of a single roll of this die
	 * 
	 * @return the result of a single roll of this die
	 */
	public abstract int roll();

	/**
	 * the result of rolling this die a given number of times
	 * 
	 * @param  numberOfRolls number of times to roll the die
	 * @return               the result of rolling this die a given number of times
	 */
	public final int roll(final int numberOfRolls) {
		int result = 0;
		
        if (!(numberOfRolls > 0)) {
            throw new IllegalArgumentException("numberOfRolls must be a positive number");
        } 
		
		for (int index = 0; index < numberOfRolls; index++) {
			result += roll();
		}
		
		return result;
	}
	
}
