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

public class ModifierUtilisateur extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ModifierUtilisateur()
	{
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("/listeUtilisateurs.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		String oldmatricule = request.getParameter("hiddeninput");
		ResultSet result = ConnexionBD.executeSelectQuery("select * from user where matricule = " + oldmatricule);
		User old_user = null;
		try {
			while(result.next())
			{
				old_user = new User(result.getLong("matricule"),result.getString("mdp"),result.getString("prenom"),result.getString("nom"),result.getInt("admin"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(ModifierUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		String matricule = request.getParameter("matricule");
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String mdp = request.getParameter("mdp");
		String confirmermdp = request.getParameter("confirmermdp");
		
		String info = "";
		try 
		{
			info = User.verif_modif_user(matricule, prenom, nom, mdp, confirmermdp,oldmatricule);
		} 
		catch (SQLException ex) 
		{
			Logger.getLogger(AjouterUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
		}
		if(info.equals("ok"))
		{
			old_user.modifier_utilisateur(Long.parseLong(matricule), prenom, nom, mdp);
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("modifinfo", info);
		}
		
		doGet(request, response);
			
	}


}
