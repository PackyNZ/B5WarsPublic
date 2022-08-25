/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.io;

import java.io.File;
import java.io.FilenameFilter;

public class ExactFilenameFilter implements FilenameFilter {

	private final String filename; 
	
	public ExactFilenameFilter(final String filename) {
        if ((filename == null) || !(filename.trim().length() > 0)) {
            throw new IllegalArgumentException("filename cannot be null or blank");
        } 	
		
		this.filename = filename.toLowerCase();
	}

	protected String getFilename() {
		return filename;
	}

	public boolean accept(File dir, String name) {
		return ((name != null) && (name.toLowerCase().equals(getFilename())));
	}
	
}
