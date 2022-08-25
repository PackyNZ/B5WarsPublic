/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.io;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * This class provides methods for accessing database connections
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public final class DBConnectionHelper {

    private static final Logger logger = Logger.getLogger(DBConnectionHelper.class.getName());

    private static String dataSourceName = null; // name of the default jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
    private static String driver         = null; // default jdbc driver class name
    private static String url            = null; // a default database url of the form jdbc:subprotocol:subname
    private static String user           = null; // the default database user on whose behalf connections will be made
    private static String password       = null; // the default user's password 
	
    private DBConnectionHelper() {
    }

	/**
	 * the name of the default jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 * 
	 * @return the name of the default jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 */	
	public static synchronized final String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * the name of the default jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 * 
	 * @param dataSourceName name of the default jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 */
	private static synchronized final void setDataSourceName(final String dataSourceName) {
		DBConnectionHelper.dataSourceName = dataSourceName;
	}
	
	/**
	 * the default jdbc driver class name
	 * 
	 * @return the default jdbc driver class name
	 */	
    public static synchronized final String getDriver() {
		return driver;
	}

	/**
	 * the default jdbc driver class name
	 * 
	 * @param driver the default jdbc driver class name
	 */
	private static synchronized final void setDriver(final String driver) {
		DBConnectionHelper.driver = driver;
	}

	/**
	 * a default database url of the form jdbc:subprotocol:subname
	 * 
	 * @return a default database url of the form jdbc:subprotocol:subname
	 */	
	public static synchronized final String getUrl() {
		return url;
	}

	/**
	 * a default database url of the form jdbc:subprotocol:subname
	 * 
	 * @param url a default database url of the form jdbc:subprotocol:subname
	 */
	private static synchronized final void setUrl(final String url) {
		DBConnectionHelper.url = url;
	}

	/**
	 * the default database user on whose behalf connections will be made
	 * 
	 * @return the default database user on whose behalf connections will be made
	 */	
	public static synchronized final String getUser() {
		return user;
	}

	/**
	 * the default database user on whose behalf connections will be made
	 * 
	 * @param user the default database user on whose behalf connections will be made
	 */
	private static synchronized final void setUser(final String user) {
		DBConnectionHelper.user = user;
	}

	/**
	 * the default user's password
	 * 
	 * @return the default user's password
	 */	
	public static synchronized final String getPassword() {
		return password;
	}

	/**
	 * the default user's password
	 * 
	 * @param password the default user's password
	 */
	private static synchronized final void setPassword(final String password) {
		DBConnectionHelper.password = password;
	}

	/**
     * set the default parameters used to retrieve database connections
     * 
     * @param driver   the jdbc driver class name
     * @param url      a database url of the form jdbc:subprotocol:subname
     * @param user     the database user on whose behalf connections will be made
     * @param password the user's password 
     */
	public static synchronized final void setConnectionProperties(final String driver, final String url, 
			final String user, final String password) {
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPassword(password);
        setDataSourceName(null);
	}

	/**
	 * set the default data source to retrieve database connections from
	 * 
	 * @param dataSourceName the name of the jdbc data source to retrieve connections from (e.g. jdbc/B5Wars)
	 */
	public static synchronized final void setConnectionProperties(final String dataSourceName) {
        setDriver(null);
        setUrl(null);
        setUser(null);
        setPassword(null);
        setDataSourceName(dataSourceName);
	}    

    /**
     * retrieve a database connection using the default parameters
     *
     * @return a database connection using the default parameters
     */	
	public static synchronized final Connection getConnection() {
		if (getDataSourceName() != null) { 
			return getConnection(getDataSourceName());
		} else {
			return getConnection(getDriver(), getUrl(), getUser(), getPassword());
		}
	}
	
    /**
     * retrieve a database connection using the specified parameters
     * 
     * @param  driver   the jdbc driver class name
     * @param  url      a database url of the form jdbc:subprotocol:subname
     * @param  user     the database user on whose behalf the connection is being made
     * @param  password the user's password 
     * @return          a database connection using the specified parameters
     */
    public static final Connection getConnection(final String driver, final String url, final String user, 
    		final String password) {
        if ((driver == null) || !(driver.length() > 0)) {
            throw new IllegalArgumentException("driver cannot be null or blank");
        } 	
        if ((url == null) || !(url.length() > 0)) {
            throw new IllegalArgumentException("url cannot be null or blank");
        } 	
        if ((user == null) || !(user.length() > 0)) {
            throw new IllegalArgumentException("user cannot be null or blank");
        } 	
        if (password == null) {
            throw new IllegalArgumentException("password cannot be null");
        } 	
        
        try {
        	Class.forName(driver).getDeclaredConstructor().newInstance();
			return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Failed to retrieve database connection for url " + url, e);
        } catch (Exception e) {
            logger.error("Failed to load driver " + driver, e);
		} 

        return null;
    }
    
    /**
     * retrieve a database connection from the data source with the specified name
     *
     * @param  dataSourceName name of the jdbc data source to retrieve a connection from (e.g. jdbc/B5Wars)
     * @return                a database connection from the data source with the specified name
     */
    public static final Connection getConnection(final String dataSourceName) {
        if ((dataSourceName == null) || !(dataSourceName.length() > 0)) {
            throw new IllegalArgumentException("dataSourceName cannot be null or blank");
        } 	
        
        try {
            Context initialContext = new InitialContext();

            if (initialContext != null) {
                DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/" + dataSourceName);

                if (dataSource != null) {
                    return dataSource.getConnection();
                } 
            } 
        } catch (NamingException e) {
            logger.error("Failed to retrieve data source " + dataSourceName, e);
        } catch (SQLException e) {
            logger.error("Failed to retrieve database connection for data source " + dataSourceName, e);
        } 

        return null;
    } 

    /**
     * close a database connection
     *
     * @param connection the database connection to close
     */
    public static final void closeConnection(final Connection connection) {
        if (connection == null) {
//            throw new IllegalArgumentException("connection cannot be null");
        } else {	
	        try {
	        	connection.close();
	        } catch (SQLException e) { 
	            logger.warn("Failed to close database connection", e);
	        }
        }
    } 

} 
