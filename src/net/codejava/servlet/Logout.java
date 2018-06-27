package net.codejava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import ruTherePackage.ruThere;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Logout extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.invalidate();
		response.sendRedirect("instructors.jsp");
	}
}