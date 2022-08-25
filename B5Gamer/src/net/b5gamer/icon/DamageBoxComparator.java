/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.util.Comparator;

public class DamageBoxComparator implements Comparator<DamageBox> {

	public int compare(DamageBox damageBox1, DamageBox damageBox2) {
		if (damageBox1.getHits() != damageBox2.getHits()) {
			return (damageBox1.getHits() > damageBox2.getHits()) ? -1 : 1;
		} else if (damageBox1.getShape().getBounds().y > damageBox2.getShape().getBounds().y) {
			return 1;
		} else if (damageBox1.getShape().getBounds().y < damageBox2.getShape().getBounds().y) {
			return -1;
		} else if (damageBox1.getShape().getBounds().x > damageBox2.getShape().getBounds().x) {
			return 1;
		} else if (damageBox1.getShape().getBounds().x < damageBox2.getShape().getBounds().x) {
			return -1;		
		} else {
			return 1;
		}
	}

}
