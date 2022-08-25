package net.b5gamer.b5wars.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.io.FileUtil;
import net.b5gamer.io.FilenameWildcardFilter;

public class FileIconDao implements IconDao {

	private final String filePath;
	
	public FileIconDao(final String filePath) {
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
	
	public List<String> findAll(final String filenamePrefix, final int damageBoxes) {
		List<File> files = FileUtil.listFiles(new File(getFilePath()), new FilenameWildcardFilter(filenamePrefix.replace("&", "And"), EXTENSION), false);

		if ((files != null) && (files.size() > 0)) {
			List<String> filenames = new ArrayList<String>(0);			
			int iconDamageBoxes = 999;

			for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
				String filename = iterator.next().getName();
				
				if (filename.startsWith(filenamePrefix)) {
					int filenameBoxes = Integer.parseInt(filename.substring(filenamePrefix.length(), filename.indexOf(")", filenamePrefix.length())));

					if (filenameBoxes >= damageBoxes) {
						if (filenameBoxes < iconDamageBoxes) {
							iconDamageBoxes = filenameBoxes;
							filenames.clear();
							filenames.add(filename);
						} else if (filenameBoxes == iconDamageBoxes) {									
							filenames.add(filename);
						}
					}
				}				
			}
			
			return filenames;
		} else {
			return null;
		}
	}
	
	public InputStream load(final String filename) throws IOException {
		if ((filename != null) && (filename.trim().length() > 0)) {
			return new BufferedInputStream(new FileInputStream(new File(getFilePath() + filename)));
		} else {
			return null;
		}
	}
	
}
