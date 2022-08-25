package net.b5gamer.b5wars.ajax;

import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;

public final class AjaxBeanFactory {

	private AjaxBeanFactory() {
	}
	
	public static final SystemBean getSystemBean(final System system) {
		if (system instanceof Weapon) {
			return new WeaponBean((Weapon) system);
		} else if (system instanceof PoweredSystem) {
			return new PoweredSystemBean((PoweredSystem) system);
		} else {
			return new SystemBean(system);			
		}		
	}
	
}
