package net.b5gamer.b5wars.io;

import java.io.InputStream;

import net.b5gamer.net.URLEncoder;

public class HTTPWeaponDataDao implements WeaponDataDao {

	private final String baseUrl;
	
	public HTTPWeaponDataDao(final String serverUrl) {
        if ((serverUrl == null) || !(serverUrl.trim().length() > 0)) {
            throw new IllegalArgumentException("serverUrl cannot be null or blank");
        } 

        StringBuilder url = new StringBuilder(serverUrl);
        if (!serverUrl.endsWith("/")) { url.append("/"); }
		url.append(LOCATION).append("/");
		this.baseUrl = url.toString();
	}
	
	protected String getBaseUrl() {
		return baseUrl;
	}
	
	public InputStream load(final String weaponType) throws Exception {
		if ((weaponType != null) && (weaponType.trim().length() > 0)) {
			return URLEncoder.encode(getBaseUrl() + weaponType + EXTENSION).openStream();
		} else {
			return null;
		}
	}

}
