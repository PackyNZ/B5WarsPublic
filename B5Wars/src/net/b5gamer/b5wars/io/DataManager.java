package net.b5gamer.b5wars.io;

import org.apache.log4j.Logger;

public final class DataManager {
	 
	private static final Logger logger = Logger.getLogger(DataManager.class.getName());
	
	private static DataAccessMode dataAccessMode;
	private static String         filePath = ".";
	private static String         serverUrl;
	private static CounterDao     counterDao;
	private static FactionDao     factionDao;
	private static IconDao        iconDao;
	private static ResourceDao    resourceDao;
	private static SilhouetteDao  silhouetteDao;
	private static UnitDao        unitDao;
	private static WeaponDataDao  weaponDataDao;
	
	private DataManager() {
	}

	static {
		setDataAccessMode(DataAccessMode.LOCAL_CLIENT);
	}
	
	public static final DataAccessMode getDataAccessMode() {
		return dataAccessMode;
	}

	public static final synchronized void setDataAccessMode(final DataAccessMode dataAccessMode) {
		if (dataAccessMode == null) {
            throw new IllegalArgumentException("dataAccessMode cannot be null");
        }

		if (logger.isDebugEnabled()) {
			logger.debug("setDataAccessMode(dataAccessMode=" + dataAccessMode + ")");
		}
		
		DataManager.dataAccessMode = dataAccessMode;		

		if (dataAccessMode == DataAccessMode.LOCAL_CLIENT) {
	        if ((getFilePath() == null) || !(getFilePath().trim().length() > 0)) {
	            throw new IllegalArgumentException("filePath cannot be null or blank");
	        } 				

	        setCounterDao(new FileCounterDao(getFilePath()));
			setFactionDao(null);
			setIconDao(new FileIconDao(getFilePath()));
			setResourceDao(new FileResourceDao(getFilePath()));
			setSilhouetteDao(new FileSilhouetteDao(getFilePath()));
			setUnitDao(new FileUnitDao(getFilePath()));
			setWeaponDataDao(new FileWeaponDataDao(getFilePath()));
		} else if (dataAccessMode == DataAccessMode.LOCAL_SERVER) {
	        if ((getFilePath() == null) || !(getFilePath().trim().length() > 0)) {
	            throw new IllegalArgumentException("filePath cannot be null or blank");
	        }

	        setCounterDao(new FileCounterDao(getFilePath()));
			setFactionDao(null);
			setIconDao(new FileIconDao(getFilePath()));
			setResourceDao(new FileResourceDao(getFilePath()));
			setSilhouetteDao(new FileSilhouetteDao(getFilePath()));
			setUnitDao(new FileUnitDao(getFilePath())); // change to db
			setWeaponDataDao(new FileWeaponDataDao(getFilePath()));
		} else if (dataAccessMode == DataAccessMode.REMOTE) {
	        if ((getServerUrl() == null) || !(getServerUrl().trim().length() > 0)) {
	            throw new IllegalArgumentException("serverUrl cannot be null or blank");
	        } 				
			
			setCounterDao(new HTTPCounterDao(getServerUrl()));
			setFactionDao(null);
			setIconDao(new HTTPIconDao(getServerUrl()));
			setResourceDao(new HTTPResourceDao(getServerUrl()));
			setSilhouetteDao(new HTTPSilhouetteDao(getServerUrl()));
			setUnitDao(new HTTPUnitDao(getServerUrl()));
			setWeaponDataDao(new HTTPWeaponDataDao(getServerUrl()));
		}
	}

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(final String filePath) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFilePath(filePath=" + filePath + ")");
		}

		DataManager.filePath = ((filePath != null) && (filePath.trim().length() > 0)) ? filePath : ".";
	}

	public static final String getServerUrl() {
		return serverUrl;
	}

	public static final void setServerUrl(final String serverUrl) {
		if (logger.isDebugEnabled()) {
			logger.debug("setServerUrl(serverUrl=" + serverUrl + ")");
		}

		DataManager.serverUrl = ((serverUrl == null) || (serverUrl.endsWith("/"))) ? serverUrl : serverUrl + "/";
	}
	
	public static final CounterDao getCounterDao() {
		return counterDao;
	}

	protected static final void setCounterDao(final CounterDao counterDao) {
		DataManager.counterDao = counterDao;
	}

	public static final FactionDao getFactionDao() {
		return factionDao;
	}
	
	protected static final void setFactionDao(final FactionDao factionDao) {
		DataManager.factionDao = factionDao;
	}

	public static IconDao getIconDao() {
		return iconDao;
	}

	protected static final void setIconDao(final IconDao iconDao) {
		DataManager.iconDao = iconDao;
	}

	public static ResourceDao getResourceDao() {
		return resourceDao;
	}

	protected static void setResourceDao(final ResourceDao resourceDao) {
		DataManager.resourceDao = resourceDao;
	}

	public static SilhouetteDao getSilhouetteDao() {
		return silhouetteDao;
	}

	protected static void setSilhouetteDao(final SilhouetteDao silhouetteDao) {
		DataManager.silhouetteDao = silhouetteDao;
	}

	public static final UnitDao getUnitDao() {
		return unitDao;
	}

	protected static final void setUnitDao(final UnitDao unitDao) {
		DataManager.unitDao = unitDao;
	}

	public static WeaponDataDao getWeaponDataDao() {
		return weaponDataDao;
	}

	protected static void setWeaponDataDao(final WeaponDataDao weaponDataDao) {
		DataManager.weaponDataDao = weaponDataDao;
	}

}
