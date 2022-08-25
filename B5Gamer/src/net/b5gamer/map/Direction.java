package net.b5gamer.map;

/**
 * !!--- work in progress ---!!
 * A Direction represents an angle in degrees 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Direction {
	
	private int angle = 0; // the angle in degrees
	
	/**
	 * @param angle the angle in degrees
	 */
	public Direction(final int angle) {
        setAngle(angle);
	}
	
	/**
	 * the angle in degrees
	 * 
	 * @return the angle in degrees
	 */
	public final int getAngle() {
		return angle;
	}

	/**
	 * the angle in degrees
	 * 
	 * @param angle the angle in degrees
	 */
	public final void setAngle(final int angle) {
        if ((angle < 0) || (angle > 359)) {
            throw new IllegalArgumentException("angle must be between 0 and 359 degrees inclusive");
        } 

        this.angle = angle;
	}

	// http://en.wikipedia.org/wiki/Trigonometry
	
	// TODO
	
	/**
	 * return a relative direction representing the angle at which a given direction is 
	 * incoming to this direction. For example, if a unit is facing a direction of 0 
	 * degrees, and the given direction representing incoming fire is 270 degrees, the 
	 * result will be that the incoming fire targets the unit at a 90 degree angle
	 * 
	 * @param direction the direction to get the relative angle for
	 */
	public final Direction getRelativeAngle(final Direction direction) {
		if (direction == null) {
            throw new IllegalArgumentException("direction cannot be null");
        } 
		
		return new Direction(getRelativeAngle(direction.getAngle()));
	}
	
	public final int getRelativeAngle(final int angle) {
		return getRelativeAngle(getAngle(), angle);
	}
	
	/**
	 * return a relative angle representing the angle at which a given angle is incoming 
	 * to this direction. For example, if a unit is facing an angle of 0 degrees, and the 
	 * given angle representing incoming fire is 270 degrees, the result will be that the
	 * incoming fire targets the unit at a 90 degree angle
	 * 
	 * @param angle1 the first angle 
	 * @param angle2 the second angle
	 */
	public static final int getRelativeAngle(final int angle1, int angle2) {
		if (angle1 == angle2) {
			return 180;
		} else if ((angle1 - angle2 == 180) || (angle2 - angle1 == 180)) {
			return 0;
		} else {
			if (angle1 > angle2) {
				return angle1 - angle2;
			} else {
				return 360 - (angle2 - angle1);
			}
		}
	}
	
	public String toString() {
		return String.valueOf(getAngle());
	}
	
	// if same, 	180
	// if opposite,	0
	
	// target	attacker	result
	// 45		45			180-
	// 45		135			270     360 - (a - t)
	// 45		225			0-
	// 45		315			90		360 - (a - t)
	// 135		45			90		t - a
	// 135		135			180-
	// 135		225			270		360 - (a - t)
	// 135		315			0-
	// 225		45			0-
	// 225		135			90		t - a
	// 225		225			180-
	// 225		315			270		360 - (a - t)
	// 315		45			270		t - a
	// 315		135			0-
	// 315		225			90		t - a
	// 315		315			180-
	
	// 0 - 0   = 180	0	= 0 + 0 + 180
	// 0 - 60  = 240	60	= 0 + 60 + 180
	// 0 - 90  = 270	90	= 0 + 90 + 180
	// 0 - 150 = 330	150	= 0 + 150 + 180
	// 0 - 180 = 0		180	= 0 + 180 - 180
	// 0 - 210 = 30		210	= 0 + 210 - 180
	// 0 - 270 = 90		270	= 0 + 270 - 180
	
	// = 360 - (210 + 90) + 120 * 0.5 = 120 
	// = 360 - (60 + 90) + 60 * 0.5 =  
	
	// 90 - 0   = 90	-90	= 90 + 0
	// 90 - 60  = 150	-30	= 90 + 60
	// 90 - 90  = 180	0	= 90 + 90
	// 90 - 150 = 60	60	= 90 + 150 - 180
	// 90 - 180 = 270	90	= 90 + 180
	// 90 - 210 = 120	120	= 90 + 210 - 180
	// 90 - 270 = 0		180	= 90 + 270 (- 360)
		
}
