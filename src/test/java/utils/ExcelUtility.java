package utils;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	//private static XSSFSheet workSheet;
    //private static XSSFRow row;
    // public static String setExcelFile(String Path, String SheetName) throws Exception {
    	public static void main (String[] args) throws Exception {
    		
    	
        try {
        
        	//String path =System.getProperty("user.dir")+"/Replix/src/test/resources/config/FAX_SOFTLINX.xlsx";
        	
            FileInputStream ExcelFile = new FileInputStream("C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx");
           
          
            XSSFWorkbook  workBook = new XSSFWorkbook(ExcelFile);
            workBook.getSheetAt(0);
            XSSFSheet workSheet = workBook.getSheet("Sheet1");
            
         XSSFRow row=workSheet.getRow(0);
            XSSFCell cell ;
            
            	
            
            System.out.println(workSheet.getRow(1).getPhysicalNumberOfCells());
            
//            XSSFCell bp = row.getCell(2);
//            String rowNumber =bp.getStringCellValue();
//            System.out.println(rowNumber);
           // return rowNumber;
           
        } catch (Exception e) {

            throw (e);

        }
        
      
    }
}

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
//    public static void createExcelAndWrite(String fileName, String value){
//        workBook = new XSSFWorkbook();
//        workSheet = workBook.createSheet("FIRST SHEET");
//        row = workSheet.createRow(0);
//        cell = row.createCell(0);
//        cell.setCellValue(value);
//        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
//        {
//            workBook.write(fos);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
