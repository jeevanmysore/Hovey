package com.hovey;

import java.io.File;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Test2 {
	static int p,q,r;
	public static void main(String[] args) {
		try{
			Workbook workbook=null;
			String path="F:/sample.xlsx";
			FileInputStream fis=new FileInputStream(new File(path));
			String ext=Test2.getFileExtension(path);
			if(ext.equals("xlsx"))
				workbook=new XSSFWorkbook(fis);
			else
				workbook=new HSSFWorkbook(fis);
			
			
			int i=workbook.getNumberOfSheets();      // Getting Number of Sheets.
			System.out.println("Number of Sheets are "+i);
			for(int j=0;j<i;j++){                             // Looping through all the sheets from 0 to i
				Sheet sheet=workbook.getSheetAt(j);
				java.util.Iterator<Row> rowIterator=sheet.iterator();   // Getting the Row Iterator to iterate from row 0 to n within the sheet
				Row row=sheet.getRow(0);
				Iterator<Cell> cellIteratoer=row.cellIterator();
				
				while(cellIteratoer.hasNext()){
					Cell cell=cellIteratoer.next();
					String cellValue=cell.getStringCellValue();
					if("MYNAME #".toLowerCase().contains(cellValue)){
						p=cell.getColumnIndex();
					}
													
				}
			//	System.out.println("column is "+p);
				
				System.out.println(p);
				while(rowIterator.hasNext()){
					Row row1=rowIterator.next();
					if(row1.getRowNum()>=1){
						Cell cell1=row1.getCell(p);
						System.out.print(cell1.getStringCellValue()+" ");
					}
					
					
				}
			
				
				
				
				
				/*while(rowIterator.hasNext()){
					Row row=rowIterator.next();      // Iterating to each and Every Row..
					Iterator<Cell> cellIterator=row.cellIterator();    // Creating a cell iterator to traverse all the cells.
					while(cellIterator.hasNext()){
						Cell cell=cellIterator.next();
						 
						 *  Use cell.getCellType() to get the Type of Data in Cell.
						 *  There are only three types of data..
						 *  1. Boolean,
						 *  2. Numeric
						 *  3. String.
						 * 
						switch (cell.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								System.out.println(cell.getBooleanCellValue()+"\t\t");
								break;
							case Cell.CELL_TYPE_NUMERIC:
			                    System.out.print(cell.getNumericCellValue() + "\t\t");
			                    break;
			                case Cell.CELL_TYPE_STRING:
			                    System.out.print(cell.getStringCellValue() + "\t\t");
			                    break;
	
							default:
								break;
						} // End of Switch.
						
						
					} // End of Cell Iterator
					
					System.out.println("");
					
				}// End of row Iterator
							
*/				
				
				
				
				
				
				
				
				
			} //end of For
			
			fis.close();
		} // End of try
		catch(Exception e){
			e.printStackTrace();
		}// End of catch
		
	}
	
	public static String getFileExtension(String path){
		System.out.println("path "+path);
		int mid = path.lastIndexOf(".");

		String ext = path.substring(mid + 1, path.length());
		
		
		System.out.println("Extension :"+ext);
		return ext;
	}
	
}
