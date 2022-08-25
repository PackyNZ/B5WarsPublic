package net.b5gamer.util;

import java.util.EventListener;

/**
 * The listener interface for receiving status events.
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface StatusListener extends EventListener {

	/**
	 * Invoked when the status of an object has changed.
	 */
	public void statusValueChanged(StatusEvent e);

}
