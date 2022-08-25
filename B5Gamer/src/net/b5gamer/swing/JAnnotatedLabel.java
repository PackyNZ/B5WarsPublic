/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.swing;

import javax.swing.JLabel;

/**
 * An extension of {@link javax.swing.JLabel} that maintains an optional prefix and suffix
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class JAnnotatedLabel extends JLabel {

	private static final long serialVersionUID = -2777936515544383683L;

	private String prefix; // the optional prefix, may be null
	private String suffix; // the optional suffix, may be null
	
	/**
	 * Create a new label using the JLabel() constructor and the specified prefix and suffix.
	 *
	 * @param prefix the optional prefix, may be null
	 * @param suffix the optional suffix, may be null
	 */
	public JAnnotatedLabel(final String prefix, final String suffix) {
		super();
		
		this.prefix = ((prefix != null) && (prefix.length() > 0)) ? prefix : null;
		this.suffix = ((suffix != null) && (suffix.length() > 0)) ? suffix : null;
	}

	/**
	 * Returns the prefix, may be null.
	 * 
	 * @return the prefix, may be null
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the optional prefix, may be null.
	 * 
	 * @param prefix the optional prefix, may be null
	 */
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * Returns the suffix, may be null.
	 * 
	 * @return the suffix, may be null
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * Sets the optional suffix, may be null.
	 * 
	 * @param suffix the optional suffix, may be null
	 */
	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	/**
	 * Returns the annotated text string that the label displays.
	 * 
	 * @return annotated label text
	 */
	@Override
	public String getText() {
		StringBuilder text = new StringBuilder();
		
		if (getPrefix() != null) {
			text.append(getPrefix());
		}
		
		text.append(super.getText());
		
		if (getSuffix() != null) {
			text.append(getSuffix());
		}
		
		return text.toString();
	}	

}
