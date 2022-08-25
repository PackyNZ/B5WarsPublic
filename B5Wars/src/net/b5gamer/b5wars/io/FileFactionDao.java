package net.b5gamer.b5wars.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.io.FileUtil;
import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.unit.PeriodOfService;
import net.b5gamer.b5wars.unit.Unit;

/**
 * An implementation for retrieving Faction data from files  
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class FileFactionDao implements FactionDao {
	
    private final File directory; 
	
	public FileFactionDao() {
		this(UnitXMLReader.DEFAULT_DIRECTORY);
	}

	public FileFactionDao(final String directory) {
		this(new File(directory));
	}

	public FileFactionDao(final File directory) {
		if (directory == null) {
			throw new IllegalArgumentException("directory cannot be null");
		}	
		if (!directory.exists()) {
			throw new IllegalArgumentException("directory " + directory.getAbsolutePath() + " does not exist");
		}	
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("directory " + directory.getAbsolutePath() + " must be a directory");
		}	

  		this.directory = directory;
	}

	public final File getDirectory() {
		return directory;
	}
	
	public List<String> findAll() {
		List<String> factions = new ArrayList<String>();

		List<File> files = FileUtil.listFiles(getDirectory(), "xml", true);
				
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			
			try {
				// TODO limit to reading POS only
				Unit unit = UnitXMLReader.read(new BufferedInputStream(new FileInputStream(file)));
				
				if ((unit != null) && (unit.getServiceHistory() != null)) {
					for (Iterator<PeriodOfService> iter = unit.getServiceHistory().getPeriodOfServiceIterator(); iter.hasNext();) {
						// TODO limit to distinct list
						factions.add(iter.next().getFaction());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return factions;
	}

}
