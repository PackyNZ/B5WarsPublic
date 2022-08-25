/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.map.Direction;
import net.b5gamer.map.Location;

/**
 * A Unit represents any type of space faring vessel 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class Unit implements Serializable {

	private static final long serialVersionUID = 4540337668066513259L;

	private       String          givenName       = null; // player provided name of the unit instance
	private final String          name;                   // unit name
	private final String          model;                  // unit model
	private final String          hull;                   // base hull the unit is built on
	private final int             version;                // version number
	private final String          author;                 // author of the unit 
	private final String          source;                 // source of the unit
	private final int             pointValue;             // combat point cost of the unit
	private final int             rammingFactor;          // factor for ramming other units
	private final int             fwdAftDefense;          // defense when attacked from fore or aft
	private final int             stbPortDefense;         // defense when attacked from starboard or port
	private final int             initiativeModifier;     // modifier to initiative
	private       int             initiative = 0;         // units initiative for the round, including modifier
	private final ServiceHistory  serviceHistory;         // the units service history 
	private       PeriodOfService periodOfService = null; // period of service currently employed
	private final List<Trait>     traits          = new ArrayList<Trait>(0); // the units traits 
	private       Location        location        = null; // the location of the unit on the map
	private       Direction       facing          = null; // direction the unit is facing
	private transient Image       counter;                // counter to display for the unit
	
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
	 */
	protected Unit(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
        if ((hull == null) || !(hull.length() > 0)) {
            throw new IllegalArgumentException("hull cannot be null or blank");
        } 
        if (!(version > 0)) {
            throw new IllegalArgumentException("version must be a positive number");
        } 
        if (!(pointValue > 0)) {
            throw new IllegalArgumentException("pointValue must be a positive number");
        } 
        if (rammingFactor < 0) {
            throw new IllegalArgumentException("rammingFactor cannot be a negative number");
        } 
//        if ((serviceHistory == null) || !(serviceHistory.getPeriodsOfService().size() > 0)) {
//            throw new IllegalArgumentException("serviceHistory cannot be null or empty");
//        } 
		
		this.name               = name;
		this.model              = ((model == null) || !(model.trim().length() > 0)) ? null : model;
		this.hull               = hull;
		this.version            = version;
		this.author             = ((author == null) || !(author.trim().length() > 0)) ? null : author;
		this.source             = ((source == null) || !(source.trim().length() > 0)) ? null : source;	
		this.pointValue         = pointValue;
		this.rammingFactor      = rammingFactor;
		this.fwdAftDefense      = fwdAftDefense;
		this.stbPortDefense     = stbPortDefense;
		this.initiativeModifier = initiativeModifier;
		this.serviceHistory     = serviceHistory;
		
		if ((serviceHistory != null) && (serviceHistory.getPeriodsOfService().size() > 0)) {
			this.periodOfService = serviceHistory.getPeriodsOfService().get(0);
		}
		
		if ((traits != null) && (traits.length > 0)) {
			this.traits.addAll(Arrays.asList(traits));
		}
	}
	
	/**
	 * the type of unit
	 * 
	 * @return the type of unit
	 */
	public abstract String getType();

	/**
	 * the units size
	 * 
	 * @return the units size
	 */
	public abstract Size getSize();

	/**
	 * whether the unit blocks line of sight
	 * 
	 * @return whether the unit blocks line of sight
	 */
	public boolean getBlocksLineOfSight() {
		return getSize() == Size.ENORMOUS;
	}
	
	/**
	 * the player provided name of the unit instance
	 * 
	 * @return the player provided name of the unit instance
	 */
	public String getGivenName() {
		return (givenName != null) ? givenName : getFullName();
	}

	/**
	 * the player provided name of the unit instance
	 * 
	 * @param name the player provided name of the unit instance
	 */
	public void setGivenName(final String name) {
        if ((name == null) || !(name.trim().length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 

        this.givenName = name;
	}
	
	/**
	 * the unit name
	 * 
	 * @return the unit name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * the full name for the unit
	 * 
	 * @return the full name for the unit
	 */	
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();

		fullName.append(getName());
		if ((getModel() != null) && (getModel().length() > 0)) {
			fullName.append(" (").append(getModel()).append(")");
		}
		
		return fullName.toString();
	}
	
	/**
	 * the unit model
	 * 
	 * @return the unit model
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * the base hull the unit is built on
	 * 
	 * @return base hull the unit is built on
	 */
	public String getHull() {
		return hull;
	}
	
	/**
	 * the version number
	 * 
	 * @return the version number
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * the author of the unit
	 * 
	 * @return the author of the unit
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * whether the unit is official (e.g. published by Agents Of Gaming)
	 * 
	 * @return whether the unit is official (e.g. published by Agents Of Gaming)
	 */
	public boolean isOfficial() {
		return "AOG".equalsIgnoreCase(getAuthor());
	}
	
	/**
	 * the source of the unit
	 * 
	 * @return the source of the unit
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * the combat point cost of the unit
	 * 
	 * @return the combat point cost of the unit
	 */
	public int getPointValue() {
		return pointValue;
	}

	/**
	 * the factor for ramming other units
	 * 
	 * @return the factor for ramming other units
	 */
	public int getRammingFactor() {
		return rammingFactor;
	}

	/**
	 * the defense when attacked from fore or aft
	 * 
	 * @return the defense when attacked from fore or aft
	 */
	public int getFwdAftDefense() {
		return fwdAftDefense;
	}

	/**
	 * the defense when attacked from starboard or port
	 * 
	 * @return the defense when attacked from starboard or port
	 */
	public int getStbPortDefense() {
		return stbPortDefense;
	}

	/**
	 * the modifier to initiative
	 * 
	 * @return the modifier to initiative
	 */
	public int getInitiativeModifier() {
		return initiativeModifier;
	}

	/**
	 * the units initiative for the round, including modifier
	 * 
	 * @return the units initiative for the round, including modifier
	 */
	public int getInitiative() {
		return initiative;
	}

	/**
	 * the units initiative for the round, including modifier
	 * 
	 * @param initiative the units initiative for the round, including modifier
	 */
	public void setInitiative(final int initiative) {
		this.initiative = initiative;
	}
	
	/**
	 * the units service history 
	 * 
	 * @return the units service history  
	 */	
	public ServiceHistory getServiceHistory() {
		return serviceHistory;
	}

	/**
	 * the period of service currently employed
	 * 
	 * @return the period of service currently employed
	 */
	public PeriodOfService getPeriodOfService() {
		return periodOfService;
	}

	/**
	 * the period of service currently employed
	 * 
	 * @param periodOfService the period of service currently employed
	 */
	public void setPeriodOfService(final PeriodOfService periodOfService) {
		if ((periodOfService != null) && (!getServiceHistory().getPeriodsOfService().contains(periodOfService))) {
            throw new IllegalArgumentException("periodOfService is not valid for this unit");			
		}
		
		this.periodOfService = periodOfService;
	}
	
	/**
	 * all the units traits 
	 * 
	 * @return all the units traits 
	 */
	protected List<Trait> getTraits() {
		return traits;
	}
	
	/**
	 * the number of traits
	 * 
	 * @return the number of traits
	 */
	public int getTraitCount() {
		return getTraits().size();
	}
	
    /**
     * an iterator of all the units traits 
     * 
	 * @return an iterator of all the units traits 
	 */
	public Iterator<Trait> getTraitIterator() {
		return getTraits().iterator();
	}
	
	/**
	 * whether the unit has a given trait
	 * 
	 * @param  trait the trait to check 
	 * @return       return whether the unit has a given trait
	 */
	public boolean hasTrait(final Trait trait) {
		return getTraits().contains(trait);
	}
	
	/**
	 * whether the unit has a given trait
	 * 
	 * @param  trait name of the trait to check 
	 * @return       return whether the unit has a given trait
	 */
	public boolean hasTrait(final String trait) {
		boolean result = false;

        for (Iterator<Trait> iterator = getTraits().iterator(); iterator.hasNext();) {
            if (iterator.next().getName().equalsIgnoreCase(trait)) {
                result = true;
                break;
            }                
    	} 
    	
		return result;
	}

	/**
	 * the location of the unit on the map
	 * 
	 * @return the location of the unit on the map
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * the location of the unit on the map
	 * 
	 * @param location the location of the unit on the map
	 */
	public void setLocation(final Location location) {
		this.location = location;
	}

	/**
	 * the direction the unit is facing
	 * 
	 * @return the direction the unit is facing
	 */
	public Direction getFacing() {
		return facing;
	}

	/**
	 * the direction the unit is facing
	 * 
	 * @param facing the direction the unit is facing
	 */
	public void setFacing(final Direction facing) {
		this.facing = facing;
	}
	
	/**
	 * the counter to display for the unit
	 * 
	 * @return the counter to display for the unit
	 */
	public Image getCounter() {
		if (counter == null) {
			synchronized (this) {				
				if (counter == null) {
					try {
						counter = DataManager.getCounterDao().load(getFullName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return counter;
	}
	
	/**
	 * whether the unit is destroyed
	 * 
	 * @return whether the unit is destroyed
	 */
	public abstract boolean isDestroyed();
	
	/**
	 * handle any end of turn actions 
	 */
	public abstract void handleEndOfTurnActions();
	
	/**
	 * availability information for the unit
	 * 
	 * @return availability information for the unit
	 */	
	public String getAvailabilityInfo() {
		StringBuilder availabilityInfo = new StringBuilder();

		if ((getPeriodOfService() != null) && (getPeriodOfService().getAvailability() != Availability.BASE)) {
			availabilityInfo.append(getHull());
			availabilityInfo.append(" Variant (").append(getPeriodOfService().getAvailability().getName()).append(")");
		}
		
		return availabilityInfo.toString();
	}
	
	/**
	 * version information for the unit
	 * 
	 * @return version information for the unit
	 */
	public String getVersionInfo() {
		StringBuilder versionInfo = new StringBuilder();

		versionInfo.append("Version ").append(getVersion());
		
		if ((getAuthor() != null) || (getSource() != null)) {
			versionInfo.append(": ");
		}
		
		if (getAuthor() != null) {
			versionInfo.append(getAuthor());	

			// TODO use / anyway if both author and source aren't supplied?
			if (getSource() != null) {
				versionInfo.append("/");		
			}
		}
		
		if (getSource() != null) {
			versionInfo.append(getSource());	
		}
		
		return versionInfo.toString();
	}
	
	/**
	 * description of the unit
	 * 
	 * @return description of the unit
	 */
	public String getDescription() {
		StringBuilder description = new StringBuilder();

		description.append(getFullName());		
		if ((getPeriodOfService() != null) && (getPeriodOfService().getAvailability() != Availability.BASE)) {
			description.append(", ").append(getAvailabilityInfo());
		}
		description.append(", ").append(getVersionInfo());		
		
		return description.toString();
	}
	
	@Override
	public String toString() {
		return getGivenName();
	}
	
}
