package api.utilities;


	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;

	import org.apache.poi.ss.usermodel.*;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	public class XLUtility {

	    private String filePath;
	    private FileInputStream fis;
	    private FileOutputStream fos;
	    private Workbook workbook;
	    private Sheet sheet;
	    private Row row;
	    private Cell cell;

	    public XLUtility(String filePath) {
	        this.filePath = filePath;
	    }

	    // Get row count
	    public int getRowCount(String sheetName) throws IOException {
	        fis = new FileInputStream(filePath);
	        workbook = new XSSFWorkbook(fis);
	        sheet = workbook.getSheet(sheetName);
	        int rowCount = sheet.getLastRowNum()+1;
	        workbook.close();
	        fis.close();
	        return rowCount;
	    }

	    // Get cell count in a row
	    public int getCellCount(String sheetName, int rowNum) throws IOException {
	        fis = new FileInputStream(filePath);
	        workbook = new XSSFWorkbook(fis);
	        sheet = workbook.getSheet(sheetName);
	        row = sheet.getRow(rowNum);
	        int cellCount = row.getLastCellNum();
	        workbook.close();
	        fis.close();
	        return cellCount;
	    }

	    // Get cell data
	    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
	        fis = new FileInputStream(filePath);
	        workbook = new XSSFWorkbook(fis);
	        sheet = workbook.getSheet(sheetName);
	        row = sheet.getRow(rowNum);
	        cell = row.getCell(colNum);

	        String data;

	        try {
	            DataFormatter formatter = new DataFormatter();
	            data = formatter.formatCellValue(cell); // handles string/numeric/boolean
	        } catch (Exception e) {
	            data = "";
	        }

	        workbook.close();
	        fis.close();
	        return data;
	    }

	    // Set cell data
	    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
	        fis = new FileInputStream(filePath);
	        workbook = new XSSFWorkbook(fis);
	        sheet = workbook.getSheet(sheetName);

	        row = sheet.getRow(rowNum);
	        if (row == null)
	            row = sheet.createRow(rowNum);

	        cell = row.getCell(colNum);
	        if (cell == null)
	            cell = row.createCell(colNum);

	        cell.setCellValue(data);

	        fos = new FileOutputStream(filePath);
	        workbook.write(fos);
	        workbook.close();
	        fis.close();
	        fos.close();
	    }
	}
