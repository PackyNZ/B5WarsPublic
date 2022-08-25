package net.b5gamer.b5wars.io;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.b5gamer.net.URLEncoder;

public class HTTPIconDao implements IconDao {

	private final String servletUrl;
	private final String baseUrl;
	
	public HTTPIconDao(final String serverUrl) {
        if ((serverUrl == null) || !(serverUrl.length() > 0)) {
            throw new IllegalArgumentException("serverUrl cannot be null or blank");
        } 

        StringBuilder url = new StringBuilder(serverUrl);
        if (!serverUrl.endsWith("/")) { url.append("/"); }
		url.append("Data");
		url.append("?").append(DataServlet.PARAM_DATATYPE).append("=").append(DataServlet.DATATYPE_ICON);
		url.append("&").append(DataServlet.PARAM_ACTION).append("=findAll");
		this.servletUrl = url.toString();
		
        url = new StringBuilder(serverUrl);
        if (!serverUrl.endsWith("/")) { url.append("/"); }
		url.append(LOCATION).append("/");
		this.baseUrl = url.toString();
	}

	protected String getServletUrl() {
		return servletUrl;
	}

	protected String getBaseUrl() {
		return baseUrl;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAll(final String filenamePrefix, final int damageBoxes) {
		List<String> filenames = null;
		InputStream inputStream = null;
		ObjectInputStream objectInputStream = null;
		
		try {
			StringBuilder urlString = new StringBuilder(getServletUrl());
			urlString.append("&filenamePrefix=").append(filenamePrefix);
			urlString.append("&damageBoxes=").append(damageBoxes);
			
			URL url = URLEncoder.encode(urlString.toString());
			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
	
			inputStream = connection.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			filenames = (List<String>) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (objectInputStream != null) {
				try { objectInputStream.close(); } catch (Exception e) {}
			}
			if (inputStream != null) {
				try { inputStream.close(); } catch (Exception e) {}		
			}
		}
		
		return filenames;
	}

	public InputStream load(final String filename) throws Exception {
		if ((filename != null) && (filename.trim().length() > 0)) {
			return URLEncoder.encode(getBaseUrl() + filename).openStream();
		} else {
			return null;
		}
	}
	
}
