package net.b5gamer.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * A base implementation for a class that retrieves data from files in a particular directory
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class DirectoryFileAccessor {

    private final File           directory; 
	private final FilenameFilter filenameFilter; 
	private final boolean        includeSubdirectories;
	
	public DirectoryFileAccessor(final String directory, final String fileExtension, final boolean includeSubdirectories) {
		this(new File(directory), 
				((fileExtension != null) && (fileExtension.trim().length() > 0)) ? new FilenameExtensionFilter(fileExtension) : null, 
				includeSubdirectories);
	}
	
	public DirectoryFileAccessor(final File directory, final FilenameFilter filenameFilter, 
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
		
		this.directory             = directory;
		this.filenameFilter        = filenameFilter;
		this.includeSubdirectories = includeSubdirectories;
	}

	public final File getDirectory() {
		return directory;
	}

	public final FilenameFilter getFilenameFilter() {
		return filenameFilter;
	}

	public final boolean getIncludeSubdirectories() {
		return includeSubdirectories;
	}

	public final List<File> listFiles() {
		return FileUtil.listFiles(getDirectory(), getFilenameFilter(), getIncludeSubdirectories());
	}
	
}
