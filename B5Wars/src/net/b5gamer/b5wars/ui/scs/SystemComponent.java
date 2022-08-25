/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.DamageBox;
import net.b5gamer.icon.DamageableIcon;
import net.b5gamer.icon.Icon;
import net.b5gamer.icon.IconPosition;

public class SystemComponent extends IconComponent {
	
	private static final long serialVersionUID = 8765867347815680106L;

	public final List<DamageBox> markedDamageBoxes      = new ArrayList<DamageBox>();
	public final List<DamageBox> newlyMarkedDamageBoxes = new ArrayList<DamageBox>();
	
	public SystemComponent(final System system) {
		super(system);

		resize();
	}

	protected List<DamageBox> getMarkedDamageBoxes() {
		return markedDamageBoxes;
	}

	protected List<DamageBox> getNewlyMarkedDamageBoxes() {
		return newlyMarkedDamageBoxes;
	}

	public boolean toggleDamageBoxMarked(int x, int y) {
		boolean changed = false;
		List<DamageBox> damageBoxes = getIcon().getDamageBoxes();

		for (int index = damageBoxes.size(); index > 0; index--) {
			if (index > damageBoxes.size() - getSystem().getBaseDamageBoxes()) {
				DamageBox damageBox = damageBoxes.get(index - 1);
				
				if (damageBox.getShape().contains(x, y)) {
					if (getMarkedDamageBoxes().contains(damageBox)) {
						getMarkedDamageBoxes().remove(damageBox);
						getSystem().setDamageBoxes(getSystem().getDamageBoxes() + damageBox.getHits());
					} else if (getNewlyMarkedDamageBoxes().contains(damageBox)) {
						getNewlyMarkedDamageBoxes().remove(damageBox);
						getSystem().setDamageBoxes(getSystem().getDamageBoxes() + damageBox.getHits());
					} else {
						getNewlyMarkedDamageBoxes().add(damageBox);
						getSystem().setDamageBoxes(getSystem().getDamageBoxes() - damageBox.getHits());
					}
					
					changed = true;
					break;
				}
			}
		}	

		return changed;
	}
	
	@Override
	public IconPosition getIconPosition() {
		return getSystem().getIconPosition();
	}
	
	public DamageableIcon getIcon() {
		return getSystem().getIcon();
	}
	
	public void setIcon(DamageableIcon icon) throws OperationNotSupportedException {
		if (getSystem() instanceof Structure) {
			((Structure) getSystem()).setIcon(icon);
		} else {
			throw new OperationNotSupportedException("Cannot set icon for " + getSystem().getClass().getName());
		}
		
		resize();		
	}
	
	public void setIcon(int index) {
		getSystem().setIcon(index);
		
		resize();		
	}
		
	public boolean isMirrored() {
		return getIconPosition().isMirror();
	}

	public void mirror() {
		for (int i = 0; i < getSystem().getIconCount(); i++) {
			DamageableIcon icon = getSystem().getIcon(i);
			icon.mirror();
		}
		
		getIconPosition().setMirror(!getIconPosition().isMirror());
	}
	
	public double getRotation() {
		return getIconPosition().getRotation();
	}

	public void resetRotation() {
		rotate(getIconPosition().getRotation() * -1);
	}

	public void rotate(double degrees) {
		if (degrees != 0) {
			for (int i = 0; i < getSystem().getIconCount(); i++) {
				DamageableIcon icon = getSystem().getIcon(i);
				icon.rotate(degrees);
			}

			getIconPosition().setRotation(getIconPosition().getRotation() + degrees);

			resize();
		}		
	}
	
	private void resize() {
		Dimension size = new Dimension(
				(int) (getIcon().getSize().width + (2 * Icon.OFFSET_X) + 1), 
				(int) (getIcon().getSize().height + (2 * Icon.OFFSET_Y) + 1));
		setPreferredSize(size);
		setSize(size);
	}
	
	@Override
	public boolean contains(int x, int y) {
		return ((getBounds().contains(x, y)) && 
				((getIcon().getOutline() == null) || (getIcon().getOutline().contains(x - getX(), y - getY()))));
	}
	
	@Override
	public void paint(Graphics g) {
		paint((Graphics2D) g);
	}

	public void paint(Graphics2D graphics) {
		SystemIconRenderer.paint(graphics, getSystem(), getIcon(), getMarkedDamageBoxes(), getNewlyMarkedDamageBoxes());
		paintSelection(graphics);		
	}

}
