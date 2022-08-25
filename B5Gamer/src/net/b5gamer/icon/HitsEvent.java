package net.b5gamer.icon;

import java.util.EventObject;

/**
 * The hits event emitted by damage box notifying objects.
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class HitsEvent extends EventObject {

	private static final long serialVersionUID = -1801341598509193624L;

	private final String key;   // key denoting the value that was changed
	private final int    value; // the new value
	
	/**
	 * @param source the object on which the Event initially occurred
	 * @param key    key denoting the value that was changed
	 * @param value  the new value
	 */
	public HitsEvent(Object source, String key, int value) {
		super(source);

		this.key   = key;
		this.value = value;
	}

	/**
	 * the key denoting the value that was changed
	 * 
	 * @return the key denoting the value that was changed
	 */
	public String getKey() {
		return key;
	}

	/**
	 * the new value
	 * 
	 * @return the new value
	 */
	public int getValue() {
		return value;
	}

}
