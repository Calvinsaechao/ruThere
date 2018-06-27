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



public class generateCode extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		//Todo: add a parameter here and in JSP
		HttpSession session = request.getSession();
		if(ruThere.generateCode((String)session.getAttribute("email"), (String)request.getParameter("sheetName"))) {
			response.sendRedirect("generateSuccess.jsp");
		}
		else response.sendRedirect("generateFailure.jsp");
		

		
	}
}