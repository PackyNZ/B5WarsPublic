package net.b5gamer.b5wars.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSilhouetteDao implements SilhouetteDao {

	private final String filePath;
		
	public FileSilhouetteDao(final String filePath) {
        if ((filePath == null) || !(filePath.trim().length() > 0)) {
            throw new IllegalArgumentException("filePath cannot be null or blank");
        } 

        StringBuilder path = new StringBuilder(filePath);
        if (!filePath.endsWith(File.separator)) { path.append(File.separator); }
        path.append(LOCATION).append(File.separator);
		this.filePath = path.toString();
	}
	
	protected String getFilePath() {
		return filePath;
	}
	
	public InputStream load(final String hull) throws IOException {
		if ((hull != null) && (hull.trim().length() > 0)) {
			return new BufferedInputStream(new FileInputStream(new File(getFilePath() + hull + EXTENSION)));
		} else {
			return null;
		}
	}

}
