/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.io;

import java.sql.Connection;

/**
 * A base implementation for a class that retrieves data from a database
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class DBAccessor {

	private boolean useDefaultDB   = false; // whether to use the default database
    private String  dataSourceName = null;  // name of the jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
    private String  dbDriver       = null;  // the jdbc driver class name
    private String  dbUrl          = null;  // a database url of the form jdbc:subprotocol:subname
    private String  dbUser         = null;  // the database user on whose behalf connections will be made
    private String  dbPassword     = null;  // the user's password 
	
    /**
     * create a new instance configured to connect to the default database
     */
	public DBAccessor() {
        this.useDefaultDB = true;
	}
	
	/**
	 * create a new instance configured to connect to the data source with the specified name
	 * 
	 * @param dataSourceName the name of the jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 */
	public DBAccessor(final String dataSourceName) {
        this.dataSourceName = dataSourceName;
	}

	/**
     * create a new instance configured to connect to the database using the specified parameters
     * 
     * @param dbDriver   the jdbc driver class name
     * @param dbUrl      a database url of the form jdbc:subprotocol:subname
     * @param dbUser     the database user on whose behalf connections will be made
     * @param dbPassword the user's password 
     */
	public DBAccessor(final String dbDriver, final String dbUrl, final String dbUser, final String dbPassword) {
        this.dbDriver   = dbDriver;
        this.dbUrl      = dbUrl;
        this.dbUser     = dbUser;
        this.dbPassword = dbPassword;
	}
		
	/**
	 * whether to use the default database
	 * 
	 * @return whether to use the default database
	 */	
	protected final boolean getUseDefaultDB() {
		return useDefaultDB;
	}

	/**
	 * the name of the jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 * 
	 * @return the name of the jdbc data source to obtain connections from (e.g. jdbc/B5Wars)
	 */
	public final String getDataSourceName() {
		return dataSourceName;
	}
	
	/**
	 * the jdbc driver class name
	 * 
	 * @return the jdbc driver class name
	 */	
	public final String getDbDriver() {
		return dbDriver;
	}

	/**
	 * a database url of the form jdbc:subprotocol:subname
	 * 
	 * @return a database url of the form jdbc:subprotocol:subname
	 */	
	public final String getDbUrl() {
		return dbUrl;
	}

	/**
	 * the database user on whose behalf connections will be made
	 * 
	 * @return the database user on whose behalf connections will be made
	 */	
	public final String getDbUser() {
		return dbUser;
	}

	/**
	 * the user's password 
	 * 
	 * @return the user's password 
	 */	
	public final String getDbPassword() {
		return dbPassword;
	}
	
    /**
     * retrieve a database connection using the parameters specified in the constructor
     *
     * @return a database connection using the parameters specified in the constructor
     */	
	protected final Connection getConnection() {
		if (getUseDefaultDB()) { 
			return DBConnectionHelper.getConnection();
		} else if (getDataSourceName() != null) {
			return DBConnectionHelper.getConnection(getDataSourceName());
		} else {
			return DBConnectionHelper.getConnection(getDbDriver(), getDbUrl(), getDbUser(), getDbPassword());
		}
	}
	
    /**
     * close a database connection
     *
     * @param connection the database connection to close
     */	
	protected final void closeConnection(final Connection connection) {
		DBConnectionHelper.closeConnection(connection);
	}
	
}
