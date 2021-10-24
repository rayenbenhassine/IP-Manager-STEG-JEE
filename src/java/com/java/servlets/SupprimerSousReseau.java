package com.java.servlets;

import com.java.classes.AdresseIP;
import com.java.classes.Reseau;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SupprimerSousReseau extends HttpServlet 
{

	public SupprimerSousReseau()
	{
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Reseau.supprimerSousReseau(request.getParameter("id_reseau"));
		request.getRequestDispatcher("/sous-reseau2.jsp").forward(request, response);
	}
}
