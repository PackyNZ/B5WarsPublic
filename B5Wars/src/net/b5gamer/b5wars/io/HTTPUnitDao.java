package net.b5gamer.b5wars.io;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;
import net.b5gamer.net.URLEncoder;

public class HTTPUnitDao implements UnitDao {

	private final String servletUrl;
	
	public HTTPUnitDao(final String serverUrl) {
        if ((serverUrl == null) || !(serverUrl.trim().length() > 0)) {
            throw new IllegalArgumentException("serverUrl cannot be null or blank");
        } 

        StringBuilder url = new StringBuilder(serverUrl);
        if (!serverUrl.endsWith("/")) { url.append("/"); }
		url.append("Data");
		url.append("?").append(DataServlet.PARAM_DATATYPE).append("=").append(DataServlet.DATATYPE_UNIT);
		url.append("&").append(DataServlet.PARAM_ACTION).append("=load");
		this.servletUrl = url.toString();
	}

	protected String getServletUrl() {
		return servletUrl;
	}
	
	public Unit load(final UnitID id) throws Exception {
		Unit unit = null;
		InputStream inputStream = null;
		ObjectInputStream objectInputStream = null;
		
		try {
			StringBuilder urlString = new StringBuilder(getServletUrl());
			urlString.append("&name=").append(id.getName());
			urlString.append("&model=").append(id.getModel());
			urlString.append("&version=").append(id.getVersion());
			
			URL url = URLEncoder.encode(urlString.toString());
			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
	
			inputStream = connection.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			unit = (Unit) objectInputStream.readObject();
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
		
		return unit;
	}

}
