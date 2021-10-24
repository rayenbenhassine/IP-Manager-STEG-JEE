
package com.java.servlets;

import com.java.mysql.ConnexionBD;
import com.java.mysql.ExcelExport;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExporterExcel extends HttpServlet 
{
	public ExporterExcel()
	{
		super();
	}
		@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ExcelExport export;
		try {
			export = new ExcelExport();
			try {
				export.ExportData();
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			try(OutputStream out = response.getOutputStream())
			{
				export.getXwb().write(out);
			}
			} catch (SQLException ex) {
				Logger.getLogger(ExporterExcel.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (InvalidFormatException ex) {
			Logger.getLogger(ExporterExcel.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
		request.getRequestDispatcher("/reseau.jsp").forward(request, response);
	}


}
