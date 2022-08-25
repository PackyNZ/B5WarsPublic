package net.b5gamer.util;

/**
 * This class provides utility methods for retrieving and validating properties of various data 
 * types
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public final class PropertyReader {

	private PropertyReader() {	
	} 

    /**
     * retrieve and validate an int property
     *
     * @param  properties   set of properties to retrieve the property from
     * @param  propertyName name of the property to retrieve
     * @return              int value of the property
     * 
	 * @throws IllegalArgumentException if the property is not a valid integer
     */
	public static final int getInteger(final Properties properties, final String propertyName) 
			throws IllegalArgumentException {
        int result = 0;

        try {
            result = Integer.parseInt(properties.getProperty(propertyName));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(propertyName + " must be an integer");
        } 

        return result;
    } 
	
    /**
     * retrieve and validate a double property
     *
     * @param  properties   set of properties to retrieve the property from
     * @param  propertyName name of the property to retrieve
     * @return              double value of the property
     * 
	 * @throws IllegalArgumentException if the property is not a valid double
     */
	public static final double getDouble(final Properties properties, final String propertyName) 
			throws IllegalArgumentException {
        double result = 0.0;

        try {
            result = Double.parseDouble(properties.getProperty(propertyName));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(propertyName + " must be a double");
        }

        return result;
    }
	
    /**
     * retrieve and validate a boolean property
     *
     * @param  properties   set of properties to retrieve the property from
     * @param  propertyName name of the property to retrieve
     * @return              boolean value of the property
     */
	public static final boolean getBoolean(final Properties properties, final String propertyName) {
        return Boolean.valueOf(properties.getProperty(propertyName));
    } 

}
