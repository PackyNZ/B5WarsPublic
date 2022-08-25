/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.dice;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Dice is a collection of {@link net.b5gamer.dice.Die}, which may be rolled 
 * as required to generate a random number  
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public final class Dice {

	private static final List<Die> DICE = new ArrayList<Die>(0); // available dice

	private static Class<? extends Die> dieClass = null; // the class of Die to use
	
	public static Die d2   = null;
	public static Die d3   = null;
	public static Die d4   = null;
	public static Die d5   = null;
	public static Die d6   = null;
	public static Die d8   = null;
	public static Die d10  = null;
	public static Die d12  = null;
	public static Die d20  = null;
	public static Die d100 = null;
	
	private Dice() {
	}
	
	static {
		try {
			setDieClass(PseudoRandomDie.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * a list of available dice
	 * 
	 * @return a list of available dice
	 */
	private static final List<Die> getDice() {
		return DICE;
	}
	
    /**
     * an iterator of all dice
     * 
	 * @return an iterator of all dice
	 */
	public static final Iterator<Die> getDieIterator() {
		return getDice().iterator();
	}
	
	/**
     * a die with a given name
     * 
     * @param  name name of the die to return
     * @return      the die, or null if a die with the given name doesn't exist
     */
    public static final Die getDie(final String name) {
    	Die result = null;

    	for (Iterator<Die> iterator = getDice().iterator(); iterator.hasNext();) {
    		Die die = iterator.next();
            if ((die != null) && (die.getName().equalsIgnoreCase(name))) {
                result = die;
                break;
            }                
    	} 

        return result;
    }
    
	/**
	 * the class of Die to use
	 * 
	 * @return the class of Die to use
	 */
	public static final Class<? extends Die> getDieClass() {
		return dieClass;
	}

	/**
     * the class of Die to use, which must expose a public constructor in the form of
     * DieClass(int numberOfSides)
     * 
     * @param dieClass the class of Die to use
     * 
	 * @throws NoSuchMethodException failed to invoke DieClass(int numberOfSides) constructor
     */
	public static final synchronized void setDieClass(Class<? extends Die> dieClass) throws NoSuchMethodException {
		if (dieClass == null) {
            throw new IllegalArgumentException("dieClass cannot be null");			
		}
		
		if (getDieClass() != dieClass) {
			Dice.dieClass = dieClass;
			
			getDice().clear();
			d2   = createDie(2);
			getDice().add(d2);
			d3   = createDie(3);
			getDice().add(d3);
			d4   = createDie(4);
			getDice().add(d4);
			d5   = createDie(5);
			getDice().add(d5);
			d6   = createDie(6);
			getDice().add(d6);
			d8   = createDie(8);
			getDice().add(d8);
			d10  = createDie(10);
			getDice().add(d10);
			d12  = createDie(12);
			getDice().add(d12);
			d20  = createDie(20);
			getDice().add(d20);
			d100 = createDie(100);
			getDice().add(d100);
		}
	}
	
	/**
     * create and return a die with the specified number of sides
     * 
	 * @param  numberOfSides number of sides on the die
     * @return               the new die
     * 
	 * @throws NoSuchMethodException failed to invoke DieClass(int numberOfSides) constructor
	 */
	public static final Die createDie(final int numberOfSides) throws NoSuchMethodException {
    	Die result = null;
    	
		try {
			Constructor<? extends Die> constructor = getDieClass().getConstructor(new Class[] {int.class});
			result = (Die) constructor.newInstance(numberOfSides);
		} catch (Exception e) {
			throw new NoSuchMethodException("Failed to create Die using " + getDieClass().getName() + "(int numberOfSides) constructor");
		}

    	return result;
    }
    
}
