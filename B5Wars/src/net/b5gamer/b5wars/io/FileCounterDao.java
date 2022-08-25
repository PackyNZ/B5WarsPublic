package net.b5gamer.b5wars.io;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import net.b5gamer.io.ExactFilenameFilter;
import net.b5gamer.io.FileUtil;

public class FileCounterDao implements CounterDao {

	private final String filePath;
	
	public FileCounterDao(final String filePath) {
        if ((filePath == null) || !(filePath.trim().length() > 0)) {
            throw new IllegalArgumentException("filePath cannot be null or blank");
        } 

        StringBuilder path = new StringBuilder(filePath);
        if (!filePath.endsWith(File.separator)) { path.append(File.separator); }
        path.append(LOCATION);
		this.filePath = path.toString();
	}
	
	protected String getFilePath() {
		return filePath;
	}
	
	public List<String> findAll(final String unitName) {
		List<File> files = FileUtil.listFiles(new File(getFilePath()), new ExactFilenameFilter(unitName + EXTENSION), true);

		if ((files != null) && (files.size() > 0)) {
			List<String> filenames = new ArrayList<String>(0);			

			for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
				File file = iterator.next();
				filenames.add(replaceAllFileSeparators(file.getPath().substring(file.getPath().indexOf(LOCATION) + LOCATION.length() + 1)));
			}
			
			return filenames;
		} else {
			return null;
		}
	}
	
	public Image load(final String unitName) throws IOException {
		List<File> files = FileUtil.listFiles(new File(getFilePath()), new ExactFilenameFilter(unitName + EXTENSION), true);

		if ((files != null) && (files.size() > 0)) {
			return ImageIO.read(files.get(0));
		} else {
			return null;
		}
	}
	
	private String replaceAllFileSeparators(String path) {
		while (path.indexOf(File.separatorChar) > -1) {
			path = path.replace(File.separatorChar, '/');
		}
		
		return path;
	}

}
