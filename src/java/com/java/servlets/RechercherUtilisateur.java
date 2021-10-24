/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.servlets;

import com.java.classes.User;
import com.java.mysql.ConnexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rayen
 */
public class RechercherUtilisateur extends HttpServlet
{

	public RechercherUtilisateur()
	{
		super();
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		long matricule;
		try
		{
			matricule = Long.parseLong(request.getParameter("matricule"));
		}
		catch (NumberFormatException nfe)
		{
			matricule = 0;
			session.setAttribute("infosearch", "Matricule invalide");
			request.getRequestDispatcher("/listeUtilisateurs.jsp").forward(request, response);
		}
		if(matricule < 10000 || matricule > 99999)
		{
			session.setAttribute("infosearch", "Matricule invalide");

		}
		else
		{
			ResultSet result = ConnexionBD.executeSelectQuery("select * from user where matricule = " + request.getParameter("matricule") + " and admin = 0");
			try {
				if(result.first())
				{
					User user = new User(result.getLong("matricule"),result.getString("mdp"),result.getString("prenom"),result.getString("nom"),result.getInt("admin"));
					session.setAttribute("result", user);
					
				}
				else
				{
					session.setAttribute("infosearch", "aucune resultat trouvée");
				}
			} catch (SQLException ex) {
				Logger.getLogger(RechercherUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
		this.getServletContext().getRequestDispatcher("/listeUtilisateurs.jsp").forward(request, response);
	}

	
}
