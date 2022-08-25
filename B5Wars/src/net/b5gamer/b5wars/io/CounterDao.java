package net.b5gamer.b5wars.io;

import java.awt.Image;
import java.util.List;

public interface CounterDao {

	public static final String LOCATION  = "counters";
	public static final String EXTENSION = ".png";

	public List<String> findAll(final String unitName);
	
	public Image load(final String unitName) throws Exception;
	
}
