package net.b5gamer.b5wars.io;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;

public interface UnitDao {

	public static final String LOCATION  = "units";
	public static final String EXTENSION = ".xml";

	public Unit load(final UnitID id) throws Exception;

}
