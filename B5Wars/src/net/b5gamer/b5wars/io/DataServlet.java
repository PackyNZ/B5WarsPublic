package net.b5gamer.b5wars.io;

import java.io.IOException;
import java.io.ObjectOutputStream;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.b5gamer.b5wars.unit.UnitID;

public class DataServlet extends HttpServlet {

	private static final long serialVersionUID = 2379287663666432027L;

	public static final String PARAM_DATATYPE = "type";
	public static final String PARAM_ACTION   = "action";
	
	public static final String DATATYPE_COUNTER = "counter";
	public static final String DATATYPE_ICON    = "icon";
	public static final String DATATYPE_UNIT    = "unit";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String path = config.getServletContext().getRealPath(".");
    	DataManager.setFilePath(path.substring(0, path.length() - 2));
    	DataManager.setDataAccessMode(DataAccessMode.LOCAL_SERVER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		response.setContentType("application/x-java-serialized-object");
		ObjectOutputStream outputStream = new ObjectOutputStream(response.getOutputStream());

		String datatype = request.getParameter(PARAM_DATATYPE);
		String action   = request.getParameter(PARAM_ACTION);

		if (DATATYPE_COUNTER.equalsIgnoreCase(datatype)) {
			if ("findAll".equalsIgnoreCase(action)) {
				outputStream.writeObject(DataManager.getCounterDao().findAll(request.getParameter("unitName")));
			}			
		} else if (DATATYPE_ICON.equalsIgnoreCase(datatype)) {
			if ("findAll".equalsIgnoreCase(action)) {
				outputStream.writeObject(DataManager.getIconDao().findAll(request.getParameter("filenamePrefix"), 
						Integer.parseInt(request.getParameter("damageBoxes"))));
			}			
		} else if (DATATYPE_UNIT.equalsIgnoreCase(datatype)) {
			if ("load".equalsIgnoreCase(action)) {
				try {
					outputStream.writeObject(DataManager.getUnitDao().load(
							new UnitID(request.getParameter("name"), request.getParameter("model"), Integer.parseInt(request.getParameter("version")))));
				} catch (Exception e) {
					outputStream.writeObject(null);
					e.printStackTrace();
				}
			}			
		}
		
		outputStream.flush();
	}

}
