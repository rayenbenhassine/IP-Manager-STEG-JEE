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
public class RechercherMatricule extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RechercherMatricule()
	{
		super();
	}

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
			ResultSet result = ConnexionBD.executeSelectQuery("select adresse_ip.matricule,adresse_ip.prenom,adresse_ip.adresse_ip,reseau.nomReseau from adresse_ip, reseau where adresse_ip.id_reseau = reseau.id_reseau and adresse_ip.matricule = " + request.getParameter("matricule"));
			try {
				if(result.first())
				{
					session.setAttribute("resultmatricule", result);
					
				}
				else
				{
					session.setAttribute("infosearch", "aucune resultat trouvée");
				}
			} catch (SQLException ex) {
				Logger.getLogger(RechercherUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
			}

		}		
		User user = (User)session.getAttribute("user");
		if(user.getAdmin() == 1)		
			request.getRequestDispatcher("/reseau.jsp").forward(request, response);
		else
			request.getRequestDispatcher("/reseauUser.jsp").forward(request, response);
	}


}
