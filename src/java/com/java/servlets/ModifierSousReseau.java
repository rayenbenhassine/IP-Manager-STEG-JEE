package com.java.servlets;

import com.java.classes.AdresseIP;
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


public class ModifierSousReseau extends HttpServlet 
{
	public ModifierSousReseau()
	{
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		
		String id_reseau = request.getParameter("id_reseau");
		String nomReseau = request.getParameter("nomReseau");
		String ip_reseau = request.getParameter("ip_reseau");
		String masque_sous_reseau = request.getParameter("masque_sous_reseau");
		String passerelle = request.getParameter("passerelle");
		String old_ip_reseau = request.getParameter("old_ip_reseau");
		HttpSession session = request.getSession(true);

		if(!AdresseIP.verifAdresseIP(ip_reseau))
		{
			session.setAttribute("modifinfo", "Adresse IP invalide");			
		}
		else if(!AdresseIP.verifAdresseIP(masque_sous_reseau))
		{
			session.setAttribute("modifinfo", "masque invalide");			
		}
		else if(!AdresseIP.verifAdresseIP(passerelle))
		{
			session.setAttribute("modifinfo", "passerelle invalide");			
		}
		else 
		{
			ResultSet result = ConnexionBD.executeSelectQuery("select * from reseau where ip_reseau <> '" + old_ip_reseau + "'");
			boolean ok = false;
			try {
				while(result.next() && ok == false)
				{
					if(result.getString("ip_reseau").equals(ip_reseau))
					{			
						ok = true;
						session.setAttribute("modifinfo", "Adresse IP existante");
					}
				}
			} catch (SQLException ex) {
				Logger.getLogger(ModifierSousReseau.class.getName()).log(Level.SEVERE, null, ex);
			}
			if(ok == false)
			{
				ConnexionBD.executeQuery("update reseau set nomReseau = '" + nomReseau + "' where id_reseau = " + id_reseau);
				ConnexionBD.executeQuery("update reseau set ip_reseau = '" + ip_reseau + "' where id_reseau = " + id_reseau);
				ConnexionBD.executeQuery("update reseau set masque_sous_reseau = '" + masque_sous_reseau + "' where id_reseau = " + id_reseau);
				ConnexionBD.executeQuery("update reseau set passerelle = '" + passerelle + "' where id_reseau = " + id_reseau);
			}
		}
		request.getRequestDispatcher("/sous-reseau2.jsp").forward(request, response);
			
	}
	

}
