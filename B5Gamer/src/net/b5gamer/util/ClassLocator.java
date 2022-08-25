/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.util;

/**
 * This class provides utility methods for finding classes 
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public final class ClassLocator {

	private ClassLocator() {	
	}

    /**
     * retrieve a class by name
     *
     * @param  className full name of the class to find
     * @return           the found class
     * 
	 * @throws ClassNotFoundException   if the class is not found
	 * @throws IllegalArgumentException if className is invalid
     */	
	public static final Class<?> find(final String className) throws ClassNotFoundException, IllegalArgumentException {
		return find(className, null);
	}
	
    /**
     * retrieve a class by name and ensure it is assignable to a given Class
     *
     * @param  className       full name of the class to find
     * @param  assignableClass optional class the found class must be assignable to (e.g. 
     *                         {@link java.lang.Class#isAssignableFrom(Class) assignableClass.isAssignableFrom(foundClass)}) 
     * @return                 the found class
     * 
	 * @throws ClassNotFoundException   if the class is not found
	 * @throws IllegalArgumentException if className is invalid, or the class is found but is not 
	 *                                  assignable to assignableClass
	 * 
	 * @see java.lang.Class#isAssignableFrom(Class)
     */	
	public static final Class<?> find(final String className, final Class<?> assignableClass) 
			throws ClassNotFoundException, IllegalArgumentException {
		Class<?> result = null;
		
        if ((className == null) || !(className.length() > 0)) {
            throw new IllegalArgumentException("className cannot be null or blank");
        } 

        try {
			Class<?> foundClass = Class.forName(className);
			
			if (assignableClass != null) {
				if (assignableClass.isAssignableFrom(foundClass)) {
					result = foundClass;
				} else {
					throw new IllegalArgumentException(className + " is not assignable to " + assignableClass.getName());
				}
			} else {
				result = foundClass;
			}
		} catch (ClassNotFoundException e) {}
				
		if (result == null) {
			throw new ClassNotFoundException("Class " + className + " not found");
		}
		
		return result;
	}
	
    /**
     * retrieve a class by name, ensure it is assignable to a given Class, and if the target
     * class isn't initially found try finding it in the same package as the assignable class
     * and optionally in sub-packages of the assignable class
     *
     * @param  className       name of the class to find, with or without package information
     * @param  assignableClass class the found class must be assignable to (e.g. 
     *                         {@link java.lang.Class#isAssignableFrom(Class) assignableClass.isAssignableFrom(foundClass)}) 
     * @param  subPackageNames names of sub-packages of the assignable class to check, may be null
     * @return                 the found class
     * 
	 * @throws ClassNotFoundException   if the class is not found
	 * @throws IllegalArgumentException if assignableClass is null, or the class is found but is not 
	 *                                  assignable to assignableClass
	 * 
	 * @see java.lang.Class#isAssignableFrom(Class)
     */	
	public static final Class<?> find(final String className, final Class<?> assignableClass,
			final String[] subPackageNames)	throws ClassNotFoundException, IllegalArgumentException {
		Class<?> result = null;

        if (assignableClass == null) {
            throw new IllegalArgumentException("assignableClass cannot be null");
        } 
		
		// attempt to find class as-is
		try {
			result = find(className, assignableClass);
		} catch (ClassNotFoundException e) {}
		
		// attempt to find class using assignable class package
		if (result == null) {
			String packageName = assignableClass.getPackage().getName();
				
			try {
				result = find(packageName + "." + className, assignableClass);
			} catch (ClassNotFoundException e) {}
		}
		
		// attempt to find class in sub-packages of assignable class package
		if ((result == null) && (subPackageNames != null)) {
			String packageName = assignableClass.getPackage().getName();
				
			for (int index = 0; index < subPackageNames.length; index++) {
				try {
					result = find(packageName + "." + subPackageNames[index] + "." + className, assignableClass);
				} catch (ClassNotFoundException e) {}
				
				if (result != null) {
					break;
				}
			}
		}

		if (result == null) {
			throw new ClassNotFoundException("Class " + className + " not found");
		}
		
		return result;
	}
	
}
