/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {

	private FileUtil() {	
	}
	
	public final static List<File> listFiles(final File directory, final boolean includeSubdirectories) {
		return listFiles(directory, includeSubdirectories);	
	}

	public final static List<File> listFiles(final File directory, final String fileExtension, 
			final boolean includeSubdirectories) {
		return listFiles(directory, 
				((fileExtension != null) && (fileExtension.trim().length() > 0)) ? new FilenameExtensionFilter(fileExtension) : null, 
				includeSubdirectories);
	}
	
	public final static List<File> listFiles(final File directory, final FilenameFilter filenameFilter, 
			final boolean includeSubdirectories) {
        if (directory == null) {
            throw new IllegalArgumentException("directory cannot be null");
        }	
        if (!directory.exists()) {
            throw new IllegalArgumentException("directory " + directory.getAbsolutePath() + " does not exist");
        }	
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("directory " + directory.getAbsolutePath() + " must be a directory");
        }	

        List<File> files = new ArrayList<File>();

	    File[] allFiles = directory.listFiles();		    
	    for (int i = 0; i < allFiles.length; i++) {
	    	File file = allFiles[i];
	    	
	    	if (file.isFile()) {
	    		if ((filenameFilter == null) || (filenameFilter.accept(directory, file.getName()))) {
	    			files.add(file);	    			
	    		}
	    	} else if (file.isDirectory()) {
	    		if (includeSubdirectories) {
	    			files.addAll(listFiles(file, filenameFilter, includeSubdirectories));
	    		}
	    	}
	    }
		
	    return files;
	}
	
}
