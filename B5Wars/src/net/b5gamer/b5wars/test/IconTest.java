package net.b5gamer.b5wars.test;

import net.b5gamer.awt.geom.ShapeUtil;
import net.b5gamer.b5wars.ui.scs.ArcComponent;
import net.b5gamer.b5wars.ui.scs.ArmorComponent;
import net.b5gamer.b5wars.ui.scs.PowerComponent;

public class IconTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Armor Circle:");
		System.out.println(ShapeUtil.toSVG(ArmorComponent.CIRCLE));			
		System.out.println("");

		System.out.println("Power Diamond:");
		System.out.println(ShapeUtil.toSVG(PowerComponent.DIAMOND));			
		System.out.println("");
		
		System.out.println("Arc Hexes:");
		for (int i = 0; i < ArcComponent.HEXES.length; i++) {
			System.out.println(ShapeUtil.toSVG(ArcComponent.HEXES[i]));			
		}
	}

}
