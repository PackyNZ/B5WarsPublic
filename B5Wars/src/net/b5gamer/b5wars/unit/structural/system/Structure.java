/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.util.ArrayList;
import java.util.List;

import net.b5gamer.icon.DamageBoxStringSerializer;
import net.b5gamer.icon.DamageableIcon;
import net.b5gamer.icon.IconUtil;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * Structure represents a structure block on a vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Structure extends System implements StructuralSystem {

	private static final long serialVersionUID = 5597386498484439876L;

	/**
	 * @param damageBoxes amount of damage the structure can sustain before being destroyed
	 * @param armor       amount of armor protecting the structure
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the structure, may be null
	 * @param properties  additional properties 
	 */
	public Structure(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name);
	}
	
	/**
	 * @param damageBoxes amount of damage the structure can sustain before being destroyed
	 * @param armor       amount of armor protecting the structure
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the structure, may be null
	 */
	public Structure(final int damageBoxes, final int armor, final Arc arc, final String name) {
		super(damageBoxes, armor, arc, name);
		
        if (damageBoxes <= 0) {
            throw new IllegalArgumentException("damageBoxes must be a positive integer");
        } 
	}
	
	public String getType() {
		return "Structure";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// no critical hits
	}	
	
	protected void adjustSystem() {
	}
	
	@Override
	public void setIcon(DamageableIcon icon) {
		if (getIconFilenames() == null) {
			setupIconVariables(icon);
		}
		
		this.icon = icon;
		getIcons()[0] = icon;
	}
	
	@Override
	protected void prepareIcons() {
		if (getIconFilenames() == null) {
			prepareCustomStructureIcon();
		}
	}
	
	protected synchronized void prepareCustomStructureIcon() {
		if ((getIconPosition() != null) && (getIconPosition().getDefinition() != null) && (getIconPosition().getDefinition().trim().length() > 0)) {
			DamageableIcon icon = new DamageableIcon(null, DamageBoxStringSerializer.deserialize(getIconPosition().getDefinition()));
			icon.orderDamageBoxes();
			icon.reposition();
			
			setupIconVariables(icon);
		} else {			
			setupIconVariables(IconUtil.createCustomIcon(getBaseDamageBoxes()));
		}
	}
	
	protected synchronized void setupIconVariables(DamageableIcon icon) {
		List<String> filenames = new ArrayList<String>();
		filenames.add("N/A");
		setIconFilenames(filenames);
		
		setIcons(new DamageableIcon[1]);
		getIcons()[0] = icon;
		setIcon(icon);
	}
	
}
