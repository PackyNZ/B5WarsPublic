package net.b5gamer.util;

public class StringProperty extends Property {

	private final boolean allowNull;
	private final boolean allowBlank;
	
	public StringProperty(String name) {
		this(name, false, false);
	}

	public StringProperty(String name, boolean allowNull, boolean allowBlank) {
		super(name);
		
		this.allowNull  = allowNull;
		this.allowBlank = allowBlank;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	@Override
	public PropertyDataType getDataType() {
		return PropertyDataType.STRING;
	}

	@Override
	protected boolean isValid(String value) throws IllegalArgumentException {
		if (value == null) {
			if (isAllowNull()) {
				return true;
			} else {
				throw new IllegalArgumentException("value cannot be null");				
			}
		} else {
			if (!isAllowBlank() && !(value.trim().length() > 0)) {
				throw new IllegalArgumentException("value cannot be blank");
			} else {
				return true;
			}
		}
	}

}
