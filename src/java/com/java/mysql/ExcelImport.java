package com.java.mysql;

import com.java.servlets.ExporterExcel;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImport 
{
	private XSSFWorkbook xwb;
	XSSFSheet mysheet;
	public ExcelImport(File myfile) throws IOException, InvalidFormatException 
	{
		xwb = new XSSFWorkbook(myfile);
		mysheet = xwb.getSheet("Sheet1");
	}
	public void importData()
	{
		ConnexionBD.executeQuery("delete from adresse_ip");
		int nbrow = mysheet.getPhysicalNumberOfRows();
		int nbcol = mysheet.getRow(0).getPhysicalNumberOfCells();
		Vector<String> v = new Vector<String>();
		for(int i = 1; i < nbrow; i++)
		{
			for ( int j = 0; j < nbcol; j++)
			{
				mysheet.getRow(i).getCell(j).setCellType(CellType.STRING);
				v.add(mysheet.getRow(i).getCell(j).getStringCellValue());
				
			}
			ConnexionBD.executeQuery("insert into adresse_ip values ("+ v.elementAt(0) + ",'" + v.elementAt(1) + "','"+ v.elementAt(2) +"',0)");
			v.clear();
		}
	}

	
}
