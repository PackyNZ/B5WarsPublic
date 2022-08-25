package net.b5gamer.util;

import java.util.EventObject;

/**
 * The status event emitted by status notifying objects.
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class StatusEvent extends EventObject {

	private static final long serialVersionUID = 3183706208555352567L;

	private final String key;   // key denoting the value that was changed
	private final String value; // the new value
	
	/**
	 * @param source the object on which the Event initially occurred
	 * @param key    key denoting the value that was changed
	 * @param value  the new value
	 */
	public StatusEvent(Object source, String key, String value) {
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
	public String getValue() {
		return value;
	}

}
