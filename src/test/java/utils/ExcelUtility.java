package utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    private static XSSFSheet workSheet;
    private static XSSFWorkbook workBook;
    private static int rowNumber = 0;

    private static final String FIRST_SHEET = "FIRST SHEET";

    public static void createExcelAndSheet() {
        workBook = new XSSFWorkbook();
        workSheet = workBook.createSheet(FIRST_SHEET);
    }

    public static void createExcelAndWrite(String fileName, String value) {
        XSSFRow row = workSheet.createRow(rowNumber);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(value);
        rowNumber++;
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
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
