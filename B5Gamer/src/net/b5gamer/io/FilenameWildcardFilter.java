/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.io;

import java.io.File;

public class FilenameWildcardFilter extends FilenameExtensionFilter {

	private final String partialFilename; 
	
	public FilenameWildcardFilter(final String partialFilename, final String extension) {
		super(extension);

        if ((partialFilename == null) || !(partialFilename.length() > 0)) {
            throw new IllegalArgumentException("partialFilename cannot be null or blank");
        } 	
		
		this.partialFilename = partialFilename.toLowerCase();
	}

	protected String getPartialFilename() {
		return partialFilename;
	}

	public boolean accept(File dir, String name) {
		return ((super.accept(dir, name)) && (name.toLowerCase().contains(getPartialFilename())));
	}
	
}
