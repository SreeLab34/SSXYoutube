package demo.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.testng.annotations.DataProvider;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    /**
     * Searching for data
     *
     * @return A two-dimensional array 
     */
    @DataProvider(name = "text")
    public Object[][] text() {

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "data.xlsx";
        String worksheetName = "Sheet1";

        // Column index containing the search terms
        int columnIndex = 0;

        // Retrieve data from the Excel file using ExcelUtils
        return ExcelReaderUtil.readExcelData(filePath);
   
}
}