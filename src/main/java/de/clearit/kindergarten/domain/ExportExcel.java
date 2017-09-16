package de.clearit.kindergarten.domain;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

/**
 * The class XXX
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportExcel {

	private static final ExportExcel INSTANCE = new ExportExcel();

	private XSSFWorkbook wb;
	private XSSFSheet sheet;

	public static ExportExcel getInstance() {
		return INSTANCE;
	}

	public void createExcelVendorsAll() {
		List<VendorBean> vendorList = VendorService.getInstance().getAll();
		vendorList.stream().forEach((vendor) -> {
			createExcelVendorForId(vendor);
		});
	}

	public void createExcelVendorForId(VendorBean pVendor) {
		try {
			PayoffData payoffData = ExportDataService.getAbrechnungForVendor(pVendor);

			wb = new XSSFWorkbook(new FileInputStream("./src/main/resources/abrechnung_template.xlsx"));
			sheet = wb.getSheetAt(0);

			// TODO MJ auslagern!!!
			XSSFCellStyle currencyStyle = wb.createCellStyle();
			currencyStyle.setDataFormat((short) 7);

			XSSFCellStyle currencyResultStyle = wb.createCellStyle();
			currencyResultStyle.setDataFormat((short) 7);
			currencyResultStyle.setBorderBottom(BorderStyle.DOUBLE);

			XSSFCellStyle dateStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.YYYY"));

			Cell vendorCell = getCellForPlaceholder("$vendor");
			if (vendorCell != null) {
				vendorCell.setCellValue(payoffData.getVendorId());
			}

			Cell lastNameCell = getCellForPlaceholder("$lastName");
			if (lastNameCell != null) {
				lastNameCell.setCellValue(payoffData.getLastName());
			}

			Cell firstNameCell = getCellForPlaceholder("$firstName");
			if (firstNameCell != null) {
				firstNameCell.setCellValue(payoffData.getFirstName());
			}

			Cell phoneNumberCell = getCellForPlaceholder("$phoneNumber");
			if (phoneNumberCell != null) {
				phoneNumberCell.setCellValue(payoffData.getPhoneNumber());
			}

			Cell totalSoldItemsCell = getCellForPlaceholder("$totalSoldItems");
			if (totalSoldItemsCell != null) {
				totalSoldItemsCell.setCellValue(payoffData.getTotalSoldItems());
			}

			Cell turnoverCell = getCellForPlaceholder("$turnover");
			if (turnoverCell != null) {
				turnoverCell.setCellValue(payoffData.getTurnover());
			}

			Cell profitCell = getCellForPlaceholder("$profit");
			if (profitCell != null) {
				profitCell.setCellValue(payoffData.getProfit());
			}

			Cell paymentCell = getCellForPlaceholder("$payment");
			if (paymentCell != null) {
				paymentCell.setCellValue(payoffData.getPayment());
			}

			Cell dateCell = getCellForPlaceholder("$date");
			if (dateCell != null) {
				dateCell.setCellValue(new Date());
			}

			Cell soldItemListStartCell = getCellForPlaceholder("$soldItemListStart");
			if (soldItemListStartCell != null) {
				createSoldItemListInExcel(payoffData.getSoldItemNumbersPricesMap(), soldItemListStartCell);
			}

			// TODO MJ noch einbauen, dass die verkauften Artikel mit angezeigt
			// werden...evtl. mit Preis?

			// autoSizeAllColumns(sheet);

			FileOutputStream fileOut = new FileOutputStream(getDateiname(payoffData));
			wb.write(fileOut);
			fileOut.close();
			wb.close();
		} catch (Exception e) {
			System.out.println("Fehler");
			e.printStackTrace();
		}
	}

	private void createSoldItemListInExcel(HashMap<Integer, Double> pSoldItemMap, Cell startCell) {
		XSSFCellStyle numberStyle = wb.createCellStyle();
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
		numberStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
		numberStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
		numberStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));
		
		XSSFCellStyle priceStyle = wb.createCellStyle();
		priceStyle.setDataFormat((short) 7);
		priceStyle.setAlignment(HorizontalAlignment.LEFT);
		priceStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
		priceStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
		priceStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
		priceStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));

		if (pSoldItemMap.isEmpty()) {
			startCell.setCellValue("Keine Artikel verkauft");
		} else {
			int count = 0;
			int rowIndex = startCell.getRowIndex();
			int colIndexNumber = startCell.getColumnIndex();
			int colIndexPrice = colIndexNumber + 1;
			for (Integer key : pSoldItemMap.keySet()) {
				XSSFRow tempRow = sheet.createRow(rowIndex + count);
				
				XSSFCell numberCell = tempRow.createCell(colIndexNumber);
				numberCell.setCellValue(key);
				numberCell.setCellStyle(numberStyle);
				
				XSSFCell priceCell = tempRow.createCell(colIndexPrice);
				priceCell.setCellValue(pSoldItemMap.get(key));
				priceCell.setCellStyle(priceStyle);
				
				count++;
			}
		}
	}

	private Cell getCellForPlaceholder(String pPlaceholder) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().startsWith(pPlaceholder)) {
						return cell;
					}
				}
			}
		}
		return null;
	}

	private void autoSizeAllColumns(XSSFSheet pSheet) {
		// for (int x = 0; x < pSheet.getRow(0).getPhysicalNumberOfCells(); x++) {
		for (int x = 0; x < 3; x++) {
			pSheet.autoSizeColumn(x, true);
		}
	}

	private String getDateiname(PayoffData pPayoffData) {
		String folder = "./Abrechnungen";
		StringBuffer dateiName = new StringBuffer();
		if (!Files.isDirectory(Paths.get(folder))) {
			try {
				Files.createDirectory(Paths.get(folder));
				dateiName.append(folder);
				dateiName.append("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			dateiName.append(folder);
			dateiName.append("/");
		}
		dateiName.append("Abrechnung_");
		dateiName.append(pPayoffData.getVendorId());
		dateiName.append("_");
		dateiName.append(pPayoffData.getLastName());
		dateiName.append(".xlsx");
		return dateiName.toString();
	}

}
