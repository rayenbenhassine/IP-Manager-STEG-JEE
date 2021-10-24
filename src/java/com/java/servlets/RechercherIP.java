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


public class RechercherIP extends HttpServlet {
private static final long serialVersionUID = 1L;

	public RechercherIP()
	{
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		HttpSession session = request.getSession(true);
		ResultSet result = ConnexionBD.executeSelectQuery("select adresse_ip.matricule,adresse_ip.prenom,adresse_ip.adresse_ip,reseau.nomReseau from adresse_ip, reseau where adresse_ip.id_reseau = reseau.id_reseau and adresse_ip.adresse_ip = '" + request.getParameter("adresse_ip")+"'");
		try {
			if(result.first())
			{
				session.setAttribute("resultIP", result);
			}
			else
			{
				session.setAttribute("infosearch", "Adresse IP inexistante");			
			}
		} catch (SQLException ex) {
			Logger.getLogger(RechercherIP.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		User user = (User)session.getAttribute("user");
		if(user.getAdmin() == 1)		
			request.getRequestDispatcher("/reseau.jsp").forward(request, response);
		else
			request.getRequestDispatcher("/reseauUser.jsp").forward(request, response);
	}

}
