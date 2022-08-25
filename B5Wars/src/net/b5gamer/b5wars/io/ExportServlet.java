package net.b5gamer.b5wars.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.b5wars.io.unit.UnitXMLWriter;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;

public class ExportServlet extends HttpServlet {

	private static final long serialVersionUID = 362390983895753548L;

	public static final String PARAM_ACTION  = "action";
	public static final String PARAM_NAME    = "name";
	public static final String PARAM_MODEL   = "model";
	public static final String PARAM_VERSION = "version";
	public static final String PARAM_FORMAT  = "format";
	
	public static final String ACTION_STORE = "store";

	public static final String FORMAT_PDF = "PDF";
	public static final String FORMAT_XML = "XML";

	public static final String SESSION_KEY = "B5W.Export";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session = request.getSession(true);		
		String      action  = request.getParameter(PARAM_ACTION);

		if (ACTION_STORE.equalsIgnoreCase(action)) {
			ObjectInputStream inputStream = null;
			try {
				inputStream = new ObjectInputStream(request.getInputStream());
				Object object = inputStream.readObject();
				session.setAttribute(SESSION_KEY, object);

				response.setContentType("text/plain");
				PrintWriter out = new PrintWriter(response.getOutputStream());
				out.println((object != null) ? object.toString() : "");
				out.close();			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try { inputStream.close(); } catch (Exception e) {}
				}
			}			
		} else {
			String name   = request.getParameter(PARAM_NAME);
			String format = request.getParameter(PARAM_FORMAT);
			Object object = null;
			
	    	if ((name != null) && (name.trim().length() > 0)) {
	    		int version = 0;
	    		try {
	    			version = Integer.parseInt(request.getParameter(PARAM_VERSION));
	    		} catch (Exception e) {}

	    		try {
	    			object = DataManager.getUnitDao().load(new UnitID(name, request.getParameter(PARAM_MODEL), version));
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	} else {
				object = session.getAttribute(SESSION_KEY);
				session.setAttribute(SESSION_KEY, null);
	    	}
			
			if (object instanceof Unit) {
				if (FORMAT_PDF.equals(format)) {
	    			response.setContentType("application/pdf");
		    		try {
		    			UnitPDFWriter.write((Unit) object, response.getOutputStream());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (FORMAT_XML.equals(format)) {
	    			response.setContentType("text/xml");
		    		try {
		    			UnitXMLWriter.write((Unit) object, response.getOutputStream());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (object instanceof Fleet) {
				// TODO
			}
		}
	}

}
