package net.b5gamer.b5wars.ajax;

import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.icon.IconPosition;

public class PoweredSystemBean extends SystemBean {

	private MutableValueBean power             = null;      
	private boolean          activated         = false;
	private IconPosition     powerIconPosition = null;
	
	public PoweredSystemBean() {
		super();
	}

	public PoweredSystemBean(final PoweredSystem system) {
		super(system);

		this.power              = new MutableValueBean(system.getBasePowerRequirement(), system.getPowerRequirement());
		this.activated          = system.isActivated();
		this.powerIconPosition  = system.getPowerIconPosition();
	}

	public final MutableValueBean getPower() {
		return power;
	}

	public final void setPower(final MutableValueBean power) {
		this.power = power;
	}

	public final boolean isActivated() {
		return activated;
	}

	public final void setActivated(final boolean activated) {
		this.activated = activated;
	}

	public final IconPosition getPowerIconPosition() {
		return powerIconPosition;
	}

	public final void setPowerIconPosition(final IconPosition powerIconPosition) {
		this.powerIconPosition = powerIconPosition;
	}
	
}
