package net.b5gamer.b5wars.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.b5gamer.b5wars.io.FactionDao;
import net.b5gamer.b5wars.io.DBFactionDao;

public class DaoTestServlet extends HttpServlet {

	private static final long serialVersionUID = -5425716202882186142L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();

		// Factions
		FactionDao dao = new DBFactionDao("jdbc/B5Wars");
		List<String> factions = dao.findAll();
		
		writer.println("Factions:");
		for (Iterator<String> iterator = factions.iterator(); iterator.hasNext();) {
			writer.println("    " + iterator.next());
		}
		
		writer.close();
	}
	
}
