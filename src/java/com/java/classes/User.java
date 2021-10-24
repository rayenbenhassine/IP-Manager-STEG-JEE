package com.java.classes;
import com.java.mysql.ConnexionBD;
import com.java.servlets.ModifierUtilisateur;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class User 
{
	private long matricule;
	private String mdp;
	private String prenom;
	private String nom;
	private int admin;
	
	public User(long matricule, String mdp, String prenom, String nom, int admin)
	{
		this.matricule = matricule;
		this.mdp = mdp;
		this.prenom = prenom;
		this.nom = nom;
		this.admin = admin;
	}
	
	public static boolean verif_login(long matricule, String mdp)
	{
		boolean ok = false;
		ResultSet result = ConnexionBD.executeSelectQuery("select * from user where matricule = "+matricule+" and mdp = '"+mdp+"'");
		try {
			ok = result.first();
		} catch (SQLException ex) {
			return false;
		}
		return ok;
	}
	
	public static boolean exist_matricule(long matricule) throws SQLException
	{
		ResultSet result = ConnexionBD.executeSelectQuery("select * from user where matricule = " + matricule);
		return result.first();
	}
	
	
	public static String Verif_ajout_user(String matriculestr, String prenom, String nom, String mdp, String confirmermdp) throws SQLException
	{
		long matricule;
		try
		{
			matricule = Long.parseLong(matriculestr);
		}
		catch (NumberFormatException nfe)
		{
			return "Matricule invalide";
		}
		if(matricule <= 10000 || matricule > 99999)
		{
			return "Matricule invalide";
		}
		else if (mdp.length() > 50)
		{
			return "Mot de passe invalide";
		}
		else if (prenom.length() > 50)
		{
			return "Prenom invalide";
		}
		else if (nom.length() > 50)
		{
			return "Nom invalide";
		}
		else if (!mdp.equals(confirmermdp))
		{
			return "Vérifier la confirmation du mot de passe";		
		}
		else if(exist_matricule(matricule))
		{
			return "Matricule existante";
		}
		else
		{
			return "ok";
		}
	}
	
	public void ajouter_utilisateur()
	{
		ConnexionBD.executeQuery("insert into user values ("+ matricule +",'"+ prenom +"','"+ nom +"','" + mdp +"', "+ admin + ")");
	}

	public static String verif_modif_user(String matriculestr, String prenom, String nom, String mdp, String confirmermdp, String oldmatricule) throws SQLException
	{
		long matricule;
		try
		{
			matricule = Long.parseLong(matriculestr);
		}
		catch (NumberFormatException nfe)
		{
			return "Matricule invalide";
		}
		
		if(matricule <= 10000 || matricule > 99999)
		{
			return "Matricule invalide";
		}
		else if (mdp.length() > 50)
		{
			return "Mot de passe invalide";
		}
		else if (prenom.length() > 50)
		{
			return "Prenom invalide";
		}
		else if (nom.length() > 50)
		{
			return "Nom invalide";
		}
		else if (!mdp.equals(confirmermdp))
		{
			return "Vérifier la confirmation du mot de passe";		
		}
		else
		{
			ResultSet result = ConnexionBD.executeSelectQuery("select * from user where matricule <> " + oldmatricule);
			while(result.next())
			{
				if(result.getLong("matricule") == matricule)					
				{
					return "Matricule existante";
				}
			}
			return "ok";	
		}
	}
	
	public void modifier_utilisateur(long matricule, String prenom,String nom,String mdp)
	{
		ConnexionBD.executeQuery("update user set prenom = '" + prenom+"' where matricule = " + this.matricule);
		ConnexionBD.executeQuery("update user set nom = '" + nom+"' where matricule = " + this.matricule);
		ConnexionBD.executeQuery("update user set mdp = '" + mdp+"' where matricule = " + this.matricule);
		ConnexionBD.executeQuery("update user set matricule = " + matricule + " where matricule = " + this.matricule);		
	}
	
	public static void supprimer_utilisateur(String matricule)
	{
		ConnexionBD.executeQuery("delete from user where matricule = " + matricule);

	}
	public int getAdmin() {
		return admin;
	}

	public long getMatricule() {
		return matricule;
	}

	public String getMdp() {
		return mdp;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
}