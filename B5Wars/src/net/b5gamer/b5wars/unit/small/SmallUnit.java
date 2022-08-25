/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.small;

import net.b5gamer.b5wars.unit.MobileUnit;
import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.map.Direction;

/**
 * A SmallUnit represents smaller craft such as fighters and shuttles
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class SmallUnit extends Unit implements MobileUnit {

	private static final String NOT_APPLICABLE = "N/A";
	
	private static final long serialVersionUID = 5209444770695866602L;

	private final String    turnCostFormatted;          // formatted speed based thrust cost to make a turn 
	private final double    turnCost;                   // speed based thrust cost to make a turn 
	private final String    turnDelayFormatted;         // formatted speed based mandatory movement delay between making turns 
	private final double    turnDelay;                  // speed based mandatory movement delay between making turns 
	private final int       accelDecelCost;             // thrust required to alter speed 
	private final String    pivotCostFormatted;         // formatted thrust required to make a pivot 
	private final int       pivotStartCost;             // thrust required to start a pivot 
	private final int       pivotStopCost;              // thrust required to stop a pivot  
	private final String    rollCostFormatted;          // formatted thrust required to make a roll 
	private final int       rollStartCost;              // thrust required to start a roll  
	private final int       rollStopCost;               // thrust required to stop a roll  
	private       Direction direction          = null;  // units direction of motion
	private       int       speed              = 0;     // units current speed 
	private       int       extendedTurnThrust = 0;     // thrust spent on performing an extended turn
	private       int       pendingTurnDelay   = 0;     // amount of turn delay unit has yet to fulfil
	// TODO change to status enums?
	private       int       pivot              = 0;     // speed and direction of pivot (0 = not pivoting, 2 = 2 hex facings clockwise, -1 = 1 hex facing counterclockwise)
	private       boolean   rolled             = false; // whether the unit is currently rolled
	private       boolean   rolling            = false; // whether the unit is currently performing a roll
	private       boolean   skinDancing        = false; // whether the unit is skin dancing
	
	/**
	 * @param name               unit name
	 * @param model              unit model
	 * @param hull               base hull the unit is built on
	 * @param version            version number
	 * @param author             author of the unit
	 * @param source             source of the unit
	 * @param pointValue         combat point cost of the unit
	 * @param rammingFactor      factor for ramming other units
	 * @param fwdAftDefense      defense when attacked from fore or aft
	 * @param stbPortDefense     defense when attacked from starboard or port
	 * @param initiativeModifier modifier to initiative
	 * @param serviceHistory     the units service history 
	 * @param traits             the units traits
	 * @param turnCostFormatted  raw/unparsed thrust cost to make a turn 
	 * @param turnDelayFormatted raw/unparsed mandatory movement delay between making turns  
	 * @param accelDecelCost     thrust required to alter speed
	 * @param pivotCostFormatted raw/unparsed thrust required to make a pivot
	 * @param rollCostFormatted  raw/unparsed thrust required to make a roll
	 */
	protected SmallUnit(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits, final String turnCostFormatted, 
			final String turnDelayFormatted, final int accelDecelCost, final String pivotCostFormatted, 
			final String rollCostFormatted) {
		super(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits);
		
        if ((turnCostFormatted == null) || !(turnCostFormatted.trim().length() > 0)) {
            throw new IllegalArgumentException("turnCost cannot be null or blank");
        } 
        double turnCost = parseTurnDouble("turnCost", turnCostFormatted);
        if (turnCost < 0) {
            throw new IllegalArgumentException("turnCost cannot be a negative number"); 
        } 
        
        if ((turnDelayFormatted == null) || !(turnDelayFormatted.trim().length() > 0)) {
            throw new IllegalArgumentException("turnDelay cannot be null or blank");
        } 
        double turnDelay = parseTurnDouble("turnDelay", turnDelayFormatted);        
        if (turnDelay < 0) {
            throw new IllegalArgumentException("turnDelay cannot be a negative number");
        }  
        
        if (accelDecelCost < 0) {
            throw new IllegalArgumentException("accelDecelCost cannot be a negative number");
        } 
        
        int pivotStartCost = 0;
        int pivotStopCost  = 0;
        String pivotCost = pivotCostFormatted;
        if (pivotCost != null) {
        	pivotCost = pivotCost.trim();
        	if (!(pivotCost.length() > 0) || (pivotCost.toUpperCase().equals(NOT_APPLICABLE))) {
        		pivotCost = null;
        	}
        	
            if (pivotCost != null) {
	    		int[] costs = parseCostIntegers("pivotCost", pivotCostFormatted);
	    		if ((costs != null) && (costs.length > 0)) {
	    			pivotStartCost = costs[0];
	    			pivotStopCost  = (costs.length == 2) ? costs[1] : 0;
	    		}
	        	
	   	        if (pivotStartCost < 0) {
		            throw new IllegalArgumentException("pivotStartCost cannot be a negative number");
		        } 
		        if (pivotStopCost < 0) {
		            throw new IllegalArgumentException("pivotStopCost cannot be a negative number");
		        } 
            }
        }
        
        int rollStartCost = 0;
        int rollStopCost  = 0;
        String rollCost = rollCostFormatted;
        if (rollCost != null) {
        	rollCost = rollCost.trim();
        	if (!(rollCost.length() > 0) || (rollCost.toUpperCase().equals(NOT_APPLICABLE))) {
        		rollCost = null;
        	}
        	
            if (rollCost != null) {
	    		int[] costs = parseCostIntegers("rollCost", rollCost);
	    		if ((costs != null) && (costs.length > 0)) {
	    			rollStartCost = costs[0];
	    			rollStopCost  = (costs.length == 2) ? costs[1] : 0;
	    		}
	        	
	   	        if (rollStartCost < 0) {
		            throw new IllegalArgumentException("rollStartCost cannot be a negative number");
		        } 
		        if (rollStopCost < 0) {
		            throw new IllegalArgumentException("rollStopCost cannot be a negative number");
		        } 
            }
        }
        
		this.turnCostFormatted  = turnCostFormatted.trim();
		this.turnCost           = turnCost;
		this.turnDelayFormatted = turnDelayFormatted.trim();
		this.turnDelay          = turnDelay;
		this.accelDecelCost     = accelDecelCost;
		this.pivotCostFormatted = (pivotCost != null) ? pivotCost : null;
		this.pivotStartCost     = pivotStartCost;
		this.pivotStopCost      = pivotStopCost;
		this.rollCostFormatted  = (rollCost != null) ? rollCost : null;
		this.rollStartCost      = rollStartCost;
		this.rollStopCost       = rollStopCost;		
	}
		
	public String getTurnCostFormatted() {
		return turnCostFormatted;
	}

	public double getTurnCost() {
		return turnCost;
	}

	public String getTurnDelayFormatted() {
		return turnDelayFormatted;
	}

	public double getTurnDelay() {
		return turnDelay;
	}

	public int getAccelDecelCost() {
		return accelDecelCost;
	}

	public boolean isPivotCapable() {
		return (getPivotCostFormatted() != null);
	}
	
	public String getPivotCostFormatted() {
		return pivotCostFormatted;
	}
	
	public int getPivotStartCost() {
		return pivotStartCost;
	}

	public int getPivotStopCost() {
		return pivotStopCost;
	}

	public boolean isRollCapable() {
		return (getRollCostFormatted() != null);
	}

	public String getRollCostFormatted() {
		return rollCostFormatted;
	}
	
	public int getRollStartCost() {
		return rollStartCost;
	}

	public int getRollStopCost() {
		return rollStopCost;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	public int getExtendedTurnThrust() {
		return extendedTurnThrust;
	}

	public void setExtendedTurnThrust(final int extendedTurnThrust) {
		this.extendedTurnThrust = extendedTurnThrust < 0 ? 0 : extendedTurnThrust;
	}

	public int getPendingTurnDelay() {
		return pendingTurnDelay;
	}

	public void setPendingTurnDelay(final int pendingTurnDelay) {
		this.pendingTurnDelay = pendingTurnDelay < 0 ? 0 : pendingTurnDelay;
	}

	public int getPivot() {
		return pivot;
	}

	public void setPivot(final int pivot) {
		this.pivot = pivot;
	}
	
	public boolean isRolled() {
		return rolled;
	}

	public void setRolled(final boolean rolled) {
		this.rolled = rolled;
	}

	public boolean isRolling() {
		return rolling;
	}

	public void setRolling(final boolean rolling) {
		this.rolling = rolling;
	}

	public boolean isSkinDancing() {
		return skinDancing;
	}

	public void setSkinDancing(final boolean skinDancing) {
		this.skinDancing = skinDancing;
	}
	
	/**
	 * parse a turn cost or turn delay in the format of "1/2" or "1" into a double
	 * 
	 * @param  name  name of the value being parsed
	 * @param  value value to be parsed
	 * @return       parsed value
	 */
	protected double parseTurnDouble(final String name, final String value) {
		double parsedValue = 0.0;
		
		if (value.contains("/")) {
			String[] values = value.split("/");
			if (values.length == 2) {
		        try {
		        	parsedValue = Double.valueOf(values[0]) / Double.valueOf(values[1]);
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException(name + " must be a number in the form of 1/2 or 1");
		        } 
			} else {
				throw new IllegalArgumentException(name + " must be a number in the form of 1/2 or 1");
			}
		} else {			
	        try {
				parsedValue = Double.valueOf(value);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException(name + " must be a number in the form of 1/2 or 1");
	        } 
		}
		
		return parsedValue;
	}
	
	/**
	 * parse a pivot cost or roll cost in the format of "2+2" or "1" into it's integer components
	 * 
	 * @param  name  name of the value being parsed
	 * @param  value value to be parsed
	 * @return       parsed value, or null if it can't be determined
	 */	
	protected int[] parseCostIntegers(final String name, final String value) {
		int[] parsedValues = null;

		if (value.contains("+")) {
			String[] values = value.split("\\+");
			if (values.length == 2) {
		        try {
		        	parsedValues = new int[] {Integer.valueOf(values[0]), Integer.valueOf(values[1])};
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException(name + " must be integers in the form of 2+2 or 1, or N/A");
		        } 
			} else {
				throw new IllegalArgumentException(name + " must be integers in the form of 2+2 or 1, or N/A");
			}
		} else {			
	        try {
	        	parsedValues = new int[] {Integer.valueOf(value)};
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException(name + " must be integers in the form of 2+2 or 1, or N/A");
	        } 
		}
		
		return parsedValues;
	}
	
}
