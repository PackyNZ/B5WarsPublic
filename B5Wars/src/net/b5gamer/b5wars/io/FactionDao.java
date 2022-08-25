package net.b5gamer.b5wars.io;

import java.util.List;

/**
 * An interface for retrieving Faction data  
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface FactionDao {
	
	/**
	 * a list of all faction names
	 * 
	 * @return a list of all faction names
	 */
	public List<String> findAll();

}
