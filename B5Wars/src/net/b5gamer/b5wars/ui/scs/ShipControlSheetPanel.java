/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Popup;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.SectionCollection;
import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.icon.DamageableIcon;
import net.b5gamer.icon.DamageableIconEditorDialog;
import net.b5gamer.swing.PartialSVGDocumentComponent;
import net.b5gamer.swing.SVGDocumentComponent;

public class ShipControlSheetPanel extends ControlSheetPanel implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = -5955598059272304547L;

	private static final String ACTION_REMOVE        = "Remove";
	private static final String ACTION_EDIT          = "Edit";
	private static final String ACTION_ICON          = "Icon";
	private static final String ACTION_MIRROR        = "Mirror";
	private static final String ACTION_ROTATE_RESET  = "To 0�";
	private static final String ACTION_ROTATE_45_CW  = "45� CW";
	private static final String ACTION_ROTATE_60_CW  = "60� CW";
	private static final String ACTION_ROTATE_90_CW  = "90� CW";
	private static final String ACTION_ROTATE_180    = "180�";
	private static final String ACTION_ROTATE_45_CCW = "45� CCW";
	private static final String ACTION_ROTATE_60_CCW = "60� CCW";
	private static final String ACTION_ROTATE_90_CCW = "90� CCW";
		
	private static final Font B5GAMER_FONT = new Font("Arial", Font.PLAIN, 4);

	private GlyphVector                b5GamerUrl        = null;
	private SVGDocumentComponent       silhouette;
	private List<IconComponent>        icons;
	private JMenuItem                  editMenu          = null;
	private JMenu                      iconMenu          = null;
	private JMenuItem                  mirrorMenuItem    = null;
	private IconListPanel              unitIconListPanel = null;
	private DamageableIconEditorDialog iconEditorDialog  = null; 
	private Map<String, JComponent>    weaponData        = null;
	private Popup                      weaponDataPopup   = null;
	private double                     iconX             = 0;
	private double                     iconY             = 0;
	private int                        minIconY;
	private boolean                    draggingIcon      = false;
	private boolean                    markingDamage     = false;
	private IconComponent              selectedIcon      = null;
	
	public ShipControlSheetPanel(final StructuralUnit unit) {
		this(unit, null);
	}
	
	public ShipControlSheetPanel(final StructuralUnit unit, final UsageMode mode) {
		super(unit, mode);
	}

    public StructuralUnit getUnit() {
		return (StructuralUnit) super.getUnit();
	}

    public void setUnit(final Unit unit) {
	    if ((unit != null) && !(unit instanceof StructuralUnit)) {
    		throw new IllegalArgumentException("unit must be a " + StructuralUnit.class.getSimpleName());
    	}

		super.setUnit(unit);
		
	    getControlSheetComponents().clear();
		
	    if (getUnit() != null) {
			DatacardComponent datacard = new DatacardComponent(getUnit());
			getControlSheetComponents().add(datacard);
			setMinIconY(datacard.getY() + datacard.getHeight());
			
			getControlSheetComponents().add(new HitLocationChartComponent(getUnit()));
	        
	        SpecialNotesComponent specialNotes = new SpecialNotesComponent(getUnit());
	        int noteCount = specialNotes.getNoteCount();
	    	if (noteCount > 0) {
	    		getControlSheetComponents().add(specialNotes);
	    	}
	
	    	SensorDataComponent sensorData = new SensorDataComponent();
	    	if (noteCount > 0) {
	    		sensorData.setLocation(sensorData.getX(), sensorData.getY() + specialNotes.getHeight() + 3);	
	    	}
	    	getControlSheetComponents().add(sensorData);
	
	    	getControlSheetComponents().add(new IconRecognitionComponent(getUnit()));
	    	
	    	// weapon data
	    	List<Weapon> weapons = getUnit().getWeaponsOfClass(Weapon.class, true);
	    	if (weapons.size() > 0) {
	    		Collections.sort(weapons, new WeaponDataComparator());
	    		
	    		WeaponDataComponent weaponDataHeading = new WeaponDataComponent();
	    		int x = weaponDataHeading.getX();
	    		int y = weaponDataHeading.getY() + weaponDataHeading.getHeight();
	    		
	    		Map<String, JComponent> weaponData = new HashMap<String, JComponent>();
	    	
	    		for (Iterator<Weapon> iterator = weapons.iterator(); iterator.hasNext();) {
	    			Weapon weapon = iterator.next();
	    			
	    			if (!weaponData.containsKey(weapon.getType())) {
						try {
							PartialSVGDocumentComponent component = new PartialSVGDocumentComponent(DataManager.getWeaponDataDao().load(weapon.getType().replace("/", " ")));
							component.setLocation(x, y);
							y += component.getHeight();
							
							weaponData.put(weapon.getType(), component);
							getControlSheetComponents().add(component);
						} catch (Exception e) {
							e.printStackTrace();
						}
	    			}
	    		}	   
	    		
	    		getControlSheetComponents().add(weaponDataHeading);	    		
	    	}
	    	
	    	try {
		    	setSilhouette(new SVGDocumentComponent(DataManager.getSilhouetteDao().load(getUnit().getHull())));
	    	} catch (Exception e) {
	    		setSilhouette(null);
	    		e.printStackTrace();
			}		
	    	
	    	// setup icons
	    	setIcons(new ArrayList<IconComponent>(0));
		    for (Iterator<Section> iterator = getUnit().getSectionIterator(); iterator.hasNext();) {
		    	setupSystemIcons(iterator.next());
		    }    	
	    }
	    
	    repaint();
    }	
	
	protected void setupSystemIcons(Section section) {
	    for (Iterator<System> iterator = section.getSystemIterator(); iterator.hasNext();) {
	    	System system = iterator.next();
	    	
//			system.applyDamage(new Random().nextInt(system.getDamageBoxes()) + 1);
	    	
	    	if ((system.getIcon() != null) &&
	    			(system.getIconPosition().getX() != 0) && (system.getIconPosition().getY() != 0)) {
	    		addIcon(new SystemComponent(system));
	    	}
	    	
	    	if ((system.getArmorIconPosition().getX() != 0) && (system.getArmorIconPosition().getY() != 0)) {
	    		addIcon(new ArmorComponent(system));
	    	}
	    	
	    	if ((system instanceof PoweredSystem) && 
	    			(((PoweredSystem) system).getPowerIconPosition().getX() != 0) && 
	    			(((PoweredSystem) system).getPowerIconPosition().getY() != 0)) {
	    		addIcon(new PowerComponent((PoweredSystem) system));		    		
	    	}
	    	
	    	if ((system.getArc() != null) && (system.getArcIconPosition().getX() != 0) && 
	    			(system.getArcIconPosition().getY() != 0)) {
	    		addIcon(new ArcComponent(system));
	    	}
	    	
	    	if ((system instanceof Weapon) && (system.getName() != null) && 
	    			(((Weapon) system).getWeaponNumberPosition().getX() != 0) && 
	    			(((Weapon) system).getWeaponNumberPosition().getY() != 0)) {
	    		addIcon(new WeaponNumberComponent((Weapon) system));		    		
	    	}
	    }		
	    
		if (section instanceof SectionCollection) {
            for (Iterator<Section> iterator = ((SectionCollection) section).getSectionIterator(); iterator.hasNext();) {
            	setupSystemIcons(iterator.next());
    		}
		}    
	}
	
    public synchronized void setUsageMode(final UsageMode mode) {
    	super.setUsageMode(mode);
		
		setPopupMenus();
	}
    
    protected GlyphVector getB5GamerUrl() {
		return b5GamerUrl;
	}

	private void setB5GamerUrl(GlyphVector b5GamerUrl) {
		this.b5GamerUrl = b5GamerUrl;
	}

	protected final SVGDocumentComponent getSilhouette() {
		return silhouette;
	}

    private final void setSilhouette(final SVGDocumentComponent silhouette) {
		this.silhouette = silhouette;
	}

	protected final List<IconComponent> getIcons() {
    	return icons;
    }
    
	private final void setIcons(List<IconComponent> icons) {
		this.icons = icons;
	}

	protected final void addIcon(IconComponent icon) {
        if (icon == null) {
            throw new IllegalArgumentException("icon cannot be null");
        }
      
        if (!getIcons().contains(icon)) {
        	if ((icon.getLocation().x == 0) && (icon.getLocation().y == 0)) {
        		Point center = convertCoordsFromScreenToControlSheet(getWidth() / 2, getHeight() / 2);
        		center.x = center.x - icon.getWidth() / 2;
        		center.y = center.y - icon.getHeight() / 2;
        		
        		icon.setLocation(validateIconLocation(center, icon.getWidth(), icon.getHeight()));
        	}
        	
        	icon.setDeployed(true);
        	getIcons().add(icon);
        	setSelectedIcon(icon);
        	repaint();				
        }
	}
	
	protected final void removeIcon(IconComponent icon) {
        if (icon == null) {
            throw new IllegalArgumentException("icon cannot be null");
        }

		getIcons().remove(icon);
		icon.setDeployed(false);					
	}
	
	@Override
    protected JPopupMenu getPopupMenu() {
    	if (getSelectedIcon() != null) {
    		JPopupMenu popupMenu = getPopupMenus().get(getSelectedIcon().getClass());
    		
    		// setup dynamic menu items
    		if (getSelectedIcon() instanceof SystemComponent) {
        		// remove Edit menu 
        		if (getEditMenu() != null) {
        			popupMenu.remove(getEditMenu());
	    			setEditMenu(null);
	    		}
        		// remove Icon menu 
        		if (getIconMenu() != null) {
        			popupMenu.remove(getIconMenu());
	    			setIconMenu(null);
	    		}
	
        		// disable mirror menu if icon is rotated
    			if ((getUsageMode() == UsageMode.ALL) || (getUsageMode() == UsageMode.EDIT)) {
    				for (int index = 0; index < popupMenu.getComponents().length; index++) {
    					Component component = popupMenu.getComponent(index);
    					
    					if ((component instanceof JMenuItem) && 
    							(((JMenuItem) component).getActionCommand().equals(ACTION_MIRROR))) {
    						((JMenuItem) component).setEnabled(getSelectedIcon().getIconPosition().getRotation() == 0);
    						break;
    					}
    				}
	    		}	    		            		        		
        		
	    		// add edit menu
	    		if (((getUsageMode() == UsageMode.ALL) || (getUsageMode() == UsageMode.EDIT)) && 
	    				(getSelectedIcon().getSystem() instanceof Structure)) {
	    			JMenuItem menuEdit = new JMenuItem(ACTION_EDIT);
	    			menuEdit.setActionCommand(ACTION_EDIT);
	    			menuEdit.addActionListener(this);
	
	        		setEditMenu(menuEdit);
	        		popupMenu.add(menuEdit, 2);
	    		}	    		            		
        		
	    		// add icon menu
	    		if (((getUsageMode() == UsageMode.ALL) || (getUsageMode() == UsageMode.EDIT)) && 
	    				(getSelectedIcon().getSystem().getIconCount() > 1)) {
	        		JMenu iconMenu = new JMenu(ACTION_ICON);
	
	        		for (int index = 0; index < getSelectedIcon().getSystem().getIconCount(); index++) {
	            		JMenuItem actionIcon = new JMenuItem(
	            				new SystemIconRenderer(getSelectedIcon().getSystem(), getSelectedIcon().getSystem().getIcon(index)));
	            		actionIcon.setActionCommand(ACTION_ICON + " " + index);
	            		actionIcon.addActionListener(this);
	            		iconMenu.add(actionIcon);            			
	        		}
	
	        		setIconMenu(iconMenu);
	        		popupMenu.add(iconMenu, 2);
	    		}	    		
    		}

        	return popupMenu;
    	} else {
    		return super.getPopupMenu();
    	}
	}
        
	@Override
	protected void setPopupMenus() {
		super.setPopupMenus();
		
		// create menu items
		JMenuItem menuRemove1 = new JMenuItem(ACTION_REMOVE);
		menuRemove1.setActionCommand(ACTION_REMOVE);
		menuRemove1.addActionListener(this);

		JMenuItem menuRemove2 = new JMenuItem(ACTION_REMOVE);
		menuRemove2.setActionCommand(ACTION_REMOVE);
		menuRemove2.addActionListener(this);
		
		JMenuItem menuMirror = new JMenuItem(ACTION_MIRROR);
		menuMirror.setActionCommand(ACTION_MIRROR);
		menuMirror.addActionListener(this);
		setMirrorMenuItem(menuMirror);
	
	    JMenu menuRotate = new JMenu("Rotate");
		JMenuItem actionRotateReset = new JMenuItem(ACTION_ROTATE_RESET);
		actionRotateReset.setActionCommand(ACTION_ROTATE_RESET);
		actionRotateReset.addActionListener(this);
	    menuRotate.add(actionRotateReset);
	    menuRotate.addSeparator();
		JMenuItem actionRotate45CW = new JMenuItem(ACTION_ROTATE_45_CW);
		actionRotate45CW.setActionCommand(ACTION_ROTATE_45_CW);
		actionRotate45CW.addActionListener(this);
	    menuRotate.add(actionRotate45CW);
		JMenuItem actionRotate60CW = new JMenuItem(ACTION_ROTATE_60_CW);
		actionRotate60CW.setActionCommand(ACTION_ROTATE_60_CW);
		actionRotate60CW.addActionListener(this);
	    menuRotate.add(actionRotate60CW);
		JMenuItem actionRotate90CW = new JMenuItem(ACTION_ROTATE_90_CW);
		actionRotate90CW.setActionCommand(ACTION_ROTATE_90_CW);
		actionRotate90CW.addActionListener(this);
	    menuRotate.add(actionRotate90CW);
	    menuRotate.addSeparator();
		JMenuItem actionRotate180 = new JMenuItem(ACTION_ROTATE_180);
		actionRotate180.setActionCommand(ACTION_ROTATE_180);
		actionRotate180.addActionListener(this);
	    menuRotate.add(actionRotate180);
	    menuRotate.addSeparator();
		JMenuItem actionRotate45CCW = new JMenuItem(ACTION_ROTATE_45_CCW);
		actionRotate45CCW.setActionCommand(ACTION_ROTATE_45_CCW);
		actionRotate45CCW.addActionListener(this);
	    menuRotate.add(actionRotate45CCW);
		JMenuItem actionRotate60CCW = new JMenuItem(ACTION_ROTATE_60_CCW);
		actionRotate60CCW.setActionCommand(ACTION_ROTATE_60_CCW);
		actionRotate60CCW.addActionListener(this);
	    menuRotate.add(actionRotate60CCW);
		JMenuItem actionRotate90CCW = new JMenuItem(ACTION_ROTATE_90_CCW);
		actionRotate90CCW.setActionCommand(ACTION_ROTATE_90_CCW);
		actionRotate90CCW.addActionListener(this);
	    menuRotate.add(actionRotate90CCW);
	    	    
		// create menus
		if (getUsageMode() == UsageMode.ALL) {
			JPopupMenu systemPopupMenu = new JPopupMenu();
			systemPopupMenu.add(menuRemove1);	
			systemPopupMenu.addSeparator();
			systemPopupMenu.add(menuMirror);
			systemPopupMenu.add(menuRotate);			
		    getPopupMenus().put(SystemComponent.class, systemPopupMenu);

			JPopupMenu iconPopupMenu = new JPopupMenu();
			iconPopupMenu.add(menuRemove2);	
			getPopupMenus().put(ArmorComponent.class,        iconPopupMenu);
			getPopupMenus().put(PowerComponent.class,        iconPopupMenu);
			getPopupMenus().put(ArcComponent.class,          iconPopupMenu);
			getPopupMenus().put(WeaponNumberComponent.class, iconPopupMenu);
		} else if (getUsageMode() == UsageMode.EDIT) {
			JPopupMenu systemPopupMenu = new JPopupMenu();
			systemPopupMenu.add(menuRemove1);			
			systemPopupMenu.addSeparator();
			systemPopupMenu.add(menuMirror);
			systemPopupMenu.add(menuRotate);
		    getPopupMenus().put(SystemComponent.class, systemPopupMenu);
			
			JPopupMenu iconPopupMenu = new JPopupMenu();
			iconPopupMenu.add(menuRemove2);	
			getPopupMenus().put(ArmorComponent.class,        iconPopupMenu);
			getPopupMenus().put(PowerComponent.class,        iconPopupMenu);
			getPopupMenus().put(ArcComponent.class,          iconPopupMenu);
			getPopupMenus().put(WeaponNumberComponent.class, iconPopupMenu);			
		} else if (getUsageMode() == UsageMode.VIEW) {
		} else if (getUsageMode() == UsageMode.PLAY) {
		}
	}    
    
	protected final JMenuItem getEditMenu() {
		return editMenu;
	}

	private final void setEditMenu(final JMenuItem editMenu) {
		this.editMenu = editMenu;
	}

	protected final JMenu getIconMenu() {
		return iconMenu;
	}

	private final void setIconMenu(final JMenu iconMenu) {
		this.iconMenu = iconMenu;
	}
	
	protected JMenuItem getMirrorMenuItem() {
		return mirrorMenuItem;
	}

	private void setMirrorMenuItem(JMenuItem mirrorMenuItem) {
		this.mirrorMenuItem = mirrorMenuItem;
	}

	public final IconListPanel getUnitIconListPanel() {
		return unitIconListPanel;
	}

	public final void setUnitIconListPanel(final IconListPanel unitIconListPanel) {
		this.unitIconListPanel = unitIconListPanel;
	}    
    
	protected final DamageableIconEditorDialog getIconEditorDialog() {
		return iconEditorDialog;
	}

	private final void setIconEditorDialog(final DamageableIconEditorDialog iconEditorDialog) {
		this.iconEditorDialog = iconEditorDialog;
	}
	
	protected Map<String, JComponent> getWeaponData() {
		return weaponData;
	}

	private void setWeaponData(Map<String, JComponent> weaponData) {
		this.weaponData = weaponData;
	}

	private Popup getWeaponDataPopup() {
		return weaponDataPopup;
	}

	private void setWeaponDataPopup(Popup weaponDataPopup) {
		this.weaponDataPopup = weaponDataPopup;
	}

	protected final double getIconX() {
		return iconX;
	}

	protected final void setIconX(final double iconX) {
		this.iconX = iconX;
	}

	protected final double getIconY() {
		return iconY;
	}

	protected final void setIconY(final double iconY) {
		this.iconY = iconY;
	}

	protected final int getMinIconY() {
		return minIconY;
	}
	
	private final void setMinIconY(final int minIconY) {
		this.minIconY = minIconY;
	}

	protected final boolean isDraggingIcon() {
		return draggingIcon;
	}

	private final void setDraggingIcon(final boolean draggingIcon) {
		this.draggingIcon = draggingIcon;
	}

	protected boolean isMarkingDamage() {
		return markingDamage;
	}

	private void setMarkingDamage(boolean markingDamage) {
		this.markingDamage = markingDamage;
	}

	protected final IconComponent getSelectedIcon() {
		return selectedIcon;
	}

	protected final void setSelectedIcon(final IconComponent icon) {
		if (getSelectedIcon() != null) {
			getSelectedIcon().setSelected(false);			
		}
		
		this.selectedIcon = icon;
		
		if (getSelectedIcon() != null) {
			if ((getUsageMode() == UsageMode.EDIT) || (getUsageMode() == UsageMode.ALL)) {
				getSelectedIcon().setSelected(true);
			}
			
			notifyStatusListeners("Selected", getSelectedIcon().toString());
			notifyStatusListeners("Position", getSelectedIcon().getX() + "," + getSelectedIcon().getY());
		} else {
			notifyStatusListeners("Selected", "");
			notifyStatusListeners("Position", "");
		}
	}	
	
	protected final IconComponent getIconAtPoint(final Point point) {
		if (getIcons() != null) {
			for (int index = getIcons().size() - 1; index >= 0; index--) {
				IconComponent icon = getIcons().get(index);
	
				if (icon.contains(point)) {
					return icon;
				}
			}
		}
		
		return null;
	}
	
	protected final Point validateIconLocation(Point point, double width, double height) {
		return validateIconLocation(point.getX(), point.getY(), width, height);
	}

	protected final Point validateIconLocation(double x, double y, double width, double height) {
		x = (x < 0) ? 0 : x;
		x = (x + width > SCS_WIDTH) ? SCS_WIDTH - width : x;

		y = (y < getMinIconY()) ? getMinIconY() : y;
		y = (y + height > SCS_HEIGHT) ? SCS_HEIGHT - height : y;

		Point point =  new Point();
		point.setLocation(x, y);

		return point;
	}
	
	@Override
	public void paintControlSheet(Graphics2D graphics, boolean exporting) {
		if (isSetupComponents()) {
			setupComponents(graphics);
		}
		
		if (getUnit() != null) {
			if (getB5GamerUrl() == null) {
				FontRenderContext fontRenderContext = graphics.getFontRenderContext();
				setB5GamerUrl(B5GAMER_FONT.createGlyphVector(fontRenderContext, "http://www.b5gamer.net/B5Wars"));
			}
			
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, (int) getSCSWidth(), (int) getSCSHeight());		
			
			if (!(exporting || isShowData())) {
				graphics.transform(AffineTransform.getTranslateInstance(0, SCS_HEADER * -1));			
			}		
			
			paint(graphics, getSilhouette());
			
			IconComponent selectedIcon = null;
			if (exporting) {
				selectedIcon = getSelectedIcon();
				setSelectedIcon(null);
			}
			
			if (getIcons() != null) {
				for (Iterator<IconComponent> iterator = getIcons().iterator(); iterator.hasNext();) {
					paint(graphics, iterator.next());
				}		
			}
			
			if (exporting || isShowData()) {
				if ((getUnit().isOfficial()) && (getLogo() != null)) {
					paint(graphics, getLogo());
				}
	
				for (Iterator<JComponent> iterator = getControlSheetComponents().iterator(); iterator.hasNext();) {
					paint(graphics, iterator.next());
				}
				
				if (getB5GamerUrl() != null) {
					graphics.drawGlyphVector(getB5GamerUrl(), 
							(float) (18 + (86 - getB5GamerUrl().getLogicalBounds().getWidth()) / 2), 
							(float) (SCS_HEIGHT - 17 + getB5GamerUrl().getLogicalBounds().getHeight()));
				}				
				
				if ((getUnit().isOfficial()) && (getCopyrightLine1() != null) && (getCopyrightLine2() != null)) {
					graphics.drawGlyphVector(getCopyrightLine1(), 
							(float) (SCS_WIDTH - 17 - getCopyrightLine1().getLogicalBounds().getWidth()), 
							(float) (SCS_HEIGHT - 13 - getCopyrightLine2().getLogicalBounds().getHeight()));					
					graphics.drawGlyphVector(getCopyrightLine2(), 
							(float) (SCS_WIDTH - 17 - getCopyrightLine2().getLogicalBounds().getWidth()), 
							(float) (SCS_HEIGHT - 13));					
				}
			}
			
			if (exporting) {
				setSelectedIcon(selectedIcon);
			}		
		}
	}
	
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		if ((e.getButton() == MouseEvent.BUTTON1) && (getMode() == UsageMode.MANUAL)) {
//			Point point = convertCoordsFromScreenToControlSheet(e.getX(), e.getY());
//			SCSIconComponent icon = getIconAtPoint(point);
//			
//			if ((icon != null) && (icon instanceof SystemComponent)) {
//				if (((SystemComponent) icon).toggleDamageBoxMarked(point.x - icon.getX(), point.y - icon.getY())) {
//					repaint();
//				}
//			} else {
//				super.mouseClicked(e);			
//			}
//		} else {
//			super.mouseClicked(e);			
//		}
//	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point point = convertCoordsFromScreenToControlSheet(e.getX(), e.getY());

		IconComponent icon = getSelectedIcon();
		if ((icon == null) || !(icon.contains(point))) {
			icon = getIconAtPoint(point);
			setSelectedIcon(icon);
			repaint();				
		}
		
		if ((e.getButton() == MouseEvent.BUTTON1) && (icon != null)) {
			if ((getUsageMode() == UsageMode.EDIT) || (getUsageMode() == UsageMode.ALL)) {
				if (e.getClickCount() == 1) {
					setDraggingIcon(true);
					setIconX(icon.getLocation().getX());
					setIconY(icon.getLocation().getY());
				} else {
		    		if (getSelectedIcon().getSystem() instanceof Structure) {
		    			displayDamageableIconEditorDialog();				
		    		}					
				}
			} else if ((getUsageMode() == UsageMode.PLAY) && (icon instanceof SystemComponent)) {
				if (((SystemComponent) icon).toggleDamageBoxMarked(point.x - icon.getX(), point.y - icon.getY())) {
					setMarkingDamage(true);
					repaint();
				}
			}
		}
		
		super.mousePressed(e);
	}	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		setDraggingIcon(false);
		setMarkingDamage(false);

		super.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isDraggingIcon()) {
			double x = getIconX() + ((double) (e.getX() - getMouseX()) / getScale());
			double y = getIconY() + ((double) (e.getY() - getMouseY()) / getScale());			

			getSelectedIcon().setLocation(validateIconLocation(x, y, getSelectedIcon().getWidth(), getSelectedIcon().getHeight()));
			notifyStatusListeners("Position", getSelectedIcon().getX() + "," + getSelectedIcon().getY());

			repaint();
		} else if (isMarkingDamage()) {
			
		} else {
			super.mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (!isShowData()) {
//			Point point = convertCoordsFromScreenToControlSheet(e.getX(), e.getY());

//			SCSIconComponent icon = getSelectedIcon();
//			if ((icon == null) || !(icon.contains(point))) {
//				icon = getIconAtPoint(point);
//				setSelectedIcon(icon);
//				repaint();				
//			}
			
			
//			SCSIconComponent icon = getIconAtPoint(point);
//			if ((icon != null) && (icon.getSystem() instanceof Weapon)) {
//				try {
//					File file = new File("./weapondata/" + icon.getSystem().getType().replace("/", " ") + ".svg");
//					if (file.exists()) {
//						PartialSVGDocumentComponent component = new PartialSVGDocumentComponent(file);
//						component.setScale(2.0);
//						
//						PopupFactory factory = PopupFactory.getSharedInstance();
//						Popup weaponDataPopup = factory.getPopup(this, component, e.getX(), e.getY());
//						setWeaponDataPopup(weaponDataPopup);
//						weaponDataPopup.show();
//					}
//				} catch (Exception er) {
//					er.printStackTrace();
//				}
//			}
			
			
		} else {
			super.mouseMoved(e);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSelectedIcon() != null) {
			if (e.getActionCommand() == ACTION_REMOVE) {
				removeIcon(getSelectedIcon());

				if (getUnitIconListPanel() != null) {
					getUnitIconListPanel().addIconToList(getSelectedIcon());
				}
				
				setSelectedIcon(null);
			} else if (e.getActionCommand().startsWith(ACTION_EDIT)) {
				displayDamageableIconEditorDialog();
			} else if (e.getActionCommand().startsWith(ACTION_ICON)) {
				((SystemComponent) getSelectedIcon()).setIcon(Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().lastIndexOf(" ") + 1)));
			} else if (e.getActionCommand() == ACTION_MIRROR) {
				((SystemComponent) getSelectedIcon()).mirror();
			} else if (e.getActionCommand() == ACTION_ROTATE_RESET) {
				((SystemComponent) getSelectedIcon()).resetRotation();
			} else if (e.getActionCommand() == ACTION_ROTATE_45_CW) {
				((SystemComponent) getSelectedIcon()).rotate(45);
			} else if (e.getActionCommand() == ACTION_ROTATE_60_CW) {
				((SystemComponent) getSelectedIcon()).rotate(60);
			} else if (e.getActionCommand() == ACTION_ROTATE_90_CW) {
				((SystemComponent) getSelectedIcon()).rotate(90);
			} else if (e.getActionCommand() == ACTION_ROTATE_180) {
				((SystemComponent) getSelectedIcon()).rotate(180);
			} else if (e.getActionCommand() == ACTION_ROTATE_45_CCW) {
				((SystemComponent) getSelectedIcon()).rotate(-45);
			} else if (e.getActionCommand() == ACTION_ROTATE_60_CCW) {
				((SystemComponent) getSelectedIcon()).rotate(-60);
			} else if (e.getActionCommand() == ACTION_ROTATE_90_CCW) {
				((SystemComponent) getSelectedIcon()).rotate(-90);
			}

			repaint();
		}
	} 

	private void displayDamageableIconEditorDialog() {
		DamageableIcon icon = getSelectedIcon().getSystem().getIcon();
		double rotate = determineIconEditorRotation(((SystemComponent) getSelectedIcon()).getRotation());
		if (rotate != 0.0) {
			icon.rotate(rotate);
		}
		
		DamageableIconEditorDialog dialog = new DamageableIconEditorDialog(null, getSelectedIcon().toString(), 
				icon, getSelectedIcon().getSystem().getBaseDamageBoxes());
		dialog.addWindowListener(this);
		setIconEditorDialog(dialog);
		dialog.setVisible(true);						
	}
	
	private double determineIconEditorRotation(final double degrees) {
		double remainder = degrees % 90.0;
		
		if (remainder == 0.0) {
			return 0.0;
		} else if (remainder <= 45.0) {
			return remainder * -1.0;
		} else {			
			return 90.0 - remainder;
		}
	}
	
	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
		if ((getIconEditorDialog() != null) && (e.getComponent().equals(getIconEditorDialog()))) {
			try {
				DamageableIcon icon = getIconEditorDialog().getIcon();
				double rotate = determineIconEditorRotation(((SystemComponent) getSelectedIcon()).getRotation());
				if (rotate != 0.0) {
					icon.rotate(rotate * -1.0);
				}
				
				((SystemComponent) getSelectedIcon()).setIcon(icon);
			} catch (OperationNotSupportedException er) {
				er.printStackTrace();
			}

			setIconEditorDialog(null);
			repaint();
		}
	}

	public void windowClosing(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

}
