package net.b5gamer.b5wars.unit;

public class UnitID {

	private final String name;    // unit name
	private final String model;   // unit model
	private final int    version; // version number
	
	/**
	 * @param unit unit to create the id for
	 */
	public UnitID(final Unit unit) {
   		this(unit.getName(), unit.getModel(), unit.getVersion());    	
	}
	
	/**
	 * @param name    unit name
	 * @param model   unit model
	 * @param version version number
	 */
	public UnitID(final String name, final String model, final int version) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
		
		this.name    = name;
		this.model   = ((model == null) || !(model.trim().length() > 0)) ? "" : model;
		this.version = version;
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
	 * the unit model
	 * 
	 * @return the unit model
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * the version number
	 * 
	 * @return the version number
	 */
	public int getVersion() {
		return version;
	}

	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();

		description.append(getName());
		if ((getModel() != null) && (getModel().length() > 0)) {
			description.append(" (").append(getModel()).append(")");
		}
		description.append(" Version ").append(getVersion());

		return description.toString();
	}	
	
}
