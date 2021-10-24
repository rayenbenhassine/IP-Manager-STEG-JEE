/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.servlets;

import com.java.classes.AdresseIP;
import com.java.classes.Reseau;
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
public class AjouterSousReseau extends HttpServlet {

	public AjouterSousReseau() 
	{
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		String nomReseau = request.getParameter("nomReseau");
		String ip_reseau = request.getParameter("ip_reseau");
		String masque_sous_reseau = request.getParameter("masque_sous_reseau");
		String passerelle = request.getParameter("passerelle");

		String info = "";
		try {
			info = Reseau.Verif_ajout_reseau(ip_reseau, masque_sous_reseau, passerelle);
		} catch (SQLException ex) {
			Logger.getLogger(AjouterSousReseau.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if(info.equals("ok"))
		{
			Reseau reseau = new Reseau(0,nomReseau,ip_reseau, masque_sous_reseau, passerelle);
			reseau.ajouter_sous_reseau();
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("infoAjouter", info);
		}
		
		request.getRequestDispatcher("/sous-reseau2.jsp").forward(request, response);
	}

}
