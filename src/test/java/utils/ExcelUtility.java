package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private static XSSFSheet workSheet;
	private static XSSFRow row;
	private static XSSFCell cell;
	private static XSSFWorkbook workBook = new XSSFWorkbook();
	
	
	  public static void createExcelAndWrite(String fileName, String value,int RowNum,int ColNum){
        workBook = new XSSFWorkbook();
        workSheet = workBook.createSheet("FIRST SHEET");
   
        row = workSheet.createRow(RowNum);
        cell = row.createCell(ColNum);
       
        
        cell.setCellValue(value);
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
        {
            workBook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	  
	  
	  public static void createExcelAndWrite2(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite3(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite4(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite5(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite6(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite7(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite8(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite9(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void createExcelAndWrite10(String fileName, String value,int RowNum,int ColNum){
	        workBook = new XSSFWorkbook();
	        workSheet = workBook.createSheet("FIRST SHEET");
	   
	        row = workSheet.createRow(RowNum);
	        cell = row.createCell(ColNum);
	       
	        
	        cell.setCellValue(value);
	        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
	        {
	            workBook.write(fos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  }
	
	
	
	
	
	
	
	
	
	
//	public static void main(String[] args) throws Exception {
//
//		try {
//
//			// String path
//			// =System.getProperty("user.dir")+"/Replix/src/test/resources/config/FAX_SOFTLINX.xlsx";
//
//			FileInputStream ExcelFile = new FileInputStream(
//					"C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx");
//
//			XSSFWorkbook workBook = new XSSFWorkbook(ExcelFile);
//
//			sheet = workBook.getSheet("Sheet1");
//
//			row = sheet.getRow(0);
//
//			cell = row.getCell(0);
//			
//			String rowNumber = cell.getStringCellValue();
//			
//			System.out.println(sheet.getRow(1).getPhysicalNumberOfCells());
//			System.out.println(rowNumber);
//
//		} catch (Exception e) {
//
//			throw (e);
//
//		}
//
//	}
//}

//    public static String getCellData(int RowNum, int ColNum) throws Exception{
//        try{
//            cell = workSheet.getRow(1).getCell(3);
//            return cell.getStringCellValue();
//        }catch (Exception e){
//            return"";
//        }
//    }
//
//
//    public static void setCellData(String path, String value,  int RowNum, int ColNum) throws Exception {
//        try{
//            row  = workSheet.getRow(1);
//            cell = row.getCell(1);
//            if (cell == null) {
//                cell = row.createCell(1);
//                cell.setCellValue("tsi2");
//            } else {
//                cell.setCellValue("tsi3");
//            }
//            FileOutputStream fileOut = new FileOutputStream("/Replix/dataFile/testData.xlsx");
//            workBook.write(fileOut);
//            fileOut.flush();
//            fileOut.close();
//        }catch(Exception e){
//
//            throw (e);
//
//        }
//
//    }
//
//
  

//
//}
