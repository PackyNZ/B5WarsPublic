package net.b5gamer.b5wars.io;

import java.io.InputStream;

public interface SilhouetteDao {

	public static final String LOCATION  = "silhouettes";
	public static final String EXTENSION = ".svg";

	public InputStream load(final String hull) throws Exception;
	
}
