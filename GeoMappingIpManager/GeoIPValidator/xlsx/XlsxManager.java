package xlsx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsxManager {
	private Workbook workbook;
	private Sheet mainSheet;
	
	public void setWorkbook(String path) {
		try {
			workbook = WorkbookFactory.create(new File(path));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Sheet getSheet(String sheetName) {
		Sheet retorno = null;
		for(Sheet sheet: workbook) {
			if(sheetName.equals(sheet.getSheetName())) {
				retorno = sheet;
            	break;
			}
		}
		return retorno;
	}
	
	public void setSheet(String sheetName) {
		mainSheet = getSheet(sheetName);
	}
	
	public String getRowValue(Row row, int cellNum) {
		DataFormatter dataFormatter = new DataFormatter();
		return dataFormatter.formatCellValue(row.getCell(cellNum));
	}
	
	public String getIp(Row row) {
		return getRowValue(row, 0);
	}
	
	public String getCity(Row row) {
		return getRowValue(row, 1);
	}
	
	public ArrayList<Row> getRows() {
		ArrayList<Row> retorno = new ArrayList<Row>();
		for (Row row: mainSheet) {
			retorno.add(row);
        }
		return retorno;
	}

	public XlsxManager(String path, String sheetName) {
		setWorkbook(path);
		setSheet(sheetName);
	}
}
