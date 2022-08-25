/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;
import java.awt.Font;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class TargetingArray extends Weapon {

	private static final long serialVersionUID = -3765081095716405300L;

	public static final Font ANNOTATION_FONT = new Font("Arial", Font.PLAIN, 7);
	
	private final int baseRating;
	private       int rating;
	
	public TargetingArray(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(armor, arc, Integer.parseInt(number), PropertyReader.getInteger(properties, "rating"));
	}
	
	public TargetingArray(final int armor, final Arc arc, final int number, final int rating) {
		super(6, armor, arc, String.valueOf(number), 2);
		
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
		return "Targeting Array";
	}
	
	public int getBaseRating() {
		return baseRating;
	}
	
	public int getRating() {
		return rating;
	}

	private void setRating(final int rating) {
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
	public Font getAnnotationFont(int index) {
		return ANNOTATION_FONT;
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getRating() < getBaseRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}	
	
}