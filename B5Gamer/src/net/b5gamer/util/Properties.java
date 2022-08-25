package net.b5gamer.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Properties {

	private Map<String, String> properties = new LinkedHashMap<String, String>();
	
	public String getProperty(String key) {
		return (String) properties.get(key);
	}
	
	public String setProperty(String key, String value) {
		return (String) properties.put(key, value);
	}
	
	public String removeProperty(String key) {
		return (String) properties.remove(key);
	}
	
	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	public boolean containsValue(String value) {
		return properties.containsValue(value);
	}
	
	public Set<String> keySet() {
		return properties.keySet();
	}
	
	public int size() {
		return properties.size();
	}
	
	public void clear() {
		properties.clear();
	}
	
}
