/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.io.IconDao;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.dice.Dice;
import net.b5gamer.icon.DamageableIcon;
import net.b5gamer.icon.IconPosition;
import net.b5gamer.icon.IconUtil;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A System represents any type of system on a vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class System implements Serializable {
	
	private static final long serialVersionUID = 8639214593064821955L;

	public static final Stroke OUTLINE_STROKE           = new BasicStroke(0.75f);
	public static final Color  OUTLINE_COLOR            = Color.black;
	public static final Color  OUTLINE_FILL_COLOR       = Color.white;
	public static final Font   ANNOTATION_FONT          = new Font("Arial", Font.PLAIN, 8);
	public static final Color  ANNOTATION_COLOR         = Color.black;
	public static final Color  ANNOTATION_CHANGED_COLOR = new Color(204, 0, 0);
		
	private         Section          parent = null;            // the section this system is contained within
	private   final int              baseDamageBoxes;          // base amount of damage the system can sustain before being destroyed
	private         int              turnStartDamageBoxes;     // amount of damage the system had at the beginning of the turn
	private         int              damageBoxes;              // amount of damage the system can sustain before being destroyed
	private   final int              baseArmor;                // base amount of armor protecting the system 
	private         int              armor;                    // amount of armor protecting the system 
	private         int              armorDamage = 0;          // amount of damage to armor this turn due to armor-damaging weapons
	protected       Arc              arc;                      // arc for incoming fire and/or outgoing effects
	private   final String           name;                     // name of the system
	private         boolean          destroyed = false;        // whether the system is destroyed
	private         IconPosition     iconPosition      = new IconPosition(); // position of the systems icon
	private         IconPosition     armorIconPosition = new IconPosition(); // position of the armor icon
	private         IconPosition     arcIconPosition   = new IconPosition(); // position of the arc icon
	protected transient List<String>     iconFilenames = null; // all icon filenames for the system
	protected transient DamageableIcon   icon = null;		   // icon in use for the system
	protected transient DamageableIcon[] icons = null;         // all icons available for the system
	protected transient DamageableIcon   recognitionIcon = null;
	
	/**
	 * @param damageBoxes amount of damage the system can sustain before being destroyed
	 * @param armor       amount of armor protecting the system
	 * @param arc         arc for incoming fire and/or outgoing effects, may be null
	 * @param name        name of the system, may be null
	 */
	protected System(final int damageBoxes, final int armor, final Arc arc, final String name) {
        if (damageBoxes < 0) {
            throw new IllegalArgumentException("damageBoxes cannot be a negative number");
        } 
        if (armor < 0) {
            throw new IllegalArgumentException("armor cannot be a negative number");
        } 
        
        this.baseDamageBoxes      = damageBoxes;
        this.turnStartDamageBoxes = damageBoxes;
        this.damageBoxes          = damageBoxes;
		this.baseArmor            = armor;
		this.armor                = armor;
		setArc(arc);
		this.name                 = name;
	}
		
	/**
	 * the initialisation properties for the system
	 * 
	 * @return the initialisation properties for the system
	 */
	public Properties getInitProperties() {
		return null;
	}
	
	/**
	 * the section this system is contained within
	 * 
	 * @return the section this system is contained within
	 */
	public final Section getParent() {
		return parent;
	}

	/**
	 * the section this system is contained within
	 * 
	 * @param parent the section this system is contained within
	 */
	public void setParent(final Section parent) {
		this.parent = parent;
	}
	
	/**
	 * the unit this system is contained within
	 * 
	 * @return the unit this system is contained within
	 */
	public final StructuralUnit getParentUnit() {
		return parent == null ? null : parent.getParentUnit();
	}
	
	/**
	 * the system type
	 * 
	 * @return the system type
	 */
	public abstract String getType();
	
	/**
	 * the full descriptive name of the system
	 * 
	 * @return the full descriptive name of the system
	 */
	public String getFullName() {
		return (getName() != null) ? getType() + " " + getName() : getType();
	}

	/**
	 * whether the number of damage boxes the system has is a fixed amount
	 * 
	 * @return whether the number of damage boxes the system has is a fixed amount
	 */
	public boolean isDamageBoxesFixed() {
		return false;
	}

	/**
	 * the base amount of damage the system can sustain before being destroyed
	 * 
	 * @return the base amount of damage the system can sustain before being destroyed
	 */
	public int getBaseDamageBoxes() {
		return baseDamageBoxes;
	}
	
	/**
	 * the amount of damage the system had at the beginning of the turn
	 * 
	 * @return the amount of damage the system had at the beginning of the turn
	 */
	private int getTurnStartDamageBoxes() {
		return turnStartDamageBoxes;
	}

	/**
	 * the amount of damage the system had at the beginning of the turn
	 * 
	 * @param damageBoxes the amount of damage the system had at the beginning of the turn
	 */
	private void setTurnStartDamageBoxes(final int damageBoxes) {
		this.turnStartDamageBoxes = damageBoxes;
	}

	/**
	 * whether this system has suffered damage this turn
	 * 
	 * @return whether this system has suffered damage this turn
	 */
	public boolean isDamagedThisTurn() {
		return getDamageBoxes() < getTurnStartDamageBoxes();
	}
	
	/**
	 * the amount of damage the system can sustain before being destroyed
	 * 
	 * @return the amount of damage the system can sustain before being destroyed
	 */
	public int getDamageBoxes() {
		return damageBoxes;
	}

	/**
	 * the amount of damage the system can sustain before being destroyed
	 * 
	 * @param damageBoxes the amount of damage the system can sustain before being destroyed
	 */
	public void setDamageBoxes(final int damageBoxes) {
		if (damageBoxes >= getBaseDamageBoxes()) {
			setDestroyed(false);
		}
			
		this.damageBoxes = damageBoxes < 0 ? 0 : damageBoxes;
	}
		
	/**
	 * attempt to apply a given amount of damage to the system, returning the amount of damage 
	 * that cannot be applied (overkill)
	 * 
	 * @param  damage the amount of damage to be applied if possible
	 * @return        the amount of damage that cannot be applied (overkill)
	 */
	public int applyDamage(final int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("damage cannot be less than 0");
		}

		int overkill = damage - getDamageBoxes();
		setDamageBoxes(getDamageBoxes() - damage);
		
		return (overkill < 0) ? 0 : overkill;
	}
	
	/**
	 * the amount of damage suffered by the system
	 * 
	 * @return the amount of damage suffered by the system
	 */
	public int getDamageSuffered() {
		return getBaseDamageBoxes() - getDamageBoxes();
	}
		
	/**
	 * the base amount of armor protecting the system
	 * 
	 * @return the base amount of armor protecting the system 
	 */
	public int getBaseArmor() {
		return baseArmor;
	}
	
	/**
	 * the amount of armor protecting the system
	 * 
	 * @return the amount of armor protecting the system
	 */
	public int getArmor() {
		return armor;
	}

	/**
	 * the amount of armor protecting the system
	 * 
	 * @param armor the amount of armor protecting the system
	 */
	public void setArmor(final int armor) {
		this.armor = armor < 0 ? 0 : armor;
	}

	/**
	 * the amount of damage to armor this turn due to armor-damaging weapons
	 * 
	 * @return the amount of damage to armor this turn due to armor-damaging weapons
	 */
	public int getArmorDamage() {
		return armorDamage;
	}

	/**
	 * the amount of damage to armor this turn due to armor-damaging weapons
	 * 
	 * @param armorDamage the amount of damage to armor this turn due to armor-damaging weapons
	 */
	public void setArmorDamage(final int armorDamage) {
		this.armorDamage = armorDamage < 0 ? 0 : armorDamage;
	}
		
	/**
	 * the arc for incoming fire and/or outgoing effects
	 * 
	 * @return the arc for incoming fire and/or outgoing effects
	 */
	public Arc getArc() {
		return arc;
	}

	/**
	 * the arc for incoming fire and/or outgoing effects
	 * 
	 * @param arc the arc for incoming fire and/or outgoing effects
	 */
	public void setArc(final Arc arc) {
		if ((this instanceof DirectionalSystem) && (arc == null)) {
            throw new IllegalArgumentException("arc cannot be null");
        } 
		
		this.arc = arc;
	}
	
	/**
	 * the name of the system
	 * 
	 * @return the name of the system
	 */
	public String getName() {
		return name;
	}

	/**
	 * whether the system is a valid target for incoming fire
	 * 
	 * @return whether the system is a valid target for incoming fire
	 */
	public boolean isValidTarget() {
		return (getDamageBoxes() > 0);
	}
	
	/**
	 * whether the system is destroyed
	 * 
	 * @return whether the system is destroyed
	 */
	public boolean isDestroyed() {
		return destroyed;
	}
	
	/**
	 * whether the system is destroyed
	 * 
	 * @param destroyed whether the system is destroyed
	 */
	public void setDestroyed(final boolean destroyed) {
//		if (destroyed) {
//			setDamageBoxes(0);
//		}
		
		this.destroyed = destroyed;
	}

	/**
	 * handle any actions required for the END OF TURN ACTIONS STEP 
	 */
	public void handleEndOfTurnActions() {
		if (!isDestroyed() && isDamagedThisTurn() && !(getDamageBoxes() > 0)) {
			setDestroyed(true);
		}
		
		// Critical Hit Segment
		//     determine and resolve all critical hits
		determineAndResolveCriticalHits();
		
		//    reduce armor on systems damaged by armor-damaging weapons
		if (getArmorDamage() > 0) {
			setArmor(getArmor() - getArmorDamage());
			setArmorDamage(0);
		}
		
		// Adjust Ship Systems Segment
		//     Adjust ship systems to account for damage
		adjustSystem();		
		setTurnStartDamageBoxes(getDamageBoxes());
	}

	/**
	 * determine and resolve any critical hits to the system
	 */
	protected void determineAndResolveCriticalHits() {
		if (!isDestroyed() && isDamagedThisTurn()) {
			resolveCriticalHit(Dice.d20.roll() + getDamageSuffered());
		}
	}
	
	/**
	 * resolve a critical hit to the system
	 * 
	 * @param roll result of a d20 plus appropriate modifiers 
	 */
	protected abstract void resolveCriticalHit(final int roll);

	/**
	 * perform Adjust Ship Systems Segment actions
	 */
	protected abstract void adjustSystem();
	
	/**
	 * description of the system
	 * 
	 * @return description of the system
	 */
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		
		if ((getBaseArmor() > 0) || (getBaseDamageBoxes() > 0)) {
			status.append(" (");
			
			if (getBaseArmor() > 0) {
				status.append("Armor ").append(getArmor()).append("/").append(getBaseArmor());
			}

			if (getBaseDamageBoxes() > 0) {
				if (getBaseArmor() > 0) {
					status.append(", ");
				}

				status.append("Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());				
			}
			
			status.append(")");
		}
		
		return status.toString();
	}
	
	@Override
	public String toString() {
		return getFullName();
	}

	public IconPosition getIconPosition() {
		return iconPosition;
	}

	public void setIconPosition(IconPosition iconPosition) {
		this.iconPosition = (iconPosition != null) ? iconPosition : new IconPosition();
	}

	public IconPosition getArmorIconPosition() {
		return armorIconPosition;
	}

	public void setArmorIconPosition(IconPosition armorIconPosition) {
		this.armorIconPosition = (armorIconPosition != null) ? armorIconPosition : new IconPosition();
	}

	public IconPosition getArcIconPosition() {
		return arcIconPosition;
	}

	public void setArcIconPosition(IconPosition arcIconPosition) {
		this.arcIconPosition = (arcIconPosition != null) ? arcIconPosition : new IconPosition();
	}

	protected String getIconFilenamePrefix() {
		return getType();
	}
	
	protected List<String> getIconFilenames() {
		return iconFilenames;
	}

	protected void setIconFilenames(List<String> iconFilenames) {
		this.iconFilenames = iconFilenames;
	}

	public DamageableIcon getIcon() {
		if (getIconFilenames() == null) {
			prepareIcons();
		}
		
		return icon;
	}

	protected void setIcon(DamageableIcon icon) {
		this.icon = icon;
	}

	public void setIcon(int index) {
		setIcon(getIcon(index));
		getIconPosition().setIndex(index + 1);		
	}
	
	protected DamageableIcon[] getIcons() {
		return icons;
	}

	protected void setIcons(DamageableIcon[] icons) {
		this.icons = icons;
	}
	
	protected String getRecognitionIconFilename() {
		return getType();
	}
	
	public DamageableIcon getRecognitionIcon() {
		if (recognitionIcon == null) {
			prepareRecognitionIcon();	
		}
		
		return recognitionIcon;
	}

	protected synchronized void setRecognitionIcon(DamageableIcon recognitionIcon) {
		this.recognitionIcon = recognitionIcon;
	}

	public int getIconCount() {
		if (getIconFilenames() == null) {
			prepareIcons();
		}
		
		return (getIconFilenames() != null) ? getIconFilenames().size() : 0;
	}
	
	public DamageableIcon getIcon(int index) {
		if (getIconFilenames() == null) {
			prepareIcons();
		}

		if (index >= getIconCount()) {
            throw new IndexOutOfBoundsException();
        } 
		
        DamageableIcon icon = getIcons()[index];
        if (icon == null) {
        	icon = loadIcon(index);
        }
        
		return icon;
	}
			
	protected void prepareIcons() {
		if (getIconFilenames() == null) {
			if (isDamageBoxesFixed()) {
				prepareFixedDamageIcon();
			} else {
				prepareVariableDamageIcons();
			}
		}
	}
	
	protected synchronized void prepareFixedDamageIcon() {
		String filename = getType().replace("/", " ") + IconDao.EXTENSION;
		List<String> filenames = new ArrayList<String>();
		filenames.add(filename);
		setIconFilenames(filenames);
		
		try {
			setIcons(new DamageableIcon[filenames.size()]);
			getIcons()[0] = IconUtil.createDamageableIcon(DataManager.getIconDao().load(filename), getAnnotationCount(), getBaseDamageBoxes());			
			setRecognitionIcon((DamageableIcon) getIcons()[0].clone());
			
			if (getIconPosition().isMirror()) {
				getIcons()[0].mirror();
			}
			if (getIconPosition().getRotation() != 0) {
				getIcons()[0].rotate(getIconPosition().getRotation());
			}
			setIcon(getIcons()[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected synchronized void prepareVariableDamageIcons() {
		try {
			List<String> filenames = new ArrayList<String>();
			filenames.addAll(DataManager.getIconDao().findAll(getIconFilenamePrefix().replace("&", "And") + " (", getBaseDamageBoxes()));

			setIconFilenames(filenames);
			setIcons(new DamageableIcon[filenames.size()]);
			if (filenames.size() > 0) {
				if (getIconPosition().getIndex() <= filenames.size()) {
					setIcon(getIconPosition().getIndex() - 1);
				} else {
					setIcon(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected DamageableIcon loadIcon(int index) {
		try {
			String filename    = getIconFilenames().get(index);
			int    damageBoxes = Integer.parseInt(filename.substring(filename.lastIndexOf("(") + 1, filename.indexOf(")", filename.lastIndexOf("("))));
			getIcons()[index]  = IconUtil.createDamageableIcon(DataManager.getIconDao().load(filename), getAnnotationCount(), damageBoxes);
			
			if (getIconPosition().isMirror()) {
				getIcons()[index].mirror();
			}
			if (getIconPosition().getRotation() != 0) {
				getIcons()[index].rotate(getIconPosition().getRotation());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return getIcons()[index];		
	}
	
	protected void prepareRecognitionIcon() {
		if (isDamageBoxesFixed()) {
			prepareFixedDamageIcon();				
		} else {
			try {
				setRecognitionIcon(IconUtil.createDamageableIcon(DataManager.getIconDao().load(getRecognitionIconFilename().replace("&", "And") + IconDao.EXTENSION), 0, 0));
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}
	
	public Stroke getOutlineStroke() {
		return OUTLINE_STROKE;
	}

	public Color getOutlineColor() {
		return OUTLINE_COLOR;
	}
	
	public Color getOutlineFillColor() {
		return OUTLINE_FILL_COLOR;
	}
		
	public int getAnnotationCount() {
		return 0;
	}
	
	public String getAnnotation(int index) {
		return null;
	}
	
	public Font getAnnotationFont(int index) {
		return ANNOTATION_FONT;
	}
	
	public Color getAnnotationColor(int index) {
		return ANNOTATION_COLOR;
	}
	
	public int getRecognitionOrder() {
		return 0;
	}
	
}
