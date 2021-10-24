package com.java.servlets;
import com.java.classes.AdresseIP;
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
import com.java.mysql.ExcelImport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;



public class Authentification extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public Authentification()
	{
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession(true);
		if(session.getAttribute("user") != null)
		{
			session.removeAttribute("user");
			session.invalidate();
		}
		
		String matricule = request.getParameter("matricule");
		if (matricule == null)
			matricule = "";

		
		request.setAttribute("matricule", matricule);

		request.getRequestDispatcher("/authentification.jsp").forward(request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String mdp = request.getParameter("mdp");
		HttpSession session = request.getSession();
		long matricule;
		try
		{
			matricule = Long.parseLong(request.getParameter("matricule"));
		}
		catch (NumberFormatException nfe)
		{
			matricule = 0;
			session.setAttribute("info", "Matricule invalide");
			doGet(request,response);
		}
		if(matricule <= 10000 || matricule > 99999)
		{
			session.setAttribute("info", "Matricule invalide");
			doGet(request,response);

		}
		else if (mdp.length() > 50)
		{
			session.setAttribute("info", "Mot de passe invalide");
			doGet(request,response);
		}
		else if(User.verif_login(matricule,mdp))
		{
			ResultSet result = ConnexionBD.executeSelectQuery("select * from user where matricule = "+ matricule);
			try 
			{
				result.first();
				long matricule1 = result.getLong("matricule");
				String mdp1 = result.getString("mdp");
				String prenom = result.getString("prenom");
				String nom = result.getString("nom");
				int admin = result.getInt("admin");
				User user = new User(matricule1, mdp1, prenom, nom, admin);
				session.setAttribute("user", user);
				if (admin == 1)
				{
					request.getRequestDispatcher("/TableauDeBord.jsp").forward(request, response);
				}
				else
				{
					request.getRequestDispatcher("/sous-reseauxUser.jsp").forward(request, response);
				}
			} 
			catch (SQLException ex)
			{
				Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		else
		{
			session.setAttribute("info", "login ou mot de passe incorrect");
			doGet(request,response);
			
		}

	}


}
