/**
 * 
 */
package com.linearregression;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author Arnaud
 *
 */

public class DataManager {
	
	Constants constants = new Constants();
	File file;
	Workbook wb;
	WritableWorkbook wwb;
	WritableSheet wsh = null;
	
	public int countData(String fileName) throws BiffException, IOException {
		int numLines = 0;
		file = new File(fileName);
		if (file.exists()) {
			wb = Workbook.getWorkbook(new File(fileName));
			Sheet sh = wb.getSheet("data");
			numLines = sh.getRows();
		}
		
		return numLines;
	}
	
	public void loadData(double[][] data, String fileName) throws BiffException, IOException {
		file = new File(fileName);
		if (file.exists()) {
			wb = Workbook.getWorkbook(new File(fileName));
			Sheet sh = wb.getSheet("data");
			for (int i = 0; i < sh.getColumns(); i++) {
				for (int j = 0; j < sh.getRows(); j++) {
					Cell cell = sh.getCell(i, j);
					if (cell.getContents().isEmpty()) { // Some cells of the dataset are empty. I don't know if it's buy error but i need to considerer that the corresponding value is 0.0
						data[j][i] = 0.0;
					} else {
						data[j][i] = Double.parseDouble(cell.getContents());
					}
				}
			}
		}
	}
	
	//public void writePredictions(double[] predictedData) throws BiffException, IOException, RowsExceededException, WriteException {
		//if (file.exists()) {
			//wb = Workbook.getWorkbook(new File("trainningData.xls"));
			//wwb = Workbook.createWorkbook(file, wb);
			//wsh = wwb.getSheet("data");
			
			//for (int i = 0; i < predictedData.length; i++) {
				//Label label = new Label(2, i, Double.toString(predictedData[i]));
				//wsh.addCell(label);
			//}
			//wwb.write();
			//wwb.close();
		//}
	//}
}
