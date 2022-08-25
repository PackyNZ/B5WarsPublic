package net.b5gamer.util;

public enum PropertyDataType {
	
	STRING  ("String"),
	INTEGER ("Integer"),
	DOUBLE  ("Double"),
	BOOLEAN ("Boolean");
	
	private final String name;
        
	private PropertyDataType(final String name) {
        if ((name == null) || !(name.trim().length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
		
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}