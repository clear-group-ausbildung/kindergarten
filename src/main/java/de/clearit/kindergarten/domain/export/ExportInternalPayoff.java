package de.clearit.kindergarten.domain.export;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.export.entity.PayoffDataInternal;
import de.clearit.kindergarten.domain.export.entity.PayoffDataInternalVendor;
import de.clearit.kindergarten.domain.export.service.ExportDataService;

/**
 * The class ExportInternalPayoff
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportInternalPayoff {

	private static final Logger LOGGER = Logger.getLogger(ExportExcel.class.getName());
	private static final ExportInternalPayoff INSTANCE = new ExportInternalPayoff();

	private ExportInternalPayoff() {
	}

	public static ExportInternalPayoff getInstance() {
		return INSTANCE;
	}

	private XSSFWorkbook wb;
	private XSSFSheet sheet;

	private XSSFCellStyle priceStyle;

	/**
	 * Creates an receipt in excel for the given vendor.
	 * 
	 * @param pVendor
	 *            {@link VendorBean}
	 */
	public void createInternalPayoff() {
		try {
			wb = new XSSFWorkbook(new FileInputStream("./abrechnung_intern_template.xlsx"));
			sheet = wb.getSheetAt(0);
			createStyles();

			PayoffDataInternal payoffDataInternal = ExportDataService.getPayoffDataInternal();
			fillInPlaceholders(payoffDataInternal);

			FileOutputStream fileOut = new FileOutputStream(getDateiname());
			wb.write(fileOut);
			fileOut.close();
			wb.close();
		} catch (FileNotFoundException e) {
			LOGGER.fine("Error - Excel Template not found");
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.fine("Error Exel Export");
			e.printStackTrace();
		}
	}

	private void fillInPlaceholders(PayoffDataInternal pPayoffDataInternal) {
		Cell totalSoldItemsCell = getCellForPlaceholder("$totalSoldItems");
		if (totalSoldItemsCell != null) {
			totalSoldItemsCell.setCellValue(pPayoffDataInternal.getTotalSoldItems());
		}

		Cell turnoverCell = getCellForPlaceholder("$turnover");
		if (turnoverCell != null) {
			turnoverCell.setCellValue(pPayoffDataInternal.getTurnover());
		}

		Cell profitCell = getCellForPlaceholder("$profit");
		if (profitCell != null) {
			profitCell.setCellValue(pPayoffDataInternal.getProfit());
		}

		Cell paymentCell = getCellForPlaceholder("$payment");
		if (paymentCell != null) {
			paymentCell.setCellValue(pPayoffDataInternal.getPayment());
		}

		Cell dateCell = getCellForPlaceholder("$date");
		if (dateCell != null) {
			dateCell.setCellValue(new Date());
		}

		Cell vendorListStartCell = getCellForPlaceholder("$vendorListStart");
		if (vendorListStartCell != null) {
			createVendorList(pPayoffDataInternal, vendorListStartCell);
		}
	}

	private void createVendorList(PayoffDataInternal pPayoffDataInternal, Cell pStartCell) {
		for (PayoffDataInternalVendor payoffDataInternalVendor : pPayoffDataInternal.getPayoffDataInternalVendor()) {
			int rowCountGlobal = pStartCell.getRowIndex();
			int labelColumnIndex = pStartCell.getColumnIndex();
			int valueColumnIndex = labelColumnIndex + 1;

			rowCountGlobal = createPlaceholderRow(rowCountGlobal, labelColumnIndex, "");

			rowCountGlobal = createPlaceholderRow(rowCountGlobal, labelColumnIndex, "Verk&auml;ufer:");

			XSSFRow nameRow = sheet.createRow(rowCountGlobal);
			XSSFCell nameLabelCell = nameRow.createCell(labelColumnIndex);
			nameLabelCell.setCellValue("Name");
			XSSFCell nameValueCell = nameRow.createCell(valueColumnIndex);
			nameValueCell.setCellValue(payoffDataInternalVendor.getVendor().getLastName());
			rowCountGlobal++;

			XSSFRow firstnameRow = sheet.createRow(rowCountGlobal);
			XSSFCell firstnameLabelCell = firstnameRow.createCell(labelColumnIndex);
			firstnameLabelCell.setCellValue("Vorname");
			XSSFCell firstnameValueCell = firstnameRow.createCell(valueColumnIndex);
			firstnameValueCell.setCellValue(payoffDataInternalVendor.getVendor().getFirstName());

			XSSFRow vendorNumberRow = sheet.createRow(rowCountGlobal);
			XSSFCell vendorNumberLabelCell = vendorNumberRow.createCell(labelColumnIndex);
			vendorNumberLabelCell.setCellValue("Ver&auml;ufernummer(n)");
			XSSFCell vendorNumberValueCell = vendorNumberRow.createCell(valueColumnIndex);
			vendorNumberValueCell.setCellValue(payoffDataInternalVendor.getVendor().getVendorNumbers().stream()
					.map(Object::toString).collect(Collectors.joining(",")));

			XSSFRow paymentRow = sheet.createRow(rowCountGlobal);
			XSSFCell paymentLabelCell = paymentRow.createCell(labelColumnIndex);
			paymentLabelCell.setCellValue("Name");
			XSSFCell paymentValueCell = paymentRow.createCell(valueColumnIndex);
			paymentValueCell.setCellValue(payoffDataInternalVendor.getVendorPayment());
			paymentValueCell.setCellStyle(priceStyle);

		}
	}

	private int createPlaceholderRow(int pRowCount, int pColIndex, String pValue) {
		XSSFRow tempRow = sheet.createRow(pRowCount);
		if (pValue != null && !pValue.equals("")) {
			tempRow.createCell(pColIndex).setCellValue(pValue);
		}

		return ++pRowCount;
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

	private String getDateiname() {
		String folder = System.getProperty("user.home") + "/Desktop/Basar Abrechnungen";
		StringBuilder dateiName = new StringBuilder();
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
		dateiName.append("000_Abrechnung_intern");
		dateiName.append(".xlsx");
		return dateiName.toString();
	}

	private void createStyles() {
		priceStyle = wb.createCellStyle();
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
	}

}
