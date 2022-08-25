package net.b5gamer.b5wars.unit.structural.system;

import java.util.Iterator;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

public class GenericSystem extends System {

	private static final long serialVersionUID = -8820787717122984126L;

	private static final String ANNOTATION_DENOTER = "*";
	
	private final String     type;                           // system type
	private final Properties properties  = new Properties(); // generic system properties
	private final Properties annotations = new Properties(); // generic system annotations
	
	/**
	 * @param damageBoxes amount of damage the system can sustain before being destroyed
	 * @param armor       amount of armor protecting the system
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the system, may be null
	 * @param properties  additional properties 
	 */
	public GenericSystem(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(properties.getProperty("systemType"), damageBoxes, armor, arc, name, properties);
	}
	
	/**
	 * @param type        system type
	 * @param damageBoxes amount of damage the system can sustain before being destroyed
	 * @param armor       amount of armor protecting the system
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the system, may be null
	 * @param properties  generic system properties
	 */
	public GenericSystem(final String type, final int damageBoxes, final int armor, final Arc arc, 
			final String name, final Properties properties) {
		super(damageBoxes, armor, arc, name);
		
        if ((type == null) || !(type.trim().length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 
        
        this.type = type;
        
        if (properties != null) {
            properties.removeProperty("systemType");

            for (Iterator<String> iterator = properties.keySet().iterator(); iterator.hasNext();) {
                String key = iterator.next();
                
                if (key.startsWith(ANNOTATION_DENOTER)) {
                	getAnnotations().setProperty(key.substring(1), properties.getProperty(key));
                } else {
                	getProperties().setProperty(key, properties.getProperty(key));                	
                }
            }
        }
	}
	
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
        
		for (Iterator<String> iterator = getProperties().keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
			properties.setProperty(key, getProperties().getProperty(key));
		}
		for (Iterator<String> iterator = getAnnotations().keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
			properties.setProperty(ANNOTATION_DENOTER + key, getAnnotations().getProperty(key));
		}
		
		return properties;
	}
		
	@Override
	public String getType() {
		return type;
	}

	protected Properties getProperties() {
		return properties;
	}
	
	protected Properties getAnnotations() {
		return annotations;
	}

	@Override
	protected void resolveCriticalHit(int roll) {
	}

	@Override
	protected void adjustSystem() {
	}

	@Override
	public int getAnnotationCount() {
		return getAnnotations().size();
	}
	
	@Override
	public String getAnnotation(int index) {
		if ((index < 0) || (index >= getAnnotationCount())) {
			throw new IndexOutOfBoundsException();
		}
		
		return getAnnotations().getProperty((String) getAnnotations().keySet().toArray()[index]);
	}
	
	@Override
	public int getRecognitionOrder() {
		return 950;
	}

}
