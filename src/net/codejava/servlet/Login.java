package net.codejava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import ruTherePackage.ruThere;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Login extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if(email.equals("username@gmail.com")&&
			password.equals("password")) {
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
		}
		response.sendRedirect("generateCode.jsp");
	}
}