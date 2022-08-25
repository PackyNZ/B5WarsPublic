package net.b5gamer.b5wars.io;

import java.io.InputStream;

public interface WeaponDataDao {

	public static final String LOCATION  = "weapondata";
	public static final String EXTENSION = ".svg";

	public InputStream load(final String weaponType) throws Exception;
	
}
