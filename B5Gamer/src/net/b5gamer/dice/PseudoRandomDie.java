/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.dice;

import java.util.Random;

/**
 * This implementation of Die uses {@link java.util.Random#nextInt(int) java.util.Random.nextInt(number of sides) + 1} 
 * to generate a pseudo random number between 1 and the number of sides inclusive
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class PseudoRandomDie extends Die {
	
	private static final Random RANDOM = new Random(); // pseudo random number generator
	
	/**
	 * @param numberOfSides number of sides on the die
	 */
	public PseudoRandomDie(final int numberOfSides) {
        super(numberOfSides);
	}
	
	public final int roll() {
		return RANDOM.nextInt(getNumberOfSides()) + 1;
	}
	
}
