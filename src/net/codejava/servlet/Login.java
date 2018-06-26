package net.codejava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import ruTherePackage.ruThere;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;


public class Login extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		JSONObject professorInfo = ruThere.validateEmail(email);
		if(professorInfo == null) response.sendRedirect("instructors.jsp");
		if (professorInfo != null) { //if professor exists check password
			if(ruThere.validatePassword(professorInfo, password)) { //if password matches email
				HttpSession session = request.getSession(); //login
				session.setAttribute("email", email);
				response.sendRedirect("generateCode.jsp");
			}
			else response.sendRedirect("instructors.jsp");
		}
	}
}