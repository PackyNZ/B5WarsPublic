package net.b5gamer.test;

import java.util.Iterator;

import net.b5gamer.dice.Dice;
import net.b5gamer.dice.Die;
import net.b5gamer.dice.PseudoRandomDie;
import net.b5gamer.dice.ShuffledNumberDie;

public final class DiceTester {

	public static void main(String[] args) {
		int numberOfRolls = args.length > 0 ? Integer.parseInt(args[0]) : 10;
		
		try {
			Dice.setDieClass(PseudoRandomDie.class);
			System.out.println(PseudoRandomDie.class.getSimpleName() + " - uses java.util.Random.nextInt(number of sides) + 1");
			for (Iterator<Die> iterator = Dice.getDieIterator(); iterator.hasNext();) {
				Die die = iterator.next();
				StringBuilder rolls = new StringBuilder(die.getClass().getSimpleName()).append(" - 1").append(die.getName()).append(": ");
				for (int index = 0; index < numberOfRolls; index++) {
					if (index > 0) {
						rolls.append(",");
					}
					rolls.append(die.roll());
				}
				System.out.println(rolls);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("");
		
		try {
			Dice.setDieClass(ShuffledNumberDie.class);
			System.out.println(ShuffledNumberDie.class.getSimpleName() + " - uses java.util.Collections.shuffle(possible numbers)");
			for (Iterator<Die> iterator = Dice.getDieIterator(); iterator.hasNext();) {
				Die die = iterator.next();
				StringBuilder rolls = new StringBuilder(die.getClass().getSimpleName()).append(" - 1").append(die.getName()).append(": ");
				for (int index = 0; index < numberOfRolls; index++) {
					if (index > 0) {
						rolls.append(",");
					}
					rolls.append(die.roll());
				}
				System.out.println(rolls);
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
