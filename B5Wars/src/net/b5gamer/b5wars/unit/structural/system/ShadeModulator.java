/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

public class ShadeModulator extends PoweredSystem {

	private static final long serialVersionUID = 346331803291678549L;

	private final int baseRating; 
	private       int rating;     
	
	public ShadeModulator(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"),
				PropertyReader.getInteger(properties, "rating"));
	}
	
	public ShadeModulator(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int rating) {
		super(damageBoxes, armor, arc, name, power);
		
        if (!(rating > 0)) {
            throw new IllegalArgumentException("rating must be a positive number");
        } 

        this.baseRating = rating;
        this.rating     = rating;
	}

	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("rating", String.valueOf(getBaseRating())); 
		
		return properties;
	}
	
	public String getType() {
		return "Shade Modulator";
	}
	
	public int getBaseRating() {
		return baseRating;
	}

	public int getRating() {
		return rating;
	}
	
	protected void setRating(final int rating) {
        if (rating > getBaseRating()) {
            throw new IllegalArgumentException("rating cannot exceed base rating");
        } 

		this.rating = rating < 0 ? 0 : rating;		
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getRating());
	}
		
	@Override
	public Color getAnnotationColor(int index) {
		return (getRating() < getBaseRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 890;
	}
	
}
