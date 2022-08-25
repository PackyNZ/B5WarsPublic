package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.AbstractAction;
import javax.swing.JApplet;

import net.b5gamer.b5wars.io.ExportServlet;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.net.URLEncoder;

@SuppressWarnings("removal")
public class RemoteExportAction extends AbstractAction {

	private static final long serialVersionUID = 6889842604469759362L;

	private final String  format;	
	private final JApplet parent;
	private final String  servletUrl;
	private final Unit    unit;
	
    public RemoteExportAction(final String format, final JApplet parent, final String serverUrl, final Unit unit) {
        super(format);
        
        if ((format == null) || !(format.trim().length() > 0)) {
            throw new IllegalArgumentException("format cannot be null or blank");
        } 
        if (parent == null) {
            throw new IllegalArgumentException("parent cannot be null");
        }
        if ((serverUrl == null) || !(serverUrl.trim().length() > 0)) {
            throw new IllegalArgumentException("serverUrl cannot be null or blank");
        } 
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }
        
        StringBuilder url = new StringBuilder(serverUrl);
        if (!serverUrl.endsWith("/")) { url.append("/"); }
		url.append("Export");
		
        this.format     = format;
        this.parent     = parent;
		this.servletUrl = url.toString();        
		this.unit       = unit;
    }
    
    public String getFormat() {
		return format;
	}
    
    protected JApplet getParent() {
		return parent;
	}

	protected String getServletUrl() {
		return servletUrl;
	}
	
	public Unit getUnit() {
		return unit;
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		OutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		
		try {
			StringBuilder urlString = new StringBuilder(getServletUrl());
			urlString.append("?").append(ExportServlet.PARAM_ACTION).append("=").append(ExportServlet.ACTION_STORE);
			
			URL url = URLEncoder.encode(urlString.toString());
			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
			
			outputStream = connection.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(getUnit());
			
			System.out.println(connection.getContent());
		} catch (Exception er) {
			er.printStackTrace();
		} finally {
			if (objectOutputStream != null) {
				try { objectOutputStream.close(); } catch (Exception err) {}
			}
			if (outputStream != null) {
				try { outputStream.close(); } catch (Exception err) {}		
			}
		}
		
		try {
			StringBuilder urlString = new StringBuilder(getServletUrl());
			urlString.append("/").append(getUnit().getFullName());
			urlString.append("?").append(ExportServlet.PARAM_FORMAT).append("=").append(getFormat());
			
			getParent().getAppletContext().showDocument(new URL(urlString.toString()), "_blank");
		} catch (Exception er) {
			er.printStackTrace();
		}
	}

}
