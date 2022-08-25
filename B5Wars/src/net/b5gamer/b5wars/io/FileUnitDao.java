package net.b5gamer.b5wars.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.log4j.Logger;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;
import net.b5gamer.io.ExactFilenameFilter;
import net.b5gamer.io.FileUtil;

public class FileUnitDao implements UnitDao {

	private static final Logger logger = Logger.getLogger(FileUnitDao.class.getName());

	private final String filePath;
	
	public FileUnitDao(final String filePath) {
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

	public Unit load(final UnitID id) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("load(id=" + id + ")");
		}

		List<File> files = FileUtil.listFiles(new File(getFilePath()), new ExactFilenameFilter(getFilename(id)), true);

		if ((files != null) && (files.size() > 0)) {
			if (logger.isDebugEnabled()) {
				logger.debug("load() files found = " + files.size());
			}

			return UnitXMLReader.read(new FileInputStream(files.get(0)));
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("load() files found = 0");
			}

			return null;
		}
	}
	
	protected String getFilename(final UnitID id) {
		StringBuilder filename = new StringBuilder();

		filename.append(id.getName());
		if ((id.getModel() != null) && (id.getModel().length() > 0)) {
			filename.append(" (").append(id.getModel()).append(")");
		}
		filename.append(EXTENSION);

		if (logger.isDebugEnabled()) {
			logger.debug("getFilename() filename = " + filename);
		}

		return filename.toString();
	}	
	
}
