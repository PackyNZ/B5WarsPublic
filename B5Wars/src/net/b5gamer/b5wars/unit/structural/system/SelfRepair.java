/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * RC231
 * 
 * repair rate per turn
 * only reapir damage prev turn
 * cannot repair self-repair/armor/shuttles/fighters
 * cannot repair destroyed structure blocks (or anything attached)
 * can repair critical hits, 1 box each, c&c 4 boxes each, multiple crits repaired separately
 * can repair completely destroyed (except structure), not functional until totally repaired
 * max boxes repair = 10 x remaining damage on self repair, recalculate for damage
 * crit 19+ repair rate halved
 * 
 * vorlon - double repair rate by deactvating all weapons/shields for round
 * 
 * shadows - cannot repair structure or tendrils 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SelfRepair extends System implements RepairSystem {

	private static final long serialVersionUID = 3807913109451326669L;

	public static final Font ANNOTATION_FONT = new Font("Arial", Font.PLAIN, 7);
	
	private final int baseRepairRate;                  
	private       int repairRate;                  
	
	/**
	 * @param damageBoxes amount of damage the self repair can sustain before being destroyed
	 * @param armor       amount of armor protecting the self repair
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the self repair, may be null
	 * @param properties  additional properties 
	 */
	public SelfRepair(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "repairRate"));
	}

	/**
	 * @param damageBoxes amount of damage the self repair can sustain before being destroyed
	 * @param armor       amount of armor protecting the self repair
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the self repair, may be null
	 * @param repairRate  rate at which the self repair can repair damage
	 */
	public SelfRepair(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int repairRate) {
		super(damageBoxes, armor, arc, name);

        if (!(repairRate > 0)) {
            throw new IllegalArgumentException("repairRate must be a positive number");
        } 

        this.baseRepairRate = repairRate;
        this.repairRate     = repairRate;
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("repairRate", String.valueOf(getBaseRepairRate())); 
		
		return properties;
	}
	
	public String getType() {
		return "Self-Repair";
	}
	
	protected int getBaseRepairRate() {
		return baseRepairRate;
	}

	protected int getRepairRate() {
		return repairRate;
	}

	protected void setRepairRate(int repairRate) {
		this.repairRate = repairRate;
	}

	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

	public List<System> getEligibleSystems() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRepairCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int repair(System system, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getRepairRate());
	}
	
	@Override
	public Font getAnnotationFont(int index) {
		return ANNOTATION_FONT;
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getRepairRate() < getBaseRepairRate()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 870;
	}
	
}
