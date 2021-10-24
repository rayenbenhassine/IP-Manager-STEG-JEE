package com.java.classes;

import com.java.mysql.ConnexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rayen
 */
public class AdresseIP 
{

	private long matricule;
	private String prenom;
	private String adresse_ip;
	private int id_reseau;

	public AdresseIP(long matricule, String prenom, String adresse_ip, int id_reseau) 
	{
		this.matricule = matricule;
		this.prenom = prenom;
		this.adresse_ip = adresse_ip;
		this.id_reseau = id_reseau;
	}
	
	public void ajouterAdresseIP()
	{
		ConnexionBD.executeQuery("insert into adresse_ip values ("+ this.matricule +",'" + this.prenom + "','" + this.adresse_ip + "'," + this.id_reseau +")");
	}
	public static long convert_ip_long(String adresse_ip_string)
	{
		String tab_adresse_ip_string[] = adresse_ip_string.split("\\.");

		long adresse_ip_long;
		for(int i = 0; i < tab_adresse_ip_string.length; i++)
		{
			while(tab_adresse_ip_string[i].length() < 3)
			{
				tab_adresse_ip_string[i] = "0" + tab_adresse_ip_string[i];
			}
		}
		try
		{
			adresse_ip_long = Long.parseLong(String.join("",tab_adresse_ip_string));	
		}
		catch (NumberFormatException nfe)
		{
			adresse_ip_long = 0;
		}
		return adresse_ip_long;
	}
	
	public int Verif_appartenance_plage()
	{
		int id_reseau = -1;
		long ip_long = convert_ip_long(this.adresse_ip);
		ResultSet result = ConnexionBD.executeSelectQuery("select id_reseau, ip_reseau, passerelle from reseau");
		boolean ok = false;
		try {
			while(result.next() && ok == false)
			{
				long ip_reseau_long = convert_ip_long(result.getString("ip_reseau"));
				long passerelle_long = convert_ip_long(result.getString("passerelle"));
				if(ip_long > ip_reseau_long && ip_long < passerelle_long)
				{
					ok = true;
					id_reseau = result.getInt("id_reseau");
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdresseIP.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id_reseau;
	}
	public static boolean verifAdresseIP(String adresse_ip)
	{
		int nb = 0;
		if(adresse_ip.length() < 7 || adresse_ip.length() > 15)
		{
			return false;
		}
		for(int i=0;i<adresse_ip.length();i++)
		{
			if(adresse_ip.charAt(i) == '.')
			{
				nb++;
			}
		}
		if (nb != 3)
		{
			return false;
		}
		else
		{
			String tab_adresse_ip[] = adresse_ip.split("\\.");
			for(String s : tab_adresse_ip)
			{
				try
				{
					Integer a = Integer.parseInt(s);
					if(a > 255 || a < 0)
					{
						return false;
					}
				}
				catch(NumberFormatException nfe)
				{
					return false;
				}
			}
		}
		return true;
	}
	public static boolean exist_matricule(long matricule) 
	{
		ResultSet result = ConnexionBD.executeSelectQuery("select * from adresse_ip where matricule = " + matricule);
		try {
			return result.first();
		} catch (SQLException ex) {
			Logger.getLogger(AdresseIP.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
	public static boolean exist_ip(String adresse_ip) 
	{
		ResultSet result = ConnexionBD.executeSelectQuery("select * from adresse_ip where adresse_ip = '" + adresse_ip+"'");
		try {
			return result.first();
		} catch (SQLException ex) {
			Logger.getLogger(AdresseIP.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
	public static void supprimerAdresseIP(String matricule)
	{
		ConnexionBD.executeQuery("delete from adresse_ip where matricule = " + matricule);
	}
}
