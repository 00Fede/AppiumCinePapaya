package testing.datadrive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.examples.CellStyleDetails;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import testing.TestCC;

public class DataDrivenUsers {

	public static final String SHEET_NAME = "Hoja1";
	public static final String XLSFILEPATH = "C://Users//Administrator//workspace//TestCineColombia//src//credenciales.xls";
	public FileInputStream fis = null;
	public HSSFWorkbook workbook = null;
	public HSSFSheet sheet = null;
	public HSSFRow row = null;
	public HSSFCell cell = null;
	FormulaEvaluator objFormulaEvaluator = null;

	public DataDrivenUsers(String exelfilepath) {
		try {
			fis = new FileInputStream(exelfilepath);
			workbook = new HSSFWorkbook(fis);
			objFormulaEvaluator = new HSSFFormulaEvaluator(workbook);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Retorna el index (logico (empieza en 0)) de la ultima fila con
	 * informacion
	 * 
	 * @param sheet
	 *            String de la hoja, busca en workbook cargado.
	 * @return fila (basada en cero) con el ultimo dato.
	 */
	public int getLastIndexRow(String sheet) {
		HSSFSheet hssf_sheet = workbook.getSheet(sheet);
		for (int i = 0; i < hssf_sheet.getLastRowNum(); i++) {
			if (hssf_sheet.getRow(i).getCell(0).getStringCellValue().equals("")) {
				System.out.println("Ultima fila con informacion en Sheet " + (i - 1));
				return i - 1;
			}
		}
		return 0;
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		int col_Num = -1;
		try {
			// row.getLastCellNum()
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				// busca match
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}

			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);
			objFormulaEvaluator.evaluate(cell);

			DataFormatter dataFormatter = new DataFormatter();
			String cellValueStr = dataFormatter.formatCellValue(cell, objFormulaEvaluator);
			return cellValueStr;

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column  does not exist  in Excel";
		}
	}

	public void setData(int rowNum, int colNum, String statusMessage) {

		HSSFRow row = null;
		HSSFCell statusCell = null;

		try {

			FileOutputStream fileOutputStream = new FileOutputStream(XLSFILEPATH);
			sheet = workbook.getSheet(SHEET_NAME);
			row = sheet.getRow(rowNum);
			statusCell = row.createCell(colNum);
			statusCell.setCellValue(statusMessage);
//			if(statusMessage.equals(TestCC.ERROR_LOGIN)){
//				cs.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//				cs.setFillBackgroundColor(IndexedColors.RED.getIndex());
//			}
//			statusCell.setCellStyle(cs);
			workbook.write(fileOutputStream);

			fileOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
