package net.b5gamer.b5wars.io;

import java.io.InputStream;
import java.util.List;

public interface IconDao {

	public static final String LOCATION  = "icons";
	public static final String EXTENSION = ".svg";
	
	public List<String> findAll(final String filenamePrefix, final int damageBoxes);
	
	public InputStream load(final String filename) throws Exception;
	
}
