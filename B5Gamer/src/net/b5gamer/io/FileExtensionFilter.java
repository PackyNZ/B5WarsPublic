/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * FileFilter to limit files to those whose extension matches that specified
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class FileExtensionFilter extends FileFilter {

	private final String extension;   // file extension of files to accept
	private final String description; // description explaining the file extension
	
	/**
	 * @param extension file extension of files to accept
	 */	
	public FileExtensionFilter(final String extension) {
        this(extension, null);
	}

	/**
	 * @param extension   file extension of files to accept
	 * @param description description explaining the file extension
	 */	
	public FileExtensionFilter(final String extension, final String description) {
        if ((extension == null) || !(extension.length() > 0)) {
            throw new IllegalArgumentException("extension cannot be null or blank");
        } 	
		
		this.extension   = (extension.startsWith(".")) ? extension.toLowerCase() : "." + extension.toLowerCase();
		this.description = description;
	}

	/**
	 * the file extension of files to accept
	 * 
	 * @return the file extension of files to accept
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * the description explaining the file extension
	 * 
	 * @return the description explaining the file extension
	 */	
	@Override
	public String getDescription() {
		return (description == null) ? "*" + getExtension() : description;
	}
	
	@Override
	public boolean accept(File f) {
		return ((f != null) && (f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(extension))));
	}

}
