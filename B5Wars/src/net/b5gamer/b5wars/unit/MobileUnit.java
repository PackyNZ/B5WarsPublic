/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

import net.b5gamer.map.Direction;

/**
 * The MobileUnit interface denotes a {@link net.b5gamer.b5wars.unit.Unit} as having
 * the capability to manoeuvre itself through space
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface MobileUnit {
	
	/**
	 * the formatted speed based thrust cost to make a turn
	 * 
	 * @return the formatted speed based thrust cost to make a turn
	 */
	public String getTurnCostFormatted();
	
	/**
	 * the speed based thrust cost to make a turn
	 * 
	 * @return the speed based thrust cost to make a turn
	 */
	public double getTurnCost();

	/**
	 * the formatted speed based mandatory movement delay between making turns
	 * 
	 * @return the formatted speed based mandatory movement delay between making turns
	 */
	public String getTurnDelayFormatted();
	
	/**
	 * the speed based mandatory movement delay between making turns
	 * 
	 * @return the speed based mandatory movement delay between making turns
	 */
	public double getTurnDelay();

	/**
	 * the thrust required to alter speed
	 * 
	 * @return the thrust required to alter speed
	 */
	public int getAccelDecelCost();

	/**
	 * whether the unit is capable of making a pivot
	 * 
	 * @return whether the unit is capable of making a pivot
	 */
	public boolean isPivotCapable();

	/**
	 * the formatted cost to make a pivot
	 * 
	 * @return the formatted formatted cost to make a pivot
	 */
	public String getPivotCostFormatted();
	
	/**
	 * the thrust required to start a pivot
	 * 
	 * @return the thrust required to start a pivot
	 */
	public int getPivotStartCost();

	/**
	 * the thrust required to stop a pivot
	 * 
	 * @return the thrust required to stop a pivot
	 */
	public int getPivotStopCost();

	/**
	 * whether the unit is capable of making a roll
	 * 
	 * @return whether the unit is capable of making a roll
	 */
	public boolean isRollCapable();
	
	/**
	 * the formatted cost to make a roll
	 * 
	 * @return the formatted formatted cost to make a roll
	 */
	public String getRollCostFormatted();
	
	/**
	 * the thrust required to start a roll
	 * 
	 * @return the thrust required to start a roll
	 */
	public int getRollStartCost();

	/**
	 * the thrust required to stop a roll
	 * 
	 * @return the thrust required to stop a roll
	 */
	public int getRollStopCost();
	
	/**
	 * the units direction of motion
	 * 
	 * @return the units direction of motion
	 */
	public Direction getDirection();

	/**
	 * the units direction of motion
	 * 
	 * @param direction the units direction of motion
	 */
	public void setDirection(final Direction direction);

	/**
	 * the units current speed
	 * 
	 * @return the units current speed
	 */
	public int getSpeed();

	/**
	 * the units current speed
	 * 
	 * @param speed the units current speed
	 */
	public void setSpeed(final int speed);
	
	/**
	 * the thrust spent on performing an extended turn
	 * 
	 * @return the thrust spent on performing an extended turn
	 */
	public int getExtendedTurnThrust();

	/**
	 * the thrust spent on performing an extended turn
	 * 
	 * @param extendedTurnThrust the thrust spent on performing an extended turn
	 */
	public void setExtendedTurnThrust(final int extendedTurnThrust);

	/**
	 * the amount of turn delay unit has yet to fulfil
	 * 
	 * @return the amount of turn delay unit has yet to fulfil
	 */
	public int getPendingTurnDelay();

	/**
	 * the amount of turn delay unit has yet to fulfil
	 * 
	 * @param pendingTurnDelay the amount of turn delay unit has yet to fulfil
	 */
	public void setPendingTurnDelay(final int pendingTurnDelay);

	// TODO change below to status enums?
	
	/**
	 * the speed and direction of pivot (0 = not pivoting, 2 = 2 hex facings clockwise, 
	 * -1 = 1 hex facing counterclockwise)
	 * 
	 * @return the speed and direction of pivot (0 = not pivoting, 2 = 2 hex facings clockwise, 
	 *         -1 = 1 hex facing counterclockwise)
	 */
	public int getPivot();

	/**
	 * the speed and direction of pivot (0 = not pivoting, 2 = 2 hex facings clockwise, 
	 *              -1 = 1 hex facing counterclockwise)
	 *              
	 * @param pivot the speed and direction of pivot (0 = not pivoting, 2 = 2 hex facings clockwise, 
	 *              -1 = 1 hex facing counterclockwise)
	 */
	public void setPivot(final int pivot);
	
	/**
	 * whether the unit is currently rolled
	 * 
	 * @return whether the unit is currently rolled
	 */
	public boolean isRolled();

	/**
	 * whether the unit is currently rolled
	 * 
	 * @param rolled whether the unit is currently rolled
	 */
	public void setRolled(final boolean rolled);

	/**
	 * whether the unit is currently performing a roll
	 * 
	 * @return whether the unit is currently performing a roll
	 */
	public boolean isRolling();

	/**
	 * whether the unit is currently performing a roll
	 * 
	 * @param rolling whether the unit is currently performing a roll
	 */
	public void setRolling(final boolean rolling);

	/**
	 * whether the unit is skin dancing
	 * 
	 * @return whether the unit is skin dancing
	 */
	public boolean isSkinDancing();

	/**
	 * whether the unit is skin dancing
	 * 
	 * @param skinDancing whether the unit is skin dancing
	 */
	public void setSkinDancing(final boolean skinDancing);
	
}
