/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.ui.selection;

import java.util.List;

/**
 * The SelectionHandler interface is used to denote a class as being able to resolve
 * selection of a single object from a list of objects   
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public interface SelectionHandler {

    /**
     * resolve selection of a single object from a list of objects
     *
     * @param  items list of objects to make the selection from
     * @return       the selected object, will be null if objects is null, empty, or 
     *               if a null object is selected from the list
     */		
	public Object select(List<? extends Object> objects);
	
}
