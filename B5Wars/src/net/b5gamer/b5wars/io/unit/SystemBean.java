package net.b5gamer.b5wars.io.unit;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * Bean used for reading systems from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SystemBean {

	private String     className   = null;
	private int        damageBoxes = 0;
	private int        armor       = 0;
	private Arc        arc         = null;
	private String     name        = null;
	private IconBean   systemIcon  = null;
	private IconBean   armorIcon   = null;
	private IconBean   powerIcon   = null;
	private IconBean   arcIcon     = null;
	private IconBean   numberIcon  = null;
	private Properties properties  = new Properties(); 
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public int getDamageBoxes() {
		return damageBoxes;
	}

	public void setDamageBoxes(int damageBoxes) {
		this.damageBoxes = damageBoxes;
	}

	public void setDamageBoxes(String damageBoxes) {
		try {
			this.damageBoxes = Integer.parseInt(damageBoxes);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("damageBoxes must be an integer");
		}
	}
	
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void setArmor(String armor) {
		try {
			this.armor = Integer.parseInt(armor);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("armor must be an integer");
		}
	}

	public Arc getArc() {
		return arc;
	}
	
	public void setArc(String arc) {
		if ((arc != null) && (arc.length() > 0)) {
			Arc theArc = Arc.getArc(arc);

			if (theArc != null) {
				this.arc = theArc;
			} else {
				this.arc = Arc.createArc(arc);
			}
		} else {
			this.arc = null;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public void setPower(int power) {
		getProperties().setProperty("power", String.valueOf(power));
	}
	
	public void setWeaponType(String weaponType) {
		getProperties().setProperty("weaponType", weaponType);
	}
	
	public IconBean getSystemIcon() {
		return systemIcon;
	}

	public void setSystemIcon(IconBean systemIcon) {
		this.systemIcon = systemIcon;
	}

	public IconBean getArmorIcon() {
		return armorIcon;
	}

	public void setArmorIcon(IconBean armorIcon) {
		this.armorIcon = armorIcon;
	}

	public IconBean getPowerIcon() {
		return powerIcon;
	}

	public void setPowerIcon(IconBean powerIcon) {
		this.powerIcon = powerIcon;
	}

	public IconBean getArcIcon() {
		return arcIcon;
	}

	public void setArcIcon(IconBean arcIcon) {
		this.arcIcon = arcIcon;
	}

	public IconBean getNumberIcon() {
		return numberIcon;
	}

	public void setNumberIcon(IconBean numberIcon) {
		this.numberIcon = numberIcon;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public void addProperty(String name, String value) {
		properties.setProperty(name, value);
	}
		
}
