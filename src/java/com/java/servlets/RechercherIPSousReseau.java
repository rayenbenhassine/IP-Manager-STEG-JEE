package com.java.servlets;

import com.java.classes.AdresseIP;
import com.java.mysql.ConnexionBD;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RechercherIPSousReseau extends HttpServlet 
{

	public RechercherIPSousReseau() 
	{
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		String adresse_ip = request.getParameter("adresse_ip");
		HttpSession session = request.getSession();
		if(AdresseIP.verifAdresseIP(adresse_ip))
		{
			ResultSet result = ConnexionBD.executeSelectQuery("select * from reseau where ip_reseau = '" + adresse_ip +"'");
			try {
				if(result.first())
				{
					session.setAttribute("result", result);
				}
				else
				{
					session.setAttribute("infosearch", "Adresse IP inexistante");
				}
			} catch (SQLException ex) {
				Logger.getLogger(RechercherIPSousReseau.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{
			session.setAttribute("infosearch", "Adresse IP invalide");
		}
		request.getRequestDispatcher("/sous-reseau2.jsp").forward(request, response);

	}
	
	

}
