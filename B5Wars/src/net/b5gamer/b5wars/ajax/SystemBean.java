package net.b5gamer.b5wars.ajax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.awt.geom.GeneralShape;
import net.b5gamer.awt.geom.ShapeUtil;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.DamageBox;
import net.b5gamer.icon.IconPosition;
import net.b5gamer.map.Arc;

public class SystemBean {

	private String           type              = null;
	private String           name              = null;
	private MutableValueBean damageBoxes       = null;       
	private boolean          destroyed         = false;
	private MutableValueBean armor             = null;          
	private Arc              arc               = null;
	private List<String>     iconOutlines      = null;
	private List<String>     iconDamageBoxes   = null;
	private IconPosition     iconPosition      = null;
	private IconPosition     armorIconPosition = null;
	private String           arcIconOutline    = null;
	private IconPosition     arcIconPosition   = null;
	
	public SystemBean() {
	}

	public SystemBean(final System system) {
		this.type        = system.getType();
		this.name        = system.getName();
		this.damageBoxes = new MutableValueBean(system.getBaseDamageBoxes(), system.getDamageBoxes());
		this.destroyed   = system.isDestroyed();
		this.armor       = new MutableValueBean(system.getBaseArmor(), system.getArmor());
		this.arc         = system.getArc();
		
		if (system.getIcon() != null) {
			if (system.getIcon().getOutlines() != null) {
				this.iconOutlines = new ArrayList<String>();
	
				for (Iterator<GeneralShape> iterator = system.getIcon().getOutlines().iterator(); iterator.hasNext();) {
					this.iconOutlines.add(ShapeUtil.toSVG(iterator.next()));
				}
			}
			
			if (system.getIcon().getDamageBoxes() != null) {
				this.iconDamageBoxes = new ArrayList<String>();

				for (Iterator<DamageBox> iterator = system.getIcon().getDamageBoxes().iterator(); iterator.hasNext();) {
					DamageBox damageBox = iterator.next();
					
					if (damageBox.getShape() != null) {
						this.iconDamageBoxes.add(ShapeUtil.toSVG(damageBox.getShape()));
					}
				}
			}
		}
		
		this.iconPosition      = system.getIconPosition();
		this.armorIconPosition = system.getArmorIconPosition();
		this.arcIconOutline    = (system.getArc() != null) ? ShapeUtil.toSVG(system.getArc().toShape(16, 16)) : null;
		this.arcIconPosition   = system.getArcIconPosition();
	}

	public final String getType() {
		return type;
	}

	public final void setType(final String type) {
		this.type = type;
	}

	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final MutableValueBean getDamageBoxes() {
		return damageBoxes;
	}

	public final void setDamageBoxes(final MutableValueBean damageBoxes) {
		this.damageBoxes = damageBoxes;
	}

	public final boolean isDestroyed() {
		return destroyed;
	}

	public final void setDestroyed(final boolean destroyed) {
		this.destroyed = destroyed;
	}

	public final MutableValueBean getArmor() {
		return armor;
	}

	public final void setArmor(final MutableValueBean armor) {
		this.armor = armor;
	}

	public final Arc getArc() {
		return arc;
	}

	public final void setArc(final Arc arc) {
		this.arc = arc;
	}

	public final List<String> getIconOutlines() {
		return iconOutlines;
	}

	public final void setIconOutlines(final List<String> iconOutlines) {
		this.iconOutlines = iconOutlines;
	}

	public final List<String> getIconDamageBoxes() {
		return iconDamageBoxes;
	}

	public final void setIconDamageBoxes(final List<String> iconDamageBoxes) {
		this.iconDamageBoxes = iconDamageBoxes;
	}

	public final IconPosition getIconPosition() {
		return iconPosition;
	}

	public final void setIconPosition(final IconPosition iconPosition) {
		this.iconPosition = iconPosition;
	}

	public final IconPosition getArmorIconPosition() {
		return armorIconPosition;
	}

	public final void setArmorIconPosition(final IconPosition armorIconPosition) {
		this.armorIconPosition = armorIconPosition;
	}

	public final String getArcIconOutline() {
		return arcIconOutline;
	}

	public final void setArcIconOutline(final String arcIconOutline) {
		this.arcIconOutline = arcIconOutline;
	}

	public final IconPosition getArcIconPosition() {
		return arcIconPosition;
	}

	public final void setArcIconPosition(final IconPosition arcIconPosition) {
		this.arcIconPosition = arcIconPosition;
	}

}
