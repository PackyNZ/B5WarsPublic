package net.b5gamer.b5wars.ajax;

import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.icon.IconPosition;

public class WeaponBean extends PoweredSystemBean {

	private IconPosition weaponNumberPosition = null;
	
	public WeaponBean() {
		super();
	}

	public WeaponBean(final Weapon system) {
		super(system);
		
		this.weaponNumberPosition = system.getWeaponNumberPosition();
	}

	public final IconPosition getWeaponNumberPosition() {
		return weaponNumberPosition;
	}

	public final void setWeaponNumberPosition(final IconPosition weaponNumberPosition) {
		this.weaponNumberPosition = weaponNumberPosition;
	}

}
