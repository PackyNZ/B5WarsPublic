package net.b5gamer.map;

import java.awt.geom.Arc2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An Arc represents the arc covered by directional systems and ship sections
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Arc implements Serializable {
		
	private static final long serialVersionUID = -5216269018152840199L;

	private static final List<Arc> ARCS = new ArrayList<Arc>(0); // all arcs
	
	public static final Arc FWD_60       = new Arc("F60",   330, 60); 
	public static final Arc FWD_90       = new Arc("F90",   315, 90); 
	public static final Arc FWD_120      = new Arc("F120",  300, 120);
	public static final Arc FWD_180      = new Arc("F180",  270, 180);
	public static final Arc FWD_240      = new Arc("F240",  240, 240);
	public static final Arc AFT_60       = new Arc("A60",   150, 60);
	public static final Arc AFT_90       = new Arc("A90",   135, 90);
	public static final Arc AFT_120      = new Arc("A120",  120, 120);
	public static final Arc AFT_180      = new Arc("A180",  90,  180);
	public static final Arc AFT_240      = new Arc("A240",  60,  240);
	public static final Arc PORT_60      = new Arc("P60",   240, 60); 
	public static final Arc PORT_90      = new Arc("P90",   225, 90); 
	public static final Arc PORT_120     = new Arc("P120",  210, 120);
	public static final Arc PORT_180     = new Arc("P180",  180, 180);
	public static final Arc PORT_240     = new Arc("P240",  150, 240);
	public static final Arc STB_60       = new Arc("S60",   60,  60);
	public static final Arc STB_90       = new Arc("S90",   45,  90);
	public static final Arc STB_120      = new Arc("S120",  30,  120);
	public static final Arc STB_180      = new Arc("S180",  0,   180);
	public static final Arc STB_240      = new Arc("S240",  330, 240);
	public static final Arc FWD_PORT_60  = new Arc("FP60",  300, 60);
	public static final Arc FWD_PORT_90  = new Arc("FP90",  270, 90);
	public static final Arc FWD_PORT_120 = new Arc("FP120", 240, 120);
	public static final Arc FWD_PORT_180 = new Arc("FP180", 240, 180);
	public static final Arc FWD_PORT_240 = new Arc("FP240", 180, 240);	
	public static final Arc FWD_STB_60   = new Arc("FS60",  0,   60);
	public static final Arc FWD_STB_90   = new Arc("FS90",  0,   90);
	public static final Arc FWD_STB_120  = new Arc("FS120", 0,   120);
	public static final Arc FWD_STB_180  = new Arc("FS180", 300, 180);
	public static final Arc FWD_STB_240  = new Arc("FS240", 300, 240);	
	public static final Arc AFT_PORT_60  = new Arc("AP60",  180, 60);
	public static final Arc AFT_PORT_90  = new Arc("AP90",  180, 90);
	public static final Arc AFT_PORT_120 = new Arc("AP120", 180, 120);
	public static final Arc AFT_PORT_150 = new Arc("AP150", 180, 150);
	public static final Arc AFT_PORT_180 = new Arc("AP180", 120, 180);
	public static final Arc AFT_PORT_240 = new Arc("AP240", 120, 240);	
	public static final Arc AFT_STB_60   = new Arc("AS60",  120, 60);
	public static final Arc AFT_STB_90   = new Arc("AS90",  90,  90);
	public static final Arc AFT_STB_120  = new Arc("AS120", 60,  120);
	public static final Arc AFT_STB_150  = new Arc("AS150", 30,  150);
	public static final Arc AFT_STB_180  = new Arc("AS180", 60,  180);
	public static final Arc AFT_STB_240  = new Arc("AS240", 0,   240);	
	public static final Arc FULL_360     = new Arc("360",   0,   360);

	private final String name;    // name of the arc
	private final int    angle;   // starting angle of the arc (in degrees)
	private final int    degrees; // number of degrees covered by the arc
	
	/**
	 * a list of all arcs
	 * 
	 * @return a list of all arcs
	 */
	protected static final List<Arc> getArcs() {
		return ARCS;
	}

	/**
	 * the number of arcs
	 * 
	 * @return the number of arcs
	 */
	public static int getArcCount() {
		return getArcs().size();
	}
	
    /**
     * an iterator of all arcs
     * 
	 * @return an iterator of all arcs
	 */
	public static final Iterator<Arc> getArcIterator() {
		return getArcs().iterator();
	}
	
	/**
     * find and return an arc with a given name
     * 
     * @param  name name of the arc to return
     * @return      the arc, or null if an arc with a given name doesn't exist
     */
    public static final Arc getArc(final String name) {
    	Arc result = null;

    	for (Iterator<Arc> iterator = ARCS.iterator(); iterator.hasNext();) {
    		Arc arc = iterator.next();

            if (arc.getName().equalsIgnoreCase(name)) {
                result = arc;
                break;
            }                
    	} 

        return result;
    }
        
	/**
	 * create an Arc from a String definition in the form of Arc(angle,degrees)
	 * 
	 * @param  arcDefinition Arc definition in the form of Arc(angle,degrees)
	 * @return     the new arc
	 * 
	 * @throws IllegalArgumentException if arc is in an invalid format
	 */	
	public static final Arc createArc(String arcDefinition) throws IllegalArgumentException {
		Arc result = null;
		
        if ((arcDefinition != null) && (arcDefinition.length() > 0) && (arcDefinition.startsWith("Arc("))) {				
	        try {
				String[] values = arcDefinition.substring(4, arcDefinition.indexOf(")")).split(",");
				
				if (values.length == 2) {
					result = new Arc(Integer.valueOf(values[0].trim()), Integer.valueOf(values[1].trim()));
				}
	        } catch (Exception e) {} 
		}

		if (result == null) {
            throw new IllegalArgumentException("Arc " + arcDefinition + " is in an invalid format");
		}
		
		return result;
	}
	
	/**
	 * @param angle   starting angle of the arc (in degrees)
	 * @param degrees number of degrees covered by the arc
	 */
	public Arc(final int angle, final int degrees) {
		this(null, angle, degrees);
	}
	
	/**
	 * @param name    name of the arc
	 * @param angle   starting angle of the arc (in degrees)
	 * @param degrees number of degrees covered by the arc
	 */
	public Arc(final String name, final int angle, final int degrees) {
        if ((name != null) && (getArc(name) != null)) {
            throw new IllegalArgumentException("Arc with same name already exists!");
        }
        if ((angle < 0) || (angle > 359)) {
            throw new IllegalArgumentException("angle must be between 0 and 359 degrees inclusive");
        } 
        if (!(degrees > 0) || (degrees > 360)) {
            throw new IllegalArgumentException("degrees must be between 1 and 360 degrees inclusive");
        } 
		
		this.name    = name;
		this.angle   = angle;
		this.degrees = degrees;

		if ((name != null) && (name.length() > 0)) {
			getArcs().add(this);
		} 
	}

	/**
	 * the name of the arc
	 * 
	 * @return the name of the arc
	 */
	public final String getName() {
		return name;
	}

	/**
	 * the starting angle of the arc (in degrees)
	 * 
	 * @return the starting angle of the arc (in degrees)
	 */
	public final int getFromAngle() {
		return angle;
	}

	/**
	 * the end angle of the arc (in degrees)
	 * 
	 * @return the end angle of the arc (in degrees)
	 */
	public final int getToAngle() {
		int endAngle = getFromAngle() + getDegrees();
		return endAngle >= 359 ? endAngle - 360 : endAngle;
	}

	/**
	 * the number of degrees covered by the arc 
	 * 
	 * @return the number of degrees covered by the arc 
	 */
	public final int getDegrees() {
		return degrees;
	}
	
	/**
	 * whether this arc contains a given angle
	 * 
	 * @param  angle angle (in degrees) to check
	 * @return       whether this arc contains a given angle
	 */
	public final boolean contains(final int angle) {
        if ((angle < 0) || (angle > 359)) {
            throw new IllegalArgumentException("angle must be between 0 and 359 degrees inclusive");
        } 

        if (getFromAngle() + getDegrees() > 359) {
        	return ((angle >= getFromAngle()) || (angle <= getToAngle())); 
        } else {
        	return ((angle >= getFromAngle()) && (angle <= getToAngle()));         	
        }
	}
	
	public final Arc2D toShape(final int width, final int height) {
		int span = 360 - (getFromAngle() + getDegrees() - 90); 
        return new Arc2D.Double(2, 2, width, height, span, getDegrees(), getDegrees() == 360 ? Arc2D.OPEN : Arc2D.PIE);
	}
	
	@Override
	public final String toString() {
        if (getName() != null) {
        	return getName();
        } else {
        	StringBuilder definition = new StringBuilder();
        	definition.append("Arc(").append(getFromAngle()).append(",").append(getDegrees()).append(")");
        	
        	return definition.toString();
        }
	}
	
}
