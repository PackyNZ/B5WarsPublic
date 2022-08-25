/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.dice;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This implementation of Die uses {@link java.util.Collections#shuffle(List) java.util.Collections.shuffle(List)} 
 * to shuffle a list of possible numbers to generate a random result between 1 and the number of sides 
 * inclusive
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class ShuffledNumberDie extends Die {

	private final ArrayList<Integer> numbers = new ArrayList<Integer>(0); // all possible numbers from rolling the die 
	
	/**
	 * @param numberOfSides number of sides on the die
	 */
	public ShuffledNumberDie(final int numberOfSides) {
        super(numberOfSides);
		
		for (int number = 1; number <= numberOfSides; number++) {
			numbers.add(Integer.valueOf(number));
		}
	}
	
	public final int roll() {
		Collections.shuffle(numbers);
		
		return numbers.get(0).intValue();
	}
    
}
