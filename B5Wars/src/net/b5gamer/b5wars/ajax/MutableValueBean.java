package net.b5gamer.b5wars.ajax;

public class MutableValueBean {

	private int base    = 0;
	private int current = 0;
	
	public MutableValueBean() {
	}

	public MutableValueBean(final int base, final int current) {
		this.base    = base;
		this.current = current;
	}

	public final int getBase() {
		return base;
	}

	public final void setBase(final int base) {
		this.base = base;
	}

	public final int getCurrent() {
		return current;
	}

	public final void setCurrent(final int current) {
		this.current = current;
	}
	
}
