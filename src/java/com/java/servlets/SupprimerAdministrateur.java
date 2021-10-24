package com.java.servlets;
import com.java.mysql.ConnexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.java.classes.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SupprimerAdministrateur extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public SupprimerAdministrateur()
	{
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		User.supprimer_utilisateur(request.getParameter("matricule"));
		HttpSession session = request.getSession();
		request.getRequestDispatcher("/listeAdministrateurs.jsp").forward(request, response);
	}


}
