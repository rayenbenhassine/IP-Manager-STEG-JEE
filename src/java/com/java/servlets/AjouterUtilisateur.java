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

public class AjouterUtilisateur extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public AjouterUtilisateur()
	{
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		String matricule = request.getParameter("matricule");
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String mdp = request.getParameter("mdp");
		String confirmermdp = request.getParameter("confirmermdp");

		String info = "";
		try 
		{
			info = User.Verif_ajout_user(matricule, prenom, nom, mdp, confirmermdp);
		} 
		catch (SQLException ex) 
		{
			Logger.getLogger(AjouterUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
		}
		if(info.equals("ok"))
		{
			User user = new User(Long.parseLong(matricule), prenom, nom, mdp, 0);
			user.ajouter_utilisateur();
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("infoAjouter", info);
		}
		
		request.getRequestDispatcher("/listeUtilisateurs.jsp").forward(request, response);
	
	}


}
