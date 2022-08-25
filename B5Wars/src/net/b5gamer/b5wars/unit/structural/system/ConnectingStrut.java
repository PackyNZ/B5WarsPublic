/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.StructuralSection;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A Connecting Strut represents a weakness in the hull of a vessel, and when hit causes
 * double damage to the facing structure 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class ConnectingStrut extends System implements StructuralSystem {

	private static final long serialVersionUID = -1630776471390422088L;

	/**
	 * @param damageBoxes not used
	 * @param armor       not used
	 * @param arc         not used
	 * @param name        not used
	 * @param properties  not used
	 */
	public ConnectingStrut(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this();
	}
	
	/**
	 * 
	 */
	public ConnectingStrut() {
		super(0, 0, null, null);
	}
	
	public void setParent(final Section parent) {
    	if (!(parent instanceof StructuralSection)) {
            throw new IllegalArgumentException(getType() + " must belong to a " + StructuralSection.class.getSimpleName());
    	}
    	
		super.setParent(parent);
	}
	
	public String getType() {
		return "Connecting Strut";
	}
		
	public int getBaseDamageBoxes() {
		return getStructure().getBaseDamageBoxes();
	}
	
	public int getDamageBoxes() {
		return getStructure().getDamageBoxes();
	}
	
	public int applyDamage(final int damage) {
		return getStructure().applyDamage(damage * 2) / 2;
	}
	
	public int getBaseArmor() {
		return getStructure().getBaseArmor();
	}
	
	public Arc getArc() {
		return getStructure().getArc();
	}
	
	public boolean isValidTarget() {
		return getStructure().isValidTarget();
	}
	
	public boolean isDestroyed() {
		return getStructure().isDestroyed();
	}
	
	protected void resolveCriticalHit(final int roll) {
		// no critical hits
	}	
	
	protected void adjustSystem() {
	}
	
	/**
	 * the facing structure block
	 * 
	 * @return the facing structure block
	 */	
	private Structure getStructure() {
		return ((StructuralSection) getParent()).getStructure();
	}
	
	@Override
	public String getDescription() {
		return getFullName();
	}
	
}
