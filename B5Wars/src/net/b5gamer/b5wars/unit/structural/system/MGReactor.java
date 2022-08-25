/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class MGReactor extends System implements PowerProvidingSystem {

	private static final long serialVersionUID = -3151456639397746998L;

	public MGReactor(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name);
	}
	
	public MGReactor(final int damageBoxes, final int armor, final Arc arc, final String name) {
		super(damageBoxes, armor, arc, name);
	}

	public String getType() {
		return "M-G Reactor";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	

	protected void adjustSystem() {
		// TODO
	}

	public int getBaseAvailablePower() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getAvailablePower() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAvailablePower(int availablePower) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRecognitionOrder() {
		return 620;
	}
	
}
