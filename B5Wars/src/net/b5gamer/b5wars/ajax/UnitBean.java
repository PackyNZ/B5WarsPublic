package net.b5gamer.b5wars.ajax;

import net.b5gamer.b5wars.unit.Unit;

public class UnitBean {

	private String name;
	private String model;
	private int    version;

	public UnitBean() {
	}
	
	public UnitBean(final Unit unit) {
		this.name    = unit.getName();
		this.model   = unit.getModel();
		this.version = unit.getVersion();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();

		fullName.append(getName());
		if ((getModel() != null) && (getModel().length() > 0)) {
			fullName.append(" (").append(getModel()).append(")");
		}
		
		return fullName.toString();
	}
	
}
