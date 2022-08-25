package net.b5gamer.b5wars.io.unit;

/**
 * Bean used for reading icons from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class IconBean {
	
	private int     x          = 0;
	private int     y          = 0;
	private int     index      = 1;
	private boolean mirror     = false;
	private int     rotation   = 0;
	private String  definition = null;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isMirror() {
		return mirror;
	}
	
	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public String getDefinition() {
		return definition;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
}
