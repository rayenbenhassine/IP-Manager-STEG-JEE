package com.java.servlets;


import com.java.classes.AdresseIP;
import com.java.mysql.ConnexionBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifierAdresseIP extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ModifierAdresseIP()
	{
		super();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		String prenom = request.getParameter("prenom");
		String adresse_ip = request.getParameter("adresse_ip");
		HttpSession session = request.getSession(true);
		long matricule;
		try
		{
			matricule = Long.parseLong(request.getParameter("matricule"));
		}
		catch (NumberFormatException nfe)
		{
			matricule = 0;
			session.setAttribute("infoModifierIP", "Matricule invalide");
			request.getRequestDispatcher("/reseau.jsp").forward(request, response);
		}
		if(matricule < 10000 || matricule > 99999)
		{
			session.setAttribute("infoModifierIP", "Matricule invalide");
		}
		else if (prenom.length() > 50)
		{
			session.setAttribute("infoModifierIP", "Prénom invalide");
		}
		else if (!request.getParameter("matricule").equals(request.getParameter("hiddeninput")) && AdresseIP.exist_matricule(matricule))
		{
			session.setAttribute("infoModifierIP", "Matricule existante");
		}
		else if(!(request.getParameter("adresse_ip").equals(request.getParameter("hiddeninput1"))) && AdresseIP.exist_ip(adresse_ip))
		{
			session.setAttribute("infoModifierIP", "Adresse IP non libre");			
		}
		else if(AdresseIP.verifAdresseIP(adresse_ip))
		{
			AdresseIP ip = new AdresseIP(matricule, prenom, adresse_ip, 0);
			if(ip.Verif_appartenance_plage() != -1)
			{
				ConnexionBD.executeQuery("update adresse_ip set prenom = '" + prenom +"' where matricule = " + request.getParameter("hiddeninput"));
				ConnexionBD.executeQuery("update adresse_ip set adresse_ip = '" + adresse_ip +"' where matricule = " + request.getParameter("hiddeninput"));
				ConnexionBD.executeQuery("update adresse_ip set matricule = '" + matricule +"' where matricule = " + request.getParameter("hiddeninput"));
				ConnexionBD.executeQuery("update adresse_ip set id_reseau = " + ip.Verif_appartenance_plage() + " where matricule = " + matricule);
			}
			else
			{
				session.setAttribute("infoModifierIP", "Cette adresse IP n'appartient pas à aucun reseau");			
			}
		}
		

		request.getRequestDispatcher("/reseau.jsp").forward(request, response);
			
	}


}
