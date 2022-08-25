/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.SectionCollection;
import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.swing.JPaintingPanel;

public class IconListPanel extends JPaintingPanel implements TreeSelectionListener, MouseListener, ActionListener {

	private static final long serialVersionUID = -7762682291248349494L;

	private static final String ACTION_ADD = "Add";
	
	protected StructuralUnit            unit                  = null;
	protected ShipControlSheetPanel     shipControlSheetPanel = null;
	private   JScrollPane               scrollPane            = null;
	private   JTree                     tree                  = null;
	private   Map<Class<?>, JPopupMenu> popupMenus            = new HashMap<Class<?>, JPopupMenu>();
	private   JMenuItem                 menuItemAdd           = null;
	private   DefaultMutableTreeNode    selectedNode          = null;
	
	public IconListPanel(final StructuralUnit unit, ShipControlSheetPanel shipControlSheetPanel) {
		super(new BorderLayout());	

		setupPopupMenus();
		setShipControlSheetPanel(shipControlSheetPanel);
		setUnit(unit);
	}

	public StructuralUnit getUnit() {
		return unit;
	}

	protected void setUnit(StructuralUnit unit) {
//        if (unit == null) {
//            throw new IllegalArgumentException("unit cannot be null");
//        }

        this.unit = unit;
		
        if (unit != null) {
        	setupTree();
        } else {
        	setScrollPane(null);
        }
	}
	
	protected ShipControlSheetPanel getShipControlSheetPanel() {
		return shipControlSheetPanel;
	}

	private void setShipControlSheetPanel(ShipControlSheetPanel shipControlSheetPanel) {
        if (shipControlSheetPanel == null) {
            throw new IllegalArgumentException("shipControlSheetPanel cannot be null");
        }

        this.shipControlSheetPanel = shipControlSheetPanel;
	}

	protected JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void setScrollPane(JScrollPane scrollPane) {
		if (getScrollPane() != null) {
			remove(getScrollPane());
		}
		
	    this.scrollPane = scrollPane;
	    
	    if (scrollPane != null) {
	    	add(getScrollPane(), BorderLayout.CENTER);
	    }
	    
	    revalidate();
	}

	protected JTree getTree() {
		return tree;
	}

	private void setTree(JTree tree) {
		this.tree = tree;
	}
	
	protected Map<Class<?>, JPopupMenu> getPopupMenus() {
		return popupMenus;
	}

	protected JMenuItem getMenuItemAdd() {
		return menuItemAdd;
	}

	private void setMenuItemAdd(JMenuItem menuItemAdd) {
		this.menuItemAdd = menuItemAdd;
	}

	protected Object getSelectedObject() {
		return (getSelectedNode() != null) ? getSelectedNode().getUserObject() : null;
	}
	
	protected DefaultMutableTreeNode getSelectedNode() {
		return selectedNode;
	}

	private void setSelectedNode(DefaultMutableTreeNode node) {
		if ((getSelectedObject() != null) && (getSelectedObject() instanceof IconComponent)) {
			((IconComponent) getSelectedObject()).setSelected(false);
		}
		
		this.selectedNode = node;

		if ((getSelectedObject() != null) && (getSelectedObject() instanceof IconComponent)) {
			((IconComponent) getSelectedObject()).setSelected(true);
		}
	}
	
	private final void setupPopupMenus() {
		// create menu items
		JMenuItem actionAdd = new JMenuItem(ACTION_ADD);
		actionAdd.setActionCommand(ACTION_ADD);
		actionAdd.addActionListener(this);
		setMenuItemAdd(actionAdd);
		
		// create menus
	    JPopupMenu popupMenu = new JPopupMenu();
	    popupMenu.add(actionAdd);			

	    getPopupMenus().put(SystemComponent.class,       popupMenu);
		getPopupMenus().put(ArmorComponent.class,        popupMenu);
		getPopupMenus().put(PowerComponent.class,        popupMenu);
		getPopupMenus().put(ArcComponent.class,          popupMenu);
		getPopupMenus().put(WeaponNumberComponent.class, popupMenu);
	}
	
	protected void setupTree() {
		// create nodes
	    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(getUnit());

	    for (Iterator<Section> iterator = getUnit().getSectionIterator(); iterator.hasNext();) {
	    	setupSectionNode(rootNode, iterator.next());
	    }
	    
	    // create tree
	    JTree tree = new JTree(rootNode);
	    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	    tree.setCellRenderer(new IconListTreeCellRenderer());
	    tree.addTreeSelectionListener(this);
	    tree.addMouseListener(this);
	    
	    // expand nodes
//	    TreePath rootPath = new TreePath(rootNode);
//	    for (int i = 0; i < rootNode.getChildCount(); i++) {
//	    	tree.expandPath(rootPath.pathByAddingChild(rootNode.getChildAt(i)));
//	    }
	    for (int i = 0; i < tree.getRowCount(); i++) {
	    	tree.expandRow(i);
	    }
	    
	    setTree(tree);
	    setScrollPane(new JScrollPane(tree));
	}

	protected void setupSectionNode(DefaultMutableTreeNode rootNode, Section section) {
	    DefaultMutableTreeNode sectionNode = new DefaultMutableTreeNode(section);
    	rootNode.add(sectionNode);
    	
	    for (Iterator<System> iterator = section.getSystemIterator(); iterator.hasNext();) {
	    	System system = iterator.next();
		    DefaultMutableTreeNode systemNode = new DefaultMutableTreeNode(system);
	    	sectionNode.add(systemNode);	    	
	    	
	    	if ((system.getIcon() != null) && 
	    			(system.getIconPosition().getX() == 0) && (system.getIconPosition().getY() == 0)) {
	    		systemNode.add(new DefaultMutableTreeNode(new SystemComponent(system)));
	    	}
	    	
	    	if ((system.getArmorIconPosition().getX() == 0) && (system.getArmorIconPosition().getY() == 0)) {
	    		systemNode.add(new DefaultMutableTreeNode(new ArmorComponent(system)));
	    	}
	    	
	    	if ((system instanceof PoweredSystem) && 
	    			(((PoweredSystem) system).getPowerIconPosition().getX() == 0) && 
	    			(((PoweredSystem) system).getPowerIconPosition().getY() == 0)) {
		    	systemNode.add(new DefaultMutableTreeNode(new PowerComponent((PoweredSystem) system)));		    		
	    	}
	    	
	    	if ((system.getArc() != null) && (system.getArcIconPosition().getX() == 0) && 
	    			(system.getArcIconPosition().getY() == 0)) {
	    		systemNode.add(new DefaultMutableTreeNode(new ArcComponent(system)));
	    	}
	    	
	    	if ((system instanceof Weapon) && (system.getName() != null) && 
	    			(((Weapon) system).getWeaponNumberPosition().getX() == 0) && 
	    			(((Weapon) system).getWeaponNumberPosition().getY() == 0)) {
		    	systemNode.add(new DefaultMutableTreeNode(new WeaponNumberComponent((Weapon) system)));		    		
	    	}
	    }		
	    
		if (section instanceof SectionCollection) {
            for (Iterator<Section> iterator = ((SectionCollection) section).getSectionIterator(); iterator.hasNext();) {
            	setupSectionNode(rootNode, iterator.next());
    		}
		}    
	}
		
	public void valueChanged(TreeSelectionEvent e) {
		setSelectedNode((DefaultMutableTreeNode) getTree().getLastSelectedPathComponent());
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		TreePath path = getTree().getPathForLocation(e.getX(), e.getY());
		
		if (path != null) {
			getTree().setSelectionPath(path);
		}		
		
		if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() > 1)) {
			if ((getSelectedObject() != null) && (getSelectedObject() instanceof IconComponent)) {
				addSelectedIconToSCS();
			}
		}
		
		checkIfShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		checkIfShowPopup(e);
	}

    private void checkIfShowPopup(MouseEvent e) {
        if (e.isPopupTrigger() && (getSelectedObject() != null)) {
        	JPopupMenu popupMenu = getPopupMenus().get(getSelectedObject().getClass());

        	if (popupMenu != null) {        
        		popupMenu.show(this, 
        				e.getX() - getScrollPane().getViewport().getViewPosition().x, 
        				e.getY() - getScrollPane().getViewport().getViewPosition().y);
        	}
        }
    }

	public void actionPerformed(ActionEvent e) {
		if (getSelectedObject() != null) {
			if (getSelectedObject() instanceof IconComponent) {
				if (e.getActionCommand() == ACTION_ADD) {
					addSelectedIconToSCS();
				}				
			}
		}		
	}
    
	private void addSelectedIconToSCS() {
		IconComponent icon = (IconComponent) getSelectedObject();
		
		DefaultTreeModel model = (DefaultTreeModel) getTree().getModel();
		model.removeNodeFromParent(getSelectedNode());
		setSelectedNode(null);		

		getShipControlSheetPanel().addIcon(icon);
	}

	public void addIconToList(IconComponent icon) {
        if (icon == null) {
            throw new IllegalArgumentException("icon cannot be null");
        }
        
        icon.setLocation(0, 0);		
		
		DefaultTreeModel model = (DefaultTreeModel) getTree().getModel();
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();

		for (int i = 0; i < rootNode.getChildCount(); i++) {
			DefaultMutableTreeNode sectionNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);

			for (int j = 0; j < sectionNode.getChildCount(); j++) {
				DefaultMutableTreeNode systemNode = (DefaultMutableTreeNode) sectionNode.getChildAt(j);

				if (systemNode.getUserObject() == icon.getSystem()) {
					DefaultMutableTreeNode iconNode = new DefaultMutableTreeNode(icon);
					systemNode.add(iconNode);
					model.nodesWereInserted(systemNode, new int[] {systemNode.getChildCount() - 1});
					
					i = rootNode.getChildCount();
					break;
				}
			}		
		}		
	}
	
}
