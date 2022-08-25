/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

public class ShadingField extends Shield {

	private static final long serialVersionUID = 7906976833950876674L;

	public ShadingField(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(damageBoxes, armor, arc, Integer.parseInt(number));
	}
	
	public ShadingField(final int damageBoxes, final int armor, final Arc arc, final int number) {
		super(damageBoxes, armor, arc, number, 5, 4);
	}

	@Override
	public Properties getInitProperties() {
		return null;
	}
	
	public String getType() {
		return "Shading Field";
	}
	
}
