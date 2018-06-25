package net.codejava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import ruTherePackage.ruThere;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class generateCode extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		
		//String email = request.getParameter("email");
		
		ruThere.updateSheets();
		response.sendRedirect("generateSuccess.jsp");
	}
}