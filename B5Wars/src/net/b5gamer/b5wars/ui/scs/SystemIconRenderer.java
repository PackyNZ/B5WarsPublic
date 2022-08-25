/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.awt.geom.GeneralShape;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.icon.DamageBox;
import net.b5gamer.icon.DamageableIcon;
import net.b5gamer.icon.Icon;
import net.b5gamer.icon.MegaBox;

public class SystemIconRenderer implements javax.swing.Icon {
	
	public static final Color  DESTROYED_OUTLINE_COLOR = new Color(221, 0, 0);
	public static final Stroke DAMAGE_BOX_STROKE       = new BasicStroke(1.5f);
	public static final Color  DAMAGE_BOX_COLOR        = Color.black;
	public static final Color  DAMAGE_BOX_FILL_COLOR   = Color.white;
	public static final Color  DAMAGED_BOX_COLOR       = Color.black;
	public static final Color  DAMAGED_BOX_FILL_COLOR  = new Color(238, 0, 0);;
	public static final Stroke CROSSED_BOX_STROKE      = new BasicStroke(1.0f);
	public static final Color  CROSSED_BOX_COLOR       = new Color(238, 0, 0);;
	public static final Font   MEGA_BOX_FONT           = new Font("Arial", Font.PLAIN, 5);
	
	protected System system;
	protected Icon   icon;
	
	public SystemIconRenderer(final System system) {
		this(system, system.getIcon());
	}

	public SystemIconRenderer(final Icon icon) {
		this(null, icon);
	}
	
	public SystemIconRenderer(final System system, final Icon icon) {
		this.system = system;
		setIcon(icon);
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
		setIcon(system.getIcon());
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		if (icon == null) {
            throw new IllegalArgumentException("icon cannot be null");
        } 
		
		this.icon = icon;
	}
	
	public int getIconHeight() {
		return (int) (getIcon().getSize().height + (2 * Icon.OFFSET_Y) + 1);
	}

	public int getIconWidth() {
		return (int) (getIcon().getSize().width + (2 * Icon.OFFSET_X) + 1);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D graphics = (Graphics2D) g;
		AffineTransform transform = graphics.getTransform();
		graphics.transform(AffineTransform.getTranslateInstance(x, y));
		paint(graphics, getSystem(), getIcon());
		graphics.setTransform(transform);
	}

	public static void paint(Graphics2D graphics, Icon icon) {
		paint(graphics, null, icon);
	}
	
	public static void paint(Graphics2D graphics, System system, Icon icon) {
		paint(graphics, system, icon, null, null);
	}
	
	public static void paint(Graphics2D graphics, System system, Icon icon, List<DamageBox> markedDamageBoxes,
			List<DamageBox> newlyMarkedDamageBoxes) {
		if (graphics == null) {
            throw new IllegalArgumentException("graphics cannot be null");
        } 
		if (icon == null) {
            throw new IllegalArgumentException("icon cannot be null");
        } 

		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// paint outline
		if (icon.getOutlines() != null) {
			graphics.setStroke((system != null) ? system.getOutlineStroke() : System.OUTLINE_STROKE);

			for (Iterator<GeneralShape> iterator = icon.getOutlines().iterator(); iterator.hasNext();) {
				GeneralShape outline = iterator.next();
				
				// fill outline
	//			if (outline.isEnclosed()) {
					graphics.setColor((system != null) ? system.getOutlineFillColor() : System.OUTLINE_FILL_COLOR);
					graphics.fill(outline);
	//			}
				
				// draw outline
				graphics.setColor((system != null) ? (system.isDestroyed()) ? DESTROYED_OUTLINE_COLOR : system.getOutlineColor() : System.OUTLINE_COLOR);
				graphics.draw(outline);
			}
		}
		
		// paint damage boxes
		if (icon instanceof DamageableIcon) {
			graphics.setFont(MEGA_BOX_FONT);
			FontMetrics fontMetrics = null;

			int totalDamage  = ((DamageableIcon) icon).getTotalHits();
			int damageToSkip = (system == null) ? 0 : totalDamage - system.getBaseDamageBoxes();
			int damageToMark = (system == null) ? 0 : system.getBaseDamageBoxes() - system.getDamageBoxes();
			if (markedDamageBoxes != null) {
				for (Iterator<DamageBox> iterator = markedDamageBoxes.iterator(); iterator.hasNext();) {
					damageToMark -= iterator.next().getHits();
				}
			}
			if (newlyMarkedDamageBoxes != null) {
				for (Iterator<DamageBox> iterator = newlyMarkedDamageBoxes.iterator(); iterator.hasNext();) {
					damageToMark -= iterator.next().getHits();
				}
			}
			
			for (Iterator<DamageBox> iterator = ((DamageableIcon) icon).getDamageBoxes().iterator(); iterator.hasNext();) {
	        	DamageBox damageBox = iterator.next();

	        	if (damageBox.getHits() <= damageToSkip) {
	        		damageToSkip -= damageBox.getHits();
				} else {
					boolean mark = false;
					boolean cross = false;
					if ((markedDamageBoxes != null) && (markedDamageBoxes.contains(damageBox))) {						
		        		mark = true;
					} else if ((newlyMarkedDamageBoxes != null) && (newlyMarkedDamageBoxes.contains(damageBox))) {						
						cross = true;
					} else {
			        	if (damageBox.getHits() <= damageToMark) {
			        		damageToMark -= damageBox.getHits();
			        		mark = true;
			        	}
					}
					
					// mark damage box
					graphics.setColor((mark) ? DAMAGED_BOX_FILL_COLOR : DAMAGE_BOX_FILL_COLOR);
					graphics.fill(damageBox.getShape());
					
					// cross damage box
					if (cross) {
						graphics.setStroke(CROSSED_BOX_STROKE);
						graphics.setColor(CROSSED_BOX_COLOR);
						
						Point2D[] points = damageBox.getPoints();
						graphics.draw(new Line2D.Double(points[0], points[2]));
						graphics.draw(new Line2D.Double(points[1], points[3]));
					}
					
					// draw damage box
					graphics.setStroke(DAMAGE_BOX_STROKE);
					graphics.setColor((mark || cross) ? DAMAGED_BOX_COLOR : DAMAGE_BOX_COLOR);
					graphics.draw(damageBox.getShape());
					
					// draw megabox text 
					// TODO rotate
					if (damageBox instanceof MegaBox) {
						if (fontMetrics == null) {
							fontMetrics = graphics.getFontMetrics(MEGA_BOX_FONT);
						}
						
				        Rectangle2D textBounds = fontMetrics.getStringBounds(String.valueOf(damageBox.getHits()), graphics);
						graphics.drawString(String.valueOf(damageBox.getHits()), 
								(float) (damageBox.getShape().getBounds2D().getX() + 1.0),
								(float) (damageBox.getShape().getBounds2D().getY() + textBounds.getHeight()));	
					}
				}
			}
		}
		
		// paint annotations
		if ((system != null) && (system.getAnnotationCount() > 0)) {
			for (int index = 0; index < system.getAnnotationCount(); index++) {
				Point2D point = icon.getAnnotationPoint(index);
			
				if (point != null) {
					graphics.setFont(system.getAnnotationFont(index));
					graphics.setColor(system.getAnnotationColor(index));
					
					FontMetrics fontMetrics      = graphics.getFontMetrics(system.getAnnotationFont(index));
			        Rectangle2D annotationBounds = fontMetrics.getStringBounds(system.getAnnotation(index), graphics);
					graphics.drawString(system.getAnnotation(index), 
							(float) (point.getX() - annotationBounds.getWidth() / 2.0),
							(float) (point.getY() + annotationBounds.getHeight() / 2.0 - 1.5));
				}
			}
		}
	}
	
}
