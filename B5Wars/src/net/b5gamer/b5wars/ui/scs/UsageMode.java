/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

public enum UsageMode {

	ALL,
	EDIT,
	PLAY,
	VIEW;
	
	public static UsageMode getUsageMode(final String name) {
		UsageMode result = null;

    	for (UsageMode mode : UsageMode.values()) {
            if (mode.toString().equalsIgnoreCase(name)) {
                result = mode;
                break;
            }                
    	} 

        return (result != null) ? result : VIEW;	
	}
	
}
