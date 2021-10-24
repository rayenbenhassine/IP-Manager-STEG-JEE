package com.java.servlets;

import com.java.classes.AdresseIP;
import com.java.mysql.ConnexionBD;
import com.java.mysql.ExcelImport;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class ImporterExcel extends HttpServlet 
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		try 
		{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> files = upload.parseRequest(request);
			for(FileItem file : files)
			{
				try 
				{
					if(file.getName().substring(file.getName().length()-4, file.getName().length()).equals("xlsx"))
					{
						file.write(new File("C:\\excel\\" + file.getName()));
						ExcelImport f = new ExcelImport(new File("C:\\excel\\" + file.getName()));
						f.importData();
						ResultSet result = ConnexionBD.executeSelectQuery("select * from adresse_ip");
						while(result.next())
						{
							AdresseIP adresse_ip = new AdresseIP(result.getLong("matricule"),result.getString("prenom"),result.getString("adresse_ip"),result.getInt("id_reseau"));
							if(adresse_ip.Verif_appartenance_plage() != -1)			
							{
								ConnexionBD.executeQuery("update adresse_ip set id_reseau = " + adresse_ip.Verif_appartenance_plage() + " where matricule = " + result.getLong("matricule"));
							}
						}
					}
					else
					{
						HttpSession session = request.getSession(true);
						session.setAttribute("infoimport", "Veuiller séléctionner un fichier .xlsx");
					}
				} 
				catch (Exception ex) 
				{
					Logger.getLogger(ImporterExcel.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		catch (FileUploadException ex)
		{
			Logger.getLogger(ImporterExcel.class.getName()).log(Level.SEVERE, null, ex);
		}
		request.getRequestDispatcher("/reseau.jsp").forward(request, response);

	}

}
