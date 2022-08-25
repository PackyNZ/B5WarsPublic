package net.b5gamer.util;

public abstract class Property {

	private final String name;
	
	protected Property(String name) {
        if ((name == null) || !(name.trim().length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
		
		this.name = name;		
	}
	
	public String getName() {
		return name;
	}	
	
	public abstract PropertyDataType getDataType();
	
	protected abstract boolean isValid(String value) throws IllegalArgumentException;
	
}
