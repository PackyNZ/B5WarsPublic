package net.b5gamer.b5wars.ui.fleet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.swing.JPaintingPanel;

public class FleetUnitListPanel extends JPaintingPanel implements TreeSelectionListener, MouseListener, ActionListener {
	
	private static final long serialVersionUID = 1995070415029867802L;

	private static final Random RANDOM = new Random();
	
	protected Fleet                  fleet        = null;
	private   JScrollPane            scrollPane   = null;
	private   JTree                  tree         = null;
	private   DefaultMutableTreeNode selectedNode = null;

	public FleetUnitListPanel(final Fleet fleet) {
		super(new BorderLayout());	

		setFleet(fleet);
	}	

	public Fleet getFleet() {
		return fleet;
	}

	protected void setFleet(Fleet fleet) {
//      if (fleet == null) {
//          throw new IllegalArgumentException("fleet cannot be null");
//      }
		
		this.fleet = fleet;
		
        if (fleet != null) {
        	setupTree();
        } else {
        	setScrollPane(null);
        }
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
	
	protected Object getSelectedObject() {
		return (getSelectedNode() != null) ? getSelectedNode().getUserObject() : null;
	}
	
	protected DefaultMutableTreeNode getSelectedNode() {
		return selectedNode;
	}
	
	private void setSelectedNode(DefaultMutableTreeNode node) {
//		if ((getSelectedObject() != null) && (getSelectedObject() instanceof SCSIconComponent)) {
//			((SCSIconComponent) getSelectedObject()).setSelected(false);
//		}
		
		this.selectedNode = node;

//		if ((getSelectedObject() != null) && (getSelectedObject() instanceof SCSIconComponent)) {
//			((SCSIconComponent) getSelectedObject()).setSelected(true);
//		}
	}
	
	protected void setupTree() {
		// create nodes
	    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(getFleet());

	    int counterNumber = 1;
	    for (Iterator<Unit> iterator = getFleet().getUnits().iterator(); iterator.hasNext();) {
	    	Unit unit = iterator.next();

		    DefaultMutableTreeNode unitNode = new DefaultMutableTreeNode(unit);
	    	rootNode.add(unitNode);
	    	
	    	if (unit.getCounter() != null) {
	    		unitNode.add(new DefaultMutableTreeNode(new FleetUnitListLabel(unit, counterNumber++)));    		
	    	}
	    }
	    
	    // create tree
	    JTree tree = new JTree(rootNode);
	    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	    tree.setCellRenderer(new FleetUnitListTreeCellRenderer());
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
	
	protected void orderUnitsByInitiative() {
		DefaultTreeModel model = (DefaultTreeModel) getTree().getModel();
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
		
		DefaultMutableTreeNode prevNode = (DefaultMutableTreeNode) rootNode.getChildAt(0);
		Unit prevUnit = (Unit) prevNode.getUserObject();

		boolean modelChanged = false;
		for (int i = 1; i < rootNode.getChildCount(); i++) {
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
			Unit thisUnit = (Unit) thisNode.getUserObject();
			
			if (prevUnit.getInitiative() == thisUnit.getInitiative()) {
				if (prevUnit.getInitiativeModifier() == thisUnit.getInitiativeModifier()) {
					if (RANDOM.nextInt(2) == 0) {
						prevNode = thisNode;
						prevUnit = thisUnit;
					} else {
						rootNode.insert(thisNode, i - 1);
						modelChanged = true;
					}					
				} else {
					if (prevUnit.getInitiativeModifier() < thisUnit.getInitiativeModifier()) {
						prevNode = thisNode;
						prevUnit = thisUnit;
					} else {
						rootNode.insert(thisNode, i - 1);
						modelChanged = true;
					}
				}				
			} else {
				if (prevUnit.getInitiative() < thisUnit.getInitiative()) {
					prevNode = thisNode;
					prevUnit = thisUnit;
				} else {
					rootNode.insert(thisNode, i - 1);
					modelChanged = true;
				}
			}	
		}	
		
		if (modelChanged) {
			model.nodeStructureChanged(rootNode);
			
		    for (int i = 0; i < getTree().getRowCount(); i++) {
		    	getTree().expandRow(i);
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
		
//		if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() > 1)) {
//			if ((getSelectedObject() != null) && (getSelectedObject() instanceof SCSIconComponent)) {
//				addSelectedIconToSCS();
//			}
//		}
		
		checkIfShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		checkIfShowPopup(e);
	}

    private void checkIfShowPopup(MouseEvent e) {
        if (e.isPopupTrigger() && (getSelectedObject() != null)) {
//        	JPopupMenu popupMenu = getPopupMenus().get(getSelectedObject().getClass());
//
//        	if (popupMenu != null) {        
//        		popupMenu.show(this, 
//        				e.getX() - getScrollPane().getViewport().getViewPosition().x, 
//        				e.getY() - getScrollPane().getViewport().getViewPosition().y);
//        	}
        }
    }

	public void actionPerformed(ActionEvent e) {
		if (getSelectedObject() != null) {
//			if (getSelectedObject() instanceof SCSIconComponent) {
//				if (e.getActionCommand() == ACTION_ADD) {
//				}				
//			}
		}		
	}	
	
}
