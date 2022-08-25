package net.b5gamer.icon;

import java.util.EventListener;

public interface DamageBoxListener extends EventListener {

	/**
	 * Invoked when the hits of a DamageableIcon has changed.
	 */
	public void hitsValueChanged(HitsEvent e);

}
