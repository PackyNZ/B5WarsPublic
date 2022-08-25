/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.fleet;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.b5wars.unit.Unit;

public class FleetUnitListTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -4420983554601803150L;

	protected static final Map<Class<?>, Icon> ICONS = new HashMap<Class<?>, Icon>();
	protected static final Map<Class<?>, Font> FONTS = new HashMap<Class<?>, Font>();
	
	static {
		ICONS.put(Fleet.class, null);
		FONTS.put(Fleet.class, new Font("Verdana", Font.BOLD, 13));
		
		ICONS.put(Unit.class, null);
		FONTS.put(Unit.class, new Font("Verdana", Font.PLAIN, 12));

		ICONS.put(FleetUnitListTreeCellRenderer.class, null);
		FONTS.put(FleetUnitListTreeCellRenderer.class, new Font("Verdana", Font.PLAIN, 11));
	}
	
	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
    		boolean expanded, boolean leaf, int row, boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        
        if (node.getUserObject() instanceof FleetUnitListLabel) {
        	super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        	
        	setIcon(((FleetUnitListLabel) node.getUserObject()).getIcon());
        	setText(((FleetUnitListLabel) node.getUserObject()).getText());
        	
        	return this;
        } else {
    		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    		
            if (node.getUserObject() instanceof Fleet) {
            	setIcon(ICONS.get(Fleet.class));
        		setFont(FONTS.get(Fleet.class));
            } else if (node.getUserObject() instanceof Unit) {
            	setIcon(ICONS.get(Unit.class));
        		setFont(FONTS.get(Unit.class));
            } else {
            	setIcon(ICONS.get(FleetUnitListTreeCellRenderer.class));
        		setFont(FONTS.get(FleetUnitListTreeCellRenderer.class));
            }            
    		
    		return this;
        }
	}
	
}
