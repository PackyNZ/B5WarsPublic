package net.b5gamer.net;

import java.net.MalformedURLException;
import java.net.URL;

public final class URLEncoder {

	private URLEncoder() {
	}
	
	public static URL encode(final String url) throws MalformedURLException {
		if ((url != null) && (url.trim().length() > 0)) {
			String encodedUrl = url.replaceAll(" ", "%20");
			
			return new URL(encodedUrl);
		} else {
			return null;
		}		
	}
	
}
