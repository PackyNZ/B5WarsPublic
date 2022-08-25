/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.ui.selection;

import java.awt.Component;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class selects a single object from a list of objects by presenting the user with a
 * dialog box from which to make a selection   
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public class InteractiveSelectionHandler implements SelectionHandler {

	private final Component parent;  // parent of the dialog 
	private final String    title;   // title for the dialog
	private final String    message; // message to display in the dialog
	
	/**
	 * @param parent  parent of the dialog 
	 * @param title   title for the dialog
	 * @param message message to display in the dialog
	 */
	public InteractiveSelectionHandler(Component parent, String title, String message) {
        if ((title == null) || !(title.length() > 0)) {
            throw new IllegalArgumentException("title cannot be null or blank");
        } 
        if ((message == null) || !(message.length() > 0)) {
            throw new IllegalArgumentException("message cannot be null or blank");
        } 

        this.parent  = parent;
		this.title   = title;
		this.message = message;
	}	
	
	/**
	 * the parent of the dialog 
	 * 
	 * @return the parent of the dialog 
	 */
	public Component getParent() {
		return parent;
	}

	/**
	 * the title for the dialog
	 * 
	 * @return the title for the dialog
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * the message to display in the dialog
	 * 
	 * @return the message to display in the dialog
	 */
	public String getMessage() {
		return message;
	}

	public Object select(List<? extends Object> objects) {
		if ((objects == null) || (objects.size() == 0)) {
			return null;
		} else if (objects.size() == 1) {
			return objects.get(0);
		} else {
			Object result = JOptionPane.showInputDialog(getParent(), getMessage(), getTitle(),
					JOptionPane.QUESTION_MESSAGE, null, objects.toArray(), null);
			while (result == null) {
				result = JOptionPane.showInputDialog(getParent(), getMessage(), getTitle(),
						JOptionPane.QUESTION_MESSAGE, null, objects.toArray(), null);
			}
			
			return result;
		}
	}

}
