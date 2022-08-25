/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * FilenameFilter to limit files to those whose extension matches that specified
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class FilenameExtensionFilter implements FilenameFilter {

	private final String extension; // file extension of files to accept
	
	/**
	 * @param extension file extension of files to accept
	 */	
	public FilenameExtensionFilter(final String extension) {
        if ((extension == null) || !(extension.length() > 0)) {
            throw new IllegalArgumentException("extension cannot be null or blank");
        } 	
		
		this.extension = (extension.startsWith(".")) ? extension.toLowerCase() : "." + extension.toLowerCase();
	}

	/**
	 * the file extension of files to accept
	 * 
	 * @return the file extension of files to accept
	 */
	public String getExtension() {
		return extension;
	}

	public boolean accept(File dir, String name) {
		return ((name != null) && (name.toLowerCase().endsWith(getExtension())));
	}

}
