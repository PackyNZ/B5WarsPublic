/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.ui.selection;

import java.util.List;
import java.util.Random;

/**
 * This class randomly selects a single object from a list of objects   
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public class RandomSelectionHandler implements SelectionHandler {

	private static final Random RANDOM = new Random();
	
	public Object select(List<? extends Object> objects) {
		if ((objects == null) || (objects.size() == 0)) {
			return null;
		} else if (objects.size() == 1) {
			return objects.get(0);
		} else {
			return objects.get(RANDOM.nextInt(objects.size()));
		}
	}

}
