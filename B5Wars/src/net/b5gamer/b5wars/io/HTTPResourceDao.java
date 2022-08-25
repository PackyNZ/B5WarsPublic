package net.b5gamer.b5wars.io;

import java.io.InputStream;

import net.b5gamer.net.URLEncoder;

public class HTTPResourceDao implements ResourceDao {

	private final String baseUrl;
	
	public HTTPResourceDao(final String serverUrl) {
        if ((serverUrl == null) || !(serverUrl.length() > 0)) {
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

	public InputStream load(final String filename) throws Exception {
		if ((filename != null) && (filename.trim().length() > 0)) {
			return URLEncoder.encode(getBaseUrl() + filename).openStream();
		} else {
			return null;
		}
	}
	
}
