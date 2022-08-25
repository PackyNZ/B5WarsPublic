package net.b5gamer.b5wars.io;

import java.io.InputStream;

public interface ResourceDao {

	public static final String LOCATION = "resources";
	
	public InputStream load(final String filename) throws Exception;
	
}
