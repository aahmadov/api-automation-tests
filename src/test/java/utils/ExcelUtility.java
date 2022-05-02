package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private static XSSFSheet workSheet;
    private static XSSFWorkbook workBook;
    private static XSSFCell cell;
    private static XSSFRow row;
    private static MissingCellPolicy xRow;


    public static String setExcelFile(String Path, String SheetName) throws Exception {
        try {
        	//String path =System.getProperty("user.dir")+"/Replix/src/test/resources/config/FAX_SOFTLINX.xlsx";
            FileInputStream ExcelFile = new FileInputStream("/Replix/src/test/resources/config/FAX_SOFTLINX.xlsx");
            workBook = new XSSFWorkbook(ExcelFile);
            workSheet = workBook.getSheet("Sheet1");
            row=workSheet.getRow(1);
            
            XSSFCell bp = row.getCell(1);
            String rowNumber =bp.getStringCellValue();
           System.out.println(rowNumber);
           return rowNumber;
           
        } catch (Exception e) {

            throw (e);

        }
        
      
    }

    public static String getCellData(int RowNum, int ColNum) throws Exception{
        try{
            cell = workSheet.getRow(1).getCell(3);
            return cell.getStringCellValue();
        }catch (Exception e){
            return"";
        }
    }


    public static void setCellData(String path, String value,  int RowNum, int ColNum) throws Exception {
        try{
            row  = workSheet.getRow(RowNum);
            cell = row.getCell(ColNum);
            if (cell == null) {
                cell = row.createCell(ColNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch(Exception e){

            throw (e);

        }

    }


    public static void createExcelAndWrite(String fileName, String value){
        workBook = new XSSFWorkbook();
        workSheet = workBook.createSheet("FIRST SHEET");
        row = workSheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue(value);
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)))
        {
            workBook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
