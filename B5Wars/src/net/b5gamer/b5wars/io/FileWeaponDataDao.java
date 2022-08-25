package net.b5gamer.b5wars.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileWeaponDataDao implements WeaponDataDao {

	private final String filePath;
	
	public FileWeaponDataDao(final String filePath) {
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

	public InputStream load(final String weaponType) throws IOException {
		if ((weaponType != null) && (weaponType.trim().length() > 0)) {
			return new BufferedInputStream(new FileInputStream(new File(getFilePath() + weaponType + EXTENSION)));
		} else {
			return null;
		}
	}

}
