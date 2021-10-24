package com.java.classes;

import static com.java.classes.User.exist_matricule;
import com.java.mysql.ConnexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class Reseau 
{
	private int id_reseau;
	private String nomReseau;
	private String ip_reseau;
	private String masque_sous_reseau;
	private String passerelle;

	public Reseau(int id_reseau, String nomReseau, String ip_reseau, String masque_sous_reseau, String passerelle) {
		this.id_reseau = id_reseau;
		this.nomReseau = nomReseau;
		this.ip_reseau = ip_reseau;
		this.masque_sous_reseau = masque_sous_reseau;
		this.passerelle = passerelle;
	}
	
	public Vector<String> AdressesIPLibres() throws SQLException
	{
		long long_ip_reseau = AdresseIP.convert_ip_long(ip_reseau);
		long long_passerelle = AdresseIP.convert_ip_long(passerelle);
		String[] tab = new String[4];
		Vector<String> plage_ip = new Vector<String>();
		for(long i = long_ip_reseau; i <= long_passerelle; i++)
		{
			String str = String.valueOf(i);
			tab[0] = str.substring(0, 3);
			tab[1] = str.substring(3, 6);
			tab[2] = str.substring(6, 9);
			tab[3] = str.substring(9, 12);

			int octet1 = Integer.parseInt(tab[0]);
			int octet2 = Integer.parseInt(tab[1]);
			int octet3 = Integer.parseInt(tab[2]);
			int octet4 = Integer.parseInt(tab[3]);
			if(octet1 <= 255 && octet2 <= 255 && octet3 <= 255 && octet4 <= 255)
			{
				for(int j=0; j<tab.length;j++)
				{
					while(tab[j].startsWith("0") && !tab[j].equals("000"))
					{
						tab[j] = tab[j].substring(1);
					}
					if(tab[j].equals("000"))
					{
						tab[j] = "0";
					}
				}
				plage_ip.add(String.join(".", tab));
			}
		}
		ResultSet result = ConnexionBD.executeSelectQuery("select adresse_ip from adresse_ip where id_reseau = " + id_reseau);
		Vector<String> ip_reserves = new Vector<String>();
		while(result.next())
		{
			ip_reserves.add(result.getString("adresse_ip"));
		}
		Vector<String> ip_libres = new Vector<String>();
		for(String ip : plage_ip)
		{
			if(!ip_reserves.contains(ip) && !ip.endsWith("0"))
			{
				ip_libres.add(ip);
			}
		}
		return ip_libres;
	}
	public static String Verif_ajout_reseau(String ip_reseau, String masque_sous_reseau, String passerelle) throws SQLException
	{
		if(!AdresseIP.verifAdresseIP(ip_reseau))
		{
			return "Adresse IP réseau invalide";
		}
		else if(!AdresseIP.verifAdresseIP(masque_sous_reseau))
		{
			return "Masque invalide";
		}
		else if(!AdresseIP.verifAdresseIP(passerelle))
		{
			return "Passerelle invalide";
		}
		else
		{
			ResultSet result = ConnexionBD.executeSelectQuery("select ip_reseau from reseau where ip_reseau = '" + ip_reseau + "'");
			if(result.first())
			{
				return "Adresse IP réseau existante";
			}
			else
			{
				return "ok";				
			}
		}
	}
	public void ajouter_sous_reseau()
	{
		ConnexionBD.executeQuery("insert into reseau (nomReseau, ip_reseau, masque_sous_reseau,passerelle) values ('"+ nomReseau +"','"+ ip_reseau +"','"+ masque_sous_reseau +"','" + passerelle +"')");
	}
	public static void supprimerSousReseau(String id_reseau)
	{
		ConnexionBD.executeQuery("delete from reseau where id_reseau = " + id_reseau);
	}
}
