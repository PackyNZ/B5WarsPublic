package net.b5gamer.b5wars.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import net.b5gamer.io.DBConnectionHelper;
import net.b5gamer.io.FileUtil;
import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.unit.PeriodOfService;
import net.b5gamer.b5wars.unit.Unit;

public class ImportUnits {

	public static void main(String[] args) throws FileNotFoundException {
	    String dbDriver   = "com.mysql.jdbc.Driver";       
	    String dbUrl      = "jdbc:mysql://localhost:3306/b5wars";         
	    String dbUser     = "B5W";         
	    String dbPassword = "4Ever";   
	    DBConnectionHelper.setConnectionProperties(dbDriver, dbUrl, dbUser, dbPassword);

	    // generate sql
	    StringBuilder unitSql = new StringBuilder();
	    unitSql.append("Insert Into unit (type, model, version, definition, modified_by) ");
	    unitSql.append("values (?, ?, ?, ?, 'Swordsman')");
	    
	    StringBuilder posSql = new StringBuilder();
	    posSql.append("Insert Into period_of_service (type, model, version, faction, in_service_from, in_service_until) ");
	    posSql.append("values (?, ?, ?, ?, ?, ?)");
	    
	    Connection connection = null;
        try {
        	connection = DBConnectionHelper.getConnection();
        	QueryRunner run = new QueryRunner();
        	
    	    // remove existing units
        	run.update(connection, "Delete From Unit");

    	    // import local units
    		List<File> files = FileUtil.listFiles(new File(UnitXMLReader.DEFAULT_DIRECTORY), "xml", true);
    		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
    			File file = iterator.next();
    			
    			try {
    				Unit unit = UnitXMLReader.read(new BufferedInputStream(new FileInputStream(file)));
    				
    				if (unit != null) {
    	    	    	run.update(connection, unitSql.toString(), new Object[] {unit.getName(), unit.getModel(), unit.getVersion(), 
    	    	    		new BufferedInputStream(new FileInputStream(file))});

    					for (Iterator<PeriodOfService> iter = unit.getServiceHistory().getPeriodOfServiceIterator(); iter.hasNext();) {
    						PeriodOfService pos = iter.next();
    						
    		    	    	run.update(connection, posSql.toString(), new Object[] {unit.getName(), unit.getModel(), unit.getVersion(), 
    		    	    		pos.getFaction(), pos.getInService(), pos.getEndedService()});
    					}
    				}
    			} catch (Exception e) {
    	            System.out.println("Invalid Unit Definition: " + file.getName());
    				e.printStackTrace();
				}
    		}		      
        } catch (SQLException e) {
            System.out.println("Failed to update Unit data: " + e.getMessage());
            e.printStackTrace();
        } finally {
        	DBConnectionHelper.closeConnection(connection);
        }	    	    
	}

}
