package net.codejava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import ruTherePackage.ruThere;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class validateStudent extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email").trim();
		String sheetName = request.getParameter("sheetName").trim();
		String studentId = request.getParameter("sid").trim();
		String key = request.getParameter("password").trim();
		String answer = request.getParameter("answer").trim();
		double lat = Double.parseDouble(request.getParameter("coordLat"));
		double lng = Double.parseDouble(request.getParameter("coordLng"));
		//Todo: add a parameter here and in JSP
		
		if(ruThere.submitAttendance(email, sheetName, studentId, key, answer, lat, lng)) {
			response.sendRedirect("validationSuccess.jsp");
		}
		else response.sendRedirect("validationFailure.jsp");
		

		
	}
}