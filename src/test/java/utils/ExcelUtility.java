package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    private static XSSFSheet workSheet;
    private static XSSFWorkbook workBook;
    private static int rowNumber = 0;

    private static final String FIRST_SHEET = "FIRST SHEET";

    public static void createExcelAndSheet() {
        workBook = new XSSFWorkbook();
        workSheet = workBook.createSheet(FIRST_SHEET);
    }

    public static void createExcelAndWrite(String fileName, String... values) {
        if(workBook == null && workSheet == null) {
            createExcelAndSheet();
        }
        XSSFRow row = workSheet.createRow(rowNumber);
        int columnIndex = 0;
        for (String value: values) {
            XSSFCell cell = row.createCell(columnIndex);
            cell.setCellValue(value);
            columnIndex++;
        }
        rowNumber++;
        try (FileOutputStream fos = new FileOutputStream("src/test/resources/dataFile/testData.xlsx")) {
            workBook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getColumnData(String fileLocation, int columnNumber) {
        List<String> values = new ArrayList<>();
        try {
            try (XSSFWorkbook workBook = new XSSFWorkbook(fileLocation)) {
				XSSFSheet workSheet = workBook.getSheet(FIRST_SHEET);
				for (Row row : workSheet) {
				    Cell cell = row.getCell(columnNumber);
				    if (cell != null) {
				        values.add(cell.getStringCellValue());
				    }
				}
			}
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return values;
    }

    public static String getCellData(String fileLocation, int rowNumber, int columnNumber) {
        try {
            try (XSSFWorkbook workBook = new XSSFWorkbook(fileLocation)) {
				XSSFSheet workSheet = workBook.getSheet(FIRST_SHEET);
				return workSheet.getRow(rowNumber).getCell(columnNumber).getStringCellValue();
			}
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
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


//
//}
