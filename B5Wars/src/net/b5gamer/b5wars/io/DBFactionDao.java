package net.b5gamer.b5wars.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import net.b5gamer.io.DBAccessor;

/**
 * An implementation for retrieving Faction data from a database  
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class DBFactionDao extends DBAccessor implements FactionDao {

    protected static final Logger logger = Logger.getLogger(DBFactionDao.class.getName());

    /**
     * create a new instance configured to connect to the default database
     */
	public DBFactionDao() {
		super();	
	}
    
	/**
	 * create a new instance configured to connect to the data source with the specified name
	 * 
	 * @param dataSourceName the name of the jdbc data source to retrieve connections from (e.g. jdbc/B5Wars)
	 */
	public DBFactionDao(final String dataSourceName) {
		super(dataSourceName);
	}
	
    /**
     * create a new instance configured to connect to the database using the specified parameters
     * 
     * @param dbDriver   the jdbc driver class name
     * @param dbUrl      a database url of the form jdbc:subprotocol:subname
     * @param dbUser     the database user on whose behalf connections will be made
     * @param dbPassword the user's password 
     */
	public DBFactionDao(final String dbDriver, final String dbUrl, final String dbUser, final String dbPassword) {
		super(dbDriver, dbUrl, dbUser, dbPassword);	
	}
	
	public List<String> findAll() {
		List<String> factions = null;
		
        StringBuffer sql = new StringBuffer();
        sql.append("Select Distinct(faction) As faction ");
        sql.append("From period_of_service ");
        sql.append("Order By faction");
        
        Connection connection = null;
        try {
        	connection = getConnection();
        	factions = (List<String>) (new QueryRunner()).query(connection, sql.toString(), new FactionResultSetHandler());
        } catch (SQLException e) {
            logger.error("Failed to retrieve Faction data", e);
            e.printStackTrace();
        } finally {
        	closeConnection(connection);
        }
        
        return factions;
	}

	/**
	 * A {@link org.apache.commons.dbutils.ResultSetHandler} implementation to handle Factions   
	 * 
	 * @author Alex Packwood (aka PackyNZ)
	 */	
	private final class FactionResultSetHandler implements ResultSetHandler<List<String>> {
	
		public final List<String> handle(final ResultSet resultSet) throws SQLException {
	        List<String> results = new ArrayList<String>();
	        
	        while (resultSet.next()) {
	        	results.add(resultSet.getString(1));
	        }
	        
	        return results;
	    }
		
	}
	
}
