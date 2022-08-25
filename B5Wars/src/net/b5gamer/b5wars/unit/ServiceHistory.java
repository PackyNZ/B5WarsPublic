/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents the service history of a particular unit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class ServiceHistory implements Serializable {

	private static final long serialVersionUID = 5260287645552989460L;

	private final List<PeriodOfService> periodsOfService = new ArrayList<PeriodOfService>(0); // periods of service

	/**
	 * the periods of service
	 * 
	 * @return the periods of service
	 */
	protected final List<PeriodOfService> getPeriodsOfService() {
		return periodsOfService;
	}

	/**
	 * the number of periods of service
	 * 
	 * @return the number of periods of service
	 */
	public int getPeriodOfServiceCount() {
		return getPeriodsOfService().size();
	}
	
    /**
     * an iterator of the periods of service
     * 
	 * @return an iterator of the periods of service
	 */
	public final Iterator<PeriodOfService> getPeriodOfServiceIterator() {
		return getPeriodsOfService().iterator();
	}
	
	/**
	 * add a period of service
	 * 
	 * @param periodOfService period of service to add
	 */	
	public void addPeriodOfService(final PeriodOfService periodOfService) {
		if (periodOfService == null) {
            throw new IllegalArgumentException("periodOfService cannot be null");
        } 
		if (doesRangeOverlap(periodOfService)) {
            throw new IllegalArgumentException("periodOfService cannot overlap with an existing " + 
            		PeriodOfService.class.getSimpleName());
		}

		getPeriodsOfService().add(periodOfService);
	}	
	
	/**
	 * return whether the range for the specified period of service overlaps that of an existing
	 * period of service
	 * 
	 * @param  periodOfService period of service to check the range of
	 * @return                 whether the range for the specified period of service overlaps 
	 *                         that of an existing period of service
	 */	
	private final boolean doesRangeOverlap(final PeriodOfService periodOfService) {
		boolean result = false;
		
		if (periodOfService == null) {
            throw new IllegalArgumentException("periodOfService cannot be null");
        } 

		for (Iterator<PeriodOfService> iterator = getPeriodsOfService().iterator(); iterator.hasNext();) {
			PeriodOfService period = iterator.next();
			if ((periodOfService.getFaction().equalsIgnoreCase(period.getFaction())) && 
					(periodOfService.overlaps(period))) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
}
