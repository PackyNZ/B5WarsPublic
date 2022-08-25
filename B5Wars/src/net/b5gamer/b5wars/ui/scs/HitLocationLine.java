/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.font.GlyphVector;

public class HitLocationLine {

	protected final GlyphVector range;
	protected final GlyphVector location;

	public HitLocationLine(GlyphVector range, GlyphVector location) {
		this.range    = range;
		this.location = location;
	}

	public GlyphVector getRange() {
		return range;
	}

	public GlyphVector getLocation() {
		return location;
	}
	
}
