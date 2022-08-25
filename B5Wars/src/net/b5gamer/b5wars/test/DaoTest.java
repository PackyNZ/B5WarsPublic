package net.b5gamer.b5wars.test;

import java.util.Iterator;
import java.util.List;

import net.b5gamer.io.DBConnectionHelper;
import net.b5gamer.b5wars.io.FactionDao;
import net.b5gamer.b5wars.io.DBFactionDao;
import net.b5gamer.b5wars.io.FileFactionDao;

public class DaoTest {

	public static void main(String[] args) {
	    String dbDriver   = "com.mysql.jdbc.Driver";       
	    String dbUrl      = "jdbc:mysql://localhost:3306/b5wars";         
	    String dbUser     = "B5W";         
	    String dbPassword = "4Ever";   
	    DBConnectionHelper.setConnectionProperties(dbDriver, dbUrl, dbUser, dbPassword);

	    // Factions from DB
		FactionDao factionDBDao = new DBFactionDao();
		List<String> factions = factionDBDao.findAll();
		
		System.out.println("FactionDBDao.findAll():");
		for (Iterator<String> iterator = factions.iterator(); iterator.hasNext();) {
			System.out.println("    " + iterator.next());
		}
		
		System.out.println("");

		// Factions from Files
		FactionDao factionFileDao = new FileFactionDao();
		factions = factionFileDao.findAll();
		
		System.out.println("FactionFileDao.findAll():");
		for (Iterator<String> iterator = factions.iterator(); iterator.hasNext();) {
			System.out.println("    " + iterator.next());
		}
		
	}

}
