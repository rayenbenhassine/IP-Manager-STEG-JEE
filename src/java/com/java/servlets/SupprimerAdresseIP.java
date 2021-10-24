package com.java.servlets;

import com.java.classes.AdresseIP;
import com.java.classes.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SupprimerAdresseIP extends HttpServlet
{
	public SupprimerAdresseIP()
	{
		super();
	}
		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		AdresseIP.supprimerAdresseIP(request.getParameter("matricule"));
		request.getRequestDispatcher("/reseau.jsp").forward(request, response);
	}
	

}
