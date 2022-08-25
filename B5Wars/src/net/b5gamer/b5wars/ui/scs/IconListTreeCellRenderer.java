/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.system.System;

public class IconListTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -5790676235284490152L;

	protected static final Map<Class<?>, Icon> ICONS = new HashMap<Class<?>, Icon>();
	protected static final Map<Class<?>, Font> FONTS = new HashMap<Class<?>, Font>();
	
	static {
		ICONS.put(Unit.class, null);
		FONTS.put(Unit.class, new Font("Verdana", Font.BOLD, 13));
		
		ICONS.put(Section.class, null);
		FONTS.put(Section.class, new Font("Verdana", Font.PLAIN, 12));

		ICONS.put(System.class, null);
		FONTS.put(System.class, new Font("Verdana", Font.PLAIN, 11));
	}
	
	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
    		boolean expanded, boolean leaf, int row, boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        
        if (node.getUserObject() instanceof IconComponent) {
        	super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        	setIcon((Icon) node.getUserObject());
        	setText("");
        	return this;
        } else {
    		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    		
            if (node.getUserObject() instanceof Unit) {
            	setIcon(ICONS.get(Unit.class));
        		setFont(FONTS.get(Unit.class));
            } else if (node.getUserObject() instanceof Section) {
            	setIcon(ICONS.get(Section.class));
        		setFont(FONTS.get(Section.class));
            } else if (node.getUserObject() instanceof System) {
            	setIcon(ICONS.get(System.class));
        		setFont(FONTS.get(System.class));
            }            
    		
    		return this;
        }
	}
	
}
