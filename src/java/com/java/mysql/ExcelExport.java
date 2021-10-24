package com.java.mysql;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExport 
{
	private XSSFWorkbook xwb;
	XSSFSheet mysheet;
	public ExcelExport() throws IOException, InvalidFormatException 
	{
		xwb = new XSSFWorkbook();
		mysheet = xwb.createSheet("adresses_ip");
	}
	
	public void ExportData() throws SQLException
	{
		XSSFRow row = mysheet.createRow(0);
		XSSFCell cell;
		cell = row.createCell(0);
		cell.setCellValue("matricule");
		cell = row.createCell(1);
		cell.setCellValue("prenom");
		cell = row.createCell(2);
		cell.setCellValue("adresse_ip");
		for(int i=0; i<3 ; i++)
		{
			mysheet.autoSizeColumn(i);
		}
		int rowindex = 1;
		ResultSet result = ConnexionBD.executeSelectQuery("select matricule, prenom, adresse_ip from adresse_ip");
		while(result.next())
		{
			row = mysheet.createRow(rowindex);
			rowindex++;
			cell = row.createCell(0);
			cell.setCellValue(result.getLong("matricule"));
			cell = row.createCell(1);
			cell.setCellValue(result.getString("prenom"));
			cell = row.createCell(2);
			cell.setCellValue(result.getString("adresse_ip"));
			
		}
	}

	public XSSFWorkbook getXwb() {
		return xwb;
	}
	
	
	
}
