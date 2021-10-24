package com.java.servlets;
import com.java.classes.User;
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

public class RechercherSousReseau extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public RechercherSousReseau()
	{
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		ResultSet result = ConnexionBD.executeSelectQuery("select adresse_ip.matricule,adresse_ip.prenom,adresse_ip.adresse_ip,reseau.nomReseau from adresse_ip, reseau where adresse_ip.id_reseau = reseau.id_reseau and adresse_ip.id_reseau = " + request.getParameter("select"));
		ResultSet inforeseau = ConnexionBD.executeSelectQuery("select * from reseau where id_reseau = " + request.getParameter("select"));
		HttpSession session = request.getSession();
		session.setAttribute("result", result);
		session.setAttribute("inforeseau",inforeseau);
		User user = (User)session.getAttribute("user");
		if(user.getAdmin() == 1)		
			request.getRequestDispatcher("/reseau.jsp").forward(request, response);
		else
			request.getRequestDispatcher("/reseauUser.jsp").forward(request, response);

	}


}
