package testing.datadrive;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDrivenUsers {

	public FileInputStream fis = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;

	public DataDrivenUsers(String exelfilepath) throws Exception {
		fis = new FileInputStream(exelfilepath);
		workbook = new XSSFWorkbook(fis);
		fis.close();
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		int col_Num = -1;
		try {
			// row.getLastCellNum()
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);

			System.out.println(row.getLastCellNum());
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}

			row = sheet.getRow(rowNum - 1);
			cell = row.getCell(col_Num);
			return cell.getStringCellValue();
			// if(cell.getCellTypeEnum() == CellType.STRING)
			// return cell.getStringCellValue();

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column  does not exist  in Excel";
		}
	}
}